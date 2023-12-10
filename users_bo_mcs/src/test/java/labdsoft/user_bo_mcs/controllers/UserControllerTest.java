package labdsoft.user_bo_mcs.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import labdsoft.user_bo_mcs.UserMcsApplication;
import labdsoft.user_bo_mcs.communication.Publish;
import labdsoft.user_bo_mcs.communication.Subscribe;
import labdsoft.user_bo_mcs.model.Email;
import labdsoft.user_bo_mcs.model.Name;
import labdsoft.user_bo_mcs.model.ParkyTransactionRequest;
import labdsoft.user_bo_mcs.model.Password;
import labdsoft.user_bo_mcs.model.PaymentDTO;
import labdsoft.user_bo_mcs.model.PaymentMethod;
import labdsoft.user_bo_mcs.model.Role;
import labdsoft.user_bo_mcs.model.TaxIdNumber;
import labdsoft.user_bo_mcs.model.User;
import labdsoft.user_bo_mcs.model.UserOnCreation;
import labdsoft.user_bo_mcs.model.UserStatus;
import labdsoft.user_bo_mcs.model.Vehicle;
import labdsoft.user_bo_mcs.model.VehicleEnergySource;
import labdsoft.user_bo_mcs.model.VehicleOnCreation;
import labdsoft.user_bo_mcs.model.VehicleType;
import labdsoft.user_bo_mcs.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(classes = UserMcsApplication.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private Subscribe sub;

    @MockBean
    private Publish pub;

    @Autowired
    private UserRepository repository;

    @BeforeEach
    public void deleteData() {
        this.repository.deleteAll();
    }

    @Test
    void createUser_Success() throws Exception {
        UserOnCreation userOnCreation = new UserOnCreation("John", "Doe", "johndoe@gmail.com", "password12cA&",
                "1234567890", 123456789, "AA-20-MM", VehicleType.AUTOMOBILE, VehicleEnergySource.ELECTRIC,
                PaymentMethod.CREDIT);
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userOnCreation)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").exists());
    }

    @Test
    void getAllUsers_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users").header("X-UserRole", "SUPERVISOR"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void addUserVehicle_Success() throws Exception {

        UserOnCreation userOnCreation = new UserOnCreation("John", "Doe", "johndoe@gmail.com", "password12cA&",
                "1234567890", 123456789, "AA-20-MM", VehicleType.AUTOMOBILE, VehicleEnergySource.ELECTRIC,
                PaymentMethod.CREDIT);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userOnCreation)))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
        Integer userId = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        VehicleOnCreation vehicleOnCreation = new VehicleOnCreation("AA-22-AA", VehicleType.AUTOMOBILE,
                VehicleEnergySource.ELECTRIC);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/{userId}/vehicle", userId)
                .header("X-UserRole", "CUSTOMER")
                .header("X-UserId", userId.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vehicleOnCreation)))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    @Test
    void changePaymentMethod_Success() throws Exception {
        UserOnCreation userOnCreation = new UserOnCreation("John", "Doe", "johndoe@gmail.com", "password12cA&",
                "1234567890", 123456789, "AA-20-MM", VehicleType.AUTOMOBILE, VehicleEnergySource.ELECTRIC,
                PaymentMethod.CREDIT);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userOnCreation)))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
        Integer userId = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        mockMvc.perform(MockMvcRequestBuilders.put("/users/{userId}/payment-method", userId)
                .header("X-UserRole", "CUSTOMER")
                .header("X-UserId", userId.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new PaymentDTO(PaymentMethod.PAYPAL))))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    @Test
    void addParkies_Success() throws Exception {
        final User customerManager = new User(new Name("Customer", "Manager"),
                new Email("customermanager@isep.ipp.pt"),
                new Password("123123x1adsascdasdqweqwe123asd123"), new TaxIdNumber(123123125), Role.CUSTOMER_MANAGER,
                new Vehicle("CC-20-CC", VehicleType.MOTORCYCLE, VehicleEnergySource.FUEL),
                PaymentMethod.NOT_DEFINED, UserStatus.ENABLED);

        this.repository.save(customerManager);

        UserOnCreation userOnCreation = new UserOnCreation("John", "Doe", "johndoe@gmail.com", "password12cA&",
                "1234567890", 123456789, "AA-20-MM", VehicleType.AUTOMOBILE, VehicleEnergySource.ELECTRIC,
                PaymentMethod.CREDIT);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userOnCreation)))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
        Integer userId = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        List<Long> userIdsToAdd = new ArrayList<>();

        userIdsToAdd.add(Long.valueOf(userId + ""));

        Integer amountToAdd = 50;
        ParkyTransactionRequest request = new ParkyTransactionRequest(userIdsToAdd, amountToAdd, "Some Reason");
        mockMvc.perform(MockMvcRequestBuilders.post("/users/parkies")
                .header("X-UserRole", "CUSTOMER_MANAGER")
                .header("X-UserId", customerManager.getUserId() + "")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        MvcResult getResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/users/parkies/{userId}", userId))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Integer newParkiesAmount = JsonPath.read(getResult.getResponse().getContentAsString(), "$.parkies");

        assertEquals(newParkiesAmount, amountToAdd);

    }

}
