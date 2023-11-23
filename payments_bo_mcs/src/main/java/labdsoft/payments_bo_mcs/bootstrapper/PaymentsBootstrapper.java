package labdsoft.payments_bo_mcs.bootstrapper;

import labdsoft.payments_bo_mcs.model.payment.Payments;
import labdsoft.payments_bo_mcs.model.payment.PaymentsTableRow;
import labdsoft.payments_bo_mcs.model.user.AppUser;
import labdsoft.payments_bo_mcs.model.vehicle.Vehicle;
import labdsoft.payments_bo_mcs.model.vehicle.VehicleType;
import labdsoft.payments_bo_mcs.repositories.PaymentsRepository;
import labdsoft.payments_bo_mcs.repositories.PaymentsTableRowRepository;
import labdsoft.payments_bo_mcs.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
//@Profile("bootstrap")
public class PaymentsBootstrapper implements CommandLineRunner {

    @Autowired
    private PaymentsRepository pRepo;

    @Autowired
    private PaymentsTableRowRepository p_tr_Repo;

    @Autowired
    private UserRepository uRepo;

    @Override
    public void run(String... args) {
        pRepo.deleteAll();
        p_tr_Repo.deleteAll();
        uRepo.deleteAll();

        for (int i = 0; i < 2; i++) {
            Long invoice = (long) (1000.0 + i * 100.0);
            Double discount = i % 2 == 0 ? 50.0 : 0.0;
            ArrayList<PaymentsTableRow> rows = generatePaymentTableRows(i * i);
            Long nif = 123456789L + i;
            paymentsValues(invoice, discount, rows, nif);
            userValues(nif);
        }
    }

    private void paymentsValues(Long invoice, Double discount, ArrayList<PaymentsTableRow> rows, Long nif) {
        Payments p1 = new Payments();
        p1.setInvoice(invoice);
        p1.setDiscount(discount);
        p1.setPaymentsTableRows(rows);
        p1.setNif(nif);

        //System.out.println(p1);
        pRepo.save(p1);
    }

    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";

    private void userValues(Long nif) {
        AppUser u1 = new AppUser();
        u1.setNif(nif);

        String licensePlate =  new Random().ints(6, 0, LETTERS.length() + DIGITS.length())
                .mapToObj(i -> (i < LETTERS.length()) ? LETTERS.charAt(i) : DIGITS.charAt(i - LETTERS.length()))
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();

        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleList.add(Vehicle.builder().licensePlateNumber(licensePlate).vehicleType(VehicleType.FUEL).build());

        u1.setVehicles(vehicleList);
        //System.out.println(u1);
        uRepo.save(u1);
    }

    private ArrayList<PaymentsTableRow> generatePaymentTableRows(int h) {
        ArrayList<PaymentsTableRow> rows = new ArrayList<>();

        String[] vehicleTypeArray = {"Automobiles", "Motorcycles"};

        for (int i = 0; i < 3; i++) {

            String vehicleType = vehicleTypeArray[new Random().nextInt(vehicleTypeArray.length)];

            PaymentsTableRow row = new PaymentsTableRow();
            row.setPeriodStart("");
            row.setPeriodEnd("");
            row.setFractionStart("");
            row.setFractionEnd("");
            row.setVehicleType(vehicleType);
            row.setPrice(100.0 + h * 10.0);

            rows.add(row);
            p_tr_Repo.save(row);
        }

        return rows;
    }


}
