package labdsoft.payments_bo_mcs.services;

import labdsoft.payments_bo_mcs.communication.Publish;
import labdsoft.payments_bo_mcs.model.payment.Payments;
import labdsoft.payments_bo_mcs.model.payment.PaymentsDTO;
import labdsoft.payments_bo_mcs.model.payment.PaymentsTableRow;
import labdsoft.payments_bo_mcs.model.priceTable.PriceTableEntry;
import labdsoft.payments_bo_mcs.model.user.AppUser;
import labdsoft.payments_bo_mcs.model.vehicle.Vehicle;
import labdsoft.payments_bo_mcs.repositories.PaymentsRepository;
import labdsoft.payments_bo_mcs.repositories.UserRepository;
import labdsoft.payments_bo_mcs.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PaymentsServiceImpl implements PaymentsService {
    @Value("${spring.rabbitmq.host}")
    private String host;

    @Autowired
    private PaymentsRepository repository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public PaymentsDTO create(final Date enterParkDate, final Date leftParkDate, final Long parkID, final String licensePlateNumber) throws Exception {

        final Optional<Vehicle> vehicle = vehicleRepository.findByLicensePlate(licensePlateNumber);

        if (vehicle.isPresent()) {
            throw new IllegalArgumentException("Vehicle does not exist.");
        }

        final Optional<AppUser> verification = userRepository.findByVehicle(vehicle.get().getLicensePlateNumber());

        if (verification.isPresent()) {
            throw new IllegalArgumentException("User does not exist.");
        }

        List<PriceTableEntry> listPriceTableEntry = Publish.getPriceTablePark("park", parkID.toString(), host);

        Calendar enterPark = Calendar.getInstance();
        enterPark.setTime(enterParkDate);

        Calendar leftPark = Calendar.getInstance();
        leftPark.setTime(leftParkDate);

        List<PaymentsTableRow> rows = calculateCost(vehicle.get().getVehicleType().name(), enterPark, leftPark, listPriceTableEntry);

        final Payments p = Payments.builder().discount(0D).paymentsTableRows(rows).nif(verification.get().getNif()).build();

        repository.save(p);

        return p.toDTO();
    }

    public ArrayList<PaymentsTableRow> calculateCost(String vehicleType, Calendar enterPark, Calendar leftPark, List<PriceTableEntry> listPriceTableEntry) {
        ArrayList<PaymentsTableRow> rows = new ArrayList<>();

        enterPark.set(Calendar.MILLISECOND, 0);
        leftPark.set(Calendar.MILLISECOND, 0);
        enterPark.set(Calendar.SECOND, 0);
        leftPark.set(Calendar.SECOND, 0);

        Calendar actualPeriod = (Calendar) enterPark.clone();

        int counter = 0;
        PriceTableEntry lastEntry = null;

        while (actualPeriod.before(leftPark)) {
            double price;

            PriceTableEntry entry = listPriceTableEntry.stream().filter(p -> isTimeBetween(actualPeriod, p.getPeriodStart(), p.getPeriodEnd())).findFirst().get();

            if (lastEntry == null || !lastEntry.getPeriodStart().equals(entry.getPeriodStart())) {
                counter = 0;
                lastEntry = entry;
            }

            PaymentsTableRow paymentsTableRow = new PaymentsTableRow();
            paymentsTableRow.setPeriodStart(enterPark.get(Calendar.HOUR_OF_DAY) + ":" + enterPark.get(Calendar.MINUTE));
            paymentsTableRow.setPeriodEnd(leftPark.get(Calendar.HOUR_OF_DAY) + ":" + leftPark.get(Calendar.MINUTE));

            paymentsTableRow.setFractionStart(actualPeriod.get(Calendar.HOUR_OF_DAY) + ":" + actualPeriod.get(Calendar.MINUTE));

            if (counter < entry.getThresholds().size()) {
                if (vehicleType.equals("Automobile")) {
                    price = entry.getThresholds().get(counter).getCostPerMinuteAutomobiles();
                } else {
                    price = entry.getThresholds().get(counter).getCostPerMinuteMotorcycles();
                }
                actualPeriod.add(Calendar.MINUTE, entry.getThresholds().get(counter).getThresholdMinutes());
            } else {
                if (vehicleType.equals("Automobile")) {
                    price = entry.getThresholds().get(entry.getThresholds().size() - 1).getCostPerMinuteAutomobiles();
                } else {
                    price = entry.getThresholds().get(entry.getThresholds().size() - 1).getCostPerMinuteMotorcycles();
                }
                actualPeriod.add(Calendar.MINUTE, entry.getThresholds().get(entry.getThresholds().size() - 1).getThresholdMinutes());
            }

            counter++;

            paymentsTableRow.setFractionEnd(actualPeriod.get(Calendar.HOUR_OF_DAY) + ":" + actualPeriod.get(Calendar.MINUTE));

            paymentsTableRow.setVehicleType(vehicleType);
            paymentsTableRow.setPrice(price);

            rows.add(paymentsTableRow);
        }
        return rows;
    }

    private boolean isTimeBetween(Calendar calendar, String startTime, String endTime) {
        int startHour = Integer.parseInt(startTime.split(":")[0]);
        int startMinute = Integer.parseInt(startTime.split(":")[1]);

        int endHour = Integer.parseInt(endTime.split(":")[0]);
        int endMinute = Integer.parseInt(endTime.split(":")[1]);

        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        if (startHour > endHour) {
            if (currentHour < startHour && currentHour < endHour) {
                return true;
            } else if (currentHour == startHour && currentMinute >= startMinute) {
                return true;
            } else return currentHour == endHour && currentMinute <= endMinute;
        } else {
            if (currentHour > startHour && currentHour < endHour) {
                return true;
            } else if (currentHour == startHour && currentMinute >= startMinute) {
                return true;
            } else return currentHour == endHour && currentMinute <= endMinute;
        }
    }

    @Override
    public Iterable<PaymentsDTO> findByUserNIF(String nif) {
        Optional<List<Payments>> possibleResult = repository.getPaymentsOfUser(nif);
        return possibleResult.<Iterable<PaymentsDTO>>map(payments -> payments.parallelStream().map(Payments::toDTO).collect(Collectors.toCollection(ArrayList::new))).orElseGet(ArrayList::new);
    }

    @Override
    public Iterable<PaymentsDTO> getCatalog() {
        Optional<List<Payments>> possibleResult = repository.getAllPayments();
        return possibleResult.<Iterable<PaymentsDTO>>map(payments -> payments.parallelStream().map(Payments::toDTO).collect(Collectors.toCollection(ArrayList::new))).orElseGet(ArrayList::new);
    }

}
