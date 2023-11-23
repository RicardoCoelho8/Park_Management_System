package labdsoft.payments_bo_mcs.bootstrapper;

import labdsoft.payments_bo_mcs.model.payment.Payments;
import labdsoft.payments_bo_mcs.model.payment.PaymentsTableRow;
import labdsoft.payments_bo_mcs.model.priceTable.PriceTableEntry;
import labdsoft.payments_bo_mcs.model.priceTable.ThresholdCost;
import labdsoft.payments_bo_mcs.model.user.AppUser;
import labdsoft.payments_bo_mcs.model.vehicle.Vehicle;
import labdsoft.payments_bo_mcs.model.vehicle.VehicleType;
import labdsoft.payments_bo_mcs.repositories.PaymentsRepository;
import labdsoft.payments_bo_mcs.repositories.PaymentsTableRowRepository;
import labdsoft.payments_bo_mcs.repositories.PriceTableEntryRepository;
import labdsoft.payments_bo_mcs.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
//@Profile("bootstrap")
public class ParkBootstrapper implements CommandLineRunner {

    @Autowired
    private PriceTableEntryRepository ptRepo;


    @Override
    public void run(String... args) {
        ptRepo.deleteAll();

        createSamplePriceTableEntry(1L);
        createSamplePriceTableEntry(2L);
        createSamplePriceTableEntry(3L);
        createSamplePriceTableEntry(4L);
        createSamplePriceTableEntry(5L);

    }

    private void createSamplePriceTableEntry(Long id) {
        List<PriceTableEntry> list_priceTableEntry = new ArrayList<>();

        list_priceTableEntry.add(PriceTableEntry.builder().periodStart("9:00").periodEnd("21:00").thresholds(createSampleThresholdCosts()).parkId(id).build());
        list_priceTableEntry.add(PriceTableEntry.builder().periodStart("21:00").periodEnd("9:00").thresholds(createSampleThresholdCosts()).parkId(id).build());

        ptRepo.saveAll(list_priceTableEntry);
    }

    private List<ThresholdCost> createSampleThresholdCosts() {
        List<ThresholdCost> list_thresholdCosts = new ArrayList<>();

        list_thresholdCosts.add(ThresholdCost.builder().thresholdMinutes(15).costPerMinuteAutomobiles(0.15).costPerMinuteMotorcycles(0.15).build());
        list_thresholdCosts.add(ThresholdCost.builder().thresholdMinutes(15).costPerMinuteAutomobiles(0.15).costPerMinuteMotorcycles(0.15).build());
        list_thresholdCosts.add(ThresholdCost.builder().thresholdMinutes(15).costPerMinuteAutomobiles(0.15).costPerMinuteMotorcycles(0.15).build());
        list_thresholdCosts.add(ThresholdCost.builder().thresholdMinutes(15).costPerMinuteAutomobiles(0.15).costPerMinuteMotorcycles(0.15).build());
        list_thresholdCosts.add(ThresholdCost.builder().thresholdMinutes(15).costPerMinuteAutomobiles(0.30).costPerMinuteMotorcycles(0.30).build());

        return list_thresholdCosts;
    }
}
