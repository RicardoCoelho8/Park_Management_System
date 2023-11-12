package labdsoft.payments_bo_mcs.services;

import labdsoft.payments_bo_mcs.model.payment.PaymentsTableRow;
import labdsoft.payments_bo_mcs.model.priceTable.PriceTableEntry;
import labdsoft.payments_bo_mcs.model.priceTable.ThresholdCost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaymentsServiceImplTest {
    PaymentsServiceImpl service = new PaymentsServiceImpl();
    List<PriceTableEntry> listPriceTableEntry = new ArrayList<>();
    ArrayList<ThresholdCost> listThresholdCosts = new ArrayList<>();
    Calendar enterPark = Calendar.getInstance();
    Calendar leftPark = Calendar.getInstance();

    @BeforeEach
    void setUp() {
        listThresholdCosts.add(ThresholdCost.builder().thresholdMinutes(15).costPerMinuteAutomobiles(0.15).costPerMinuteMotorcycles(0.15).build());
        listThresholdCosts.add(ThresholdCost.builder().thresholdMinutes(15).costPerMinuteAutomobiles(0.15).costPerMinuteMotorcycles(0.15).build());
        listThresholdCosts.add(ThresholdCost.builder().thresholdMinutes(15).costPerMinuteAutomobiles(0.15).costPerMinuteMotorcycles(0.15).build());
        listThresholdCosts.add(ThresholdCost.builder().thresholdMinutes(15).costPerMinuteAutomobiles(0.30).costPerMinuteMotorcycles(0.30).build());

        listPriceTableEntry.add(PriceTableEntry.builder().entryId(2L).periodStart("21:00").periodEnd("9:00").thresholds(listThresholdCosts).parkId(1L).build());
        listPriceTableEntry.add(PriceTableEntry.builder().entryId(1L).periodStart("9:00").periodEnd("21:00").thresholds(listThresholdCosts).parkId(1L).build());

        enterPark.set(2023, Calendar.NOVEMBER, 7, 8, 30, 0);
        leftPark.set(2023, Calendar.NOVEMBER, 7, 10, 14, 0);
    }

    @Test
    void calculateCost() {
        String vehicleType = "Automobiles";

        ArrayList<PaymentsTableRow> result = service.calculateCost(vehicleType, enterPark, leftPark, listPriceTableEntry);

        ArrayList<PaymentsTableRow> expectedResult = new ArrayList<>();

        expectedResult.add(PaymentsTableRow.builder().periodStart("8:30").periodEnd("10:14").fractionStart("8:30").fractionEnd("8:45").vehicleType(vehicleType).price(0.15).build());
        expectedResult.add(PaymentsTableRow.builder().periodStart("8:30").periodEnd("10:14").fractionStart("8:45").fractionEnd("9:0").vehicleType(vehicleType).price(0.15).build());
        expectedResult.add(PaymentsTableRow.builder().periodStart("8:30").periodEnd("10:14").fractionStart("9:0").fractionEnd("9:15").vehicleType(vehicleType).price(0.15).build());
        expectedResult.add(PaymentsTableRow.builder().periodStart("8:30").periodEnd("10:14").fractionStart("9:15").fractionEnd("9:30").vehicleType(vehicleType).price(0.15).build());
        expectedResult.add(PaymentsTableRow.builder().periodStart("8:30").periodEnd("10:14").fractionStart("9:30").fractionEnd("9:45").vehicleType(vehicleType).price(0.15).build());
        expectedResult.add(PaymentsTableRow.builder().periodStart("8:30").periodEnd("10:14").fractionStart("9:45").fractionEnd("10:0").vehicleType(vehicleType).price(0.15).build());
        expectedResult.add(PaymentsTableRow.builder().periodStart("8:30").periodEnd("10:14").fractionStart("10:0").fractionEnd("10:15").vehicleType(vehicleType).price(0.30).build());

        assertEquals(expectedResult, result);
    }
}