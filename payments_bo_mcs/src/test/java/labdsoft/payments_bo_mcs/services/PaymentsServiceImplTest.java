package labdsoft.payments_bo_mcs.services;

import labdsoft.payments_bo_mcs.model.barrier.BarrierInfoDTO;
import labdsoft.payments_bo_mcs.model.payment.Payments;
import labdsoft.payments_bo_mcs.model.payment.PaymentsDTO;
import labdsoft.payments_bo_mcs.model.payment.PaymentsTableRow;
import labdsoft.payments_bo_mcs.model.payment.PaymentsTableRowDTO;
import labdsoft.payments_bo_mcs.model.priceTable.PriceTableEntry;
import labdsoft.payments_bo_mcs.model.priceTable.ThresholdCost;
import labdsoft.payments_bo_mcs.model.user.AppUser;
import labdsoft.payments_bo_mcs.model.vehicle.Vehicle;
import labdsoft.payments_bo_mcs.model.vehicle.VehicleType;
import labdsoft.payments_bo_mcs.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentsServiceImplTest {
    List<PriceTableEntry> listPriceTableEntry = new ArrayList<>();
    ArrayList<ThresholdCost> listThresholdCosts = new ArrayList<>();
    Calendar enterPark = Calendar.getInstance();
    Calendar leftPark = Calendar.getInstance();
    @Mock
    private PaymentsRepository repository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private PriceTableEntryRepository priceTableEntryRepository;

    @Mock
    private PaymentsTableRowRepository p_tr_Repo;

    @InjectMocks
    private PaymentsServiceImpl service;


    @BeforeEach
    void setUp() {
        listThresholdCosts.add(ThresholdCost.builder().thresholdMinutes(15).costPerMinuteAutomobiles(0.15).costPerMinuteMotorcycles(0.15).build());
        listThresholdCosts.add(ThresholdCost.builder().thresholdMinutes(15).costPerMinuteAutomobiles(0.15).costPerMinuteMotorcycles(0.15).build());
        listThresholdCosts.add(ThresholdCost.builder().thresholdMinutes(15).costPerMinuteAutomobiles(0.15).costPerMinuteMotorcycles(0.15).build());
        listThresholdCosts.add(ThresholdCost.builder().thresholdMinutes(15).costPerMinuteAutomobiles(0.30).costPerMinuteMotorcycles(0.30).build());

        listPriceTableEntry.add(PriceTableEntry.builder().periodStart("21:00").periodEnd("9:00").thresholds(listThresholdCosts).parkId(1L).build());
        listPriceTableEntry.add(PriceTableEntry.builder().periodStart("9:00").periodEnd("21:00").thresholds(listThresholdCosts).parkId(1L).build());

        enterPark.set(2023, Calendar.NOVEMBER, 7, 8, 30, 0);
        leftPark.set(2023, Calendar.NOVEMBER, 7, 10, 14, 0);
    }

    @Test
    void calculateCost_AUTOMOBILE() {
        String vehicleType = "AUTOMOBILE";

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

    @Test
    void createFromBarrier() throws Exception {
        Vehicle vehicle = Vehicle.builder().plateNumber("AA-00-00").vehicleType(VehicleType.AUTOMOBILE).build();

        when(vehicleRepository.findByLicensePlate("AA-00-00")).thenReturn(Optional.of(vehicle));

        AppUser user = AppUser.builder().nif(501776508L).build();

        when(userRepository.findByVehicle("AA-00-00")).thenReturn(Optional.of(user));

        when(priceTableEntryRepository.findAllByParkId(1L)).thenReturn(listPriceTableEntry);

        Calendar enterPark = Calendar.getInstance();
        enterPark.set(2023, Calendar.NOVEMBER, 7, 8, 30, 0);

        Calendar leftPark = Calendar.getInstance();
        leftPark.set(2023, Calendar.NOVEMBER, 7, 8, 45, 0);

        BarrierInfoDTO barrierInfoDTO = BarrierInfoDTO.builder().licensePlateNumber("AA-00-00").enterPark(enterPark).leftPark(leftPark).parkID(1L).build();

        ArrayList<PaymentsTableRow> rows = new ArrayList<>();

        PaymentsTableRow paymentsTableRow = PaymentsTableRow.builder().paymentTableRowID(1L).periodStart("8:30").periodEnd("8:45").fractionStart("8:30").fractionEnd("8:45").price(0.15).vehicleType("AUTOMOBILE").build();

        rows.add(paymentsTableRow);

        when(p_tr_Repo.save(PaymentsTableRow.builder().periodStart("8:30").periodEnd("8:45").fractionStart("8:30").fractionEnd("8:45").price(0.15).vehicleType("AUTOMOBILE").build())).thenReturn(paymentsTableRow);

        Payments p = Payments.builder().discount(0D).paymentsTableRows(rows).nif(501776508L).build();
        Payments pResult = Payments.builder().invoice(37L).discount(0D).paymentsTableRows(rows).nif(501776508L).build();

        when(repository.save(p)).thenReturn(pResult);

        PaymentsDTO result = service.createFromBarrier(barrierInfoDTO);

        ArrayList<PaymentsTableRowDTO> paymentsTableRowDTO = new ArrayList<>();
        paymentsTableRowDTO.add(paymentsTableRow.toDTO());

        PaymentsDTO expectedResult = new PaymentsDTO(37L, 0D, 0.15, paymentsTableRowDTO, 501776508L);

        assertEquals(expectedResult, result);
    }

    @Test
    void calculateCost_MOTORCYCLE() {
        String vehicleType = "MOTORCYCLE";

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

    @Test
    void findByUserNIF() {
        List<Payments> resultDatabaseMock = new ArrayList<>();
        ArrayList<PaymentsTableRow> rows = new ArrayList<>();

        rows.add(PaymentsTableRow.builder().paymentTableRowID(105L).periodStart("8:30").periodEnd("8:45").periodStart("8:30").fractionEnd("8:45").price(0.15).vehicleType("AUTOMOBILE").build());

        resultDatabaseMock.add(Payments.builder().invoice(37L).discount(0D).paymentsTableRows(rows).nif(501776508L).build());

        when(repository.getPaymentsOfUser("501776508")).thenReturn(Optional.of(resultDatabaseMock));

        Iterable<PaymentsDTO> result = service.findByUserNIF("501776508");

        ArrayList<PaymentsDTO> expectedResult = new ArrayList<>();
        ArrayList<PaymentsTableRowDTO> paymentsTableRowDTO = new ArrayList<>();
        paymentsTableRowDTO.add(PaymentsTableRowDTO.builder().paymentTableRowID(105L).periodStart("8:30").periodEnd("8:45").periodStart("8:30").fractionEnd("8:45").price(0.15).vehicleType("AUTOMOBILE").build());

        expectedResult.add(new PaymentsDTO(37L, 0D, 0.15, paymentsTableRowDTO, 501776508L));

        assertEquals(expectedResult, result);
    }

    @Test
    void getCatalog() {

        List<Payments> resultDatabaseMock = new ArrayList<>();
        ArrayList<PaymentsTableRow> rows = new ArrayList<>();

        rows.add(PaymentsTableRow.builder().paymentTableRowID(105L).periodStart("8:30").periodEnd("8:45").periodStart("8:30").fractionEnd("8:45").price(0.15).vehicleType("AUTOMOBILE").build());

        resultDatabaseMock.add(Payments.builder().invoice(37L).discount(0D).paymentsTableRows(rows).nif(501776508L).build());

        rows = new ArrayList<>();

        rows.add(PaymentsTableRow.builder().paymentTableRowID(105L).periodStart("8:30").periodEnd("8:45").periodStart("8:30").fractionEnd("8:45").price(0.15).vehicleType("AUTOMOBILE").build());

        resultDatabaseMock.add(Payments.builder().invoice(38L).discount(0D).paymentsTableRows(rows).nif(5017765095L).build());

        when(repository.getAllPayments()).thenReturn(Optional.of(resultDatabaseMock));

        Iterable<PaymentsDTO> result = service.getCatalog();

        ArrayList<PaymentsDTO> expectedResult = new ArrayList<>();
        ArrayList<PaymentsTableRowDTO> paymentsTableRowDTO = new ArrayList<>();
        paymentsTableRowDTO.add(PaymentsTableRowDTO.builder().paymentTableRowID(105L).periodStart("8:30").periodEnd("8:45").periodStart("8:30").fractionEnd("8:45").price(0.15).vehicleType("AUTOMOBILE").build());

        expectedResult.add(new PaymentsDTO(37L, 0D, 0.15, paymentsTableRowDTO, 501776508L));

        paymentsTableRowDTO = new ArrayList<>();
        paymentsTableRowDTO.add(PaymentsTableRowDTO.builder().paymentTableRowID(105L).periodStart("8:30").periodEnd("8:45").periodStart("8:30").fractionEnd("8:45").price(0.15).vehicleType("AUTOMOBILE").build());

        expectedResult.add(new PaymentsDTO(38L, 0D, 0.15, paymentsTableRowDTO, 5017765095L));


        assertEquals(expectedResult, result);
    }
}