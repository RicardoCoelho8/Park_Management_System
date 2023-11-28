package labdsoft.payments_bo_mcs.bootstrapper;

import labdsoft.payments_bo_mcs.model.payment.Payments;
import labdsoft.payments_bo_mcs.model.payment.PaymentsTableRow;
import labdsoft.payments_bo_mcs.model.vehicle.VehicleType;
import labdsoft.payments_bo_mcs.repositories.PaymentsRepository;
import labdsoft.payments_bo_mcs.repositories.PaymentsTableRowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Random;

@Component
//@Profile("bootstrap")
public class PaymentsBootstrapper implements CommandLineRunner {

    @Autowired
    private PaymentsRepository pRepo;
    @Autowired
    private PaymentsTableRowRepository p_tr_Repo;

    @Override
    public void run(String... args) {
        pRepo.deleteAll();
        p_tr_Repo.deleteAll();

        Double discount = 0D;
        ArrayList<PaymentsTableRow> rows = generatePaymentTableRows(0);
        Long nif = 502113846L;
        paymentsValues(discount, rows, nif);

        rows = generatePaymentTableRows(1);
        nif = 508776392L;
        paymentsValues(discount, rows, nif);

    }

    private void paymentsValues(Double discount, ArrayList<PaymentsTableRow> rows, Long nif) {
        Payments p1 = new Payments();
        p1.setDiscount(discount);
        p1.setPaymentsTableRows(rows);
        p1.setNif(nif);

        //System.out.println(p1);
        pRepo.save(p1);
    }

    private ArrayList<PaymentsTableRow> generatePaymentTableRows(int h) {
        ArrayList<PaymentsTableRow> rows = new ArrayList<>();

        String[] vehicleTypeArray = {VehicleType.AUTOMOBILE.name(), VehicleType.MOTORCYCLE.name()};

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
