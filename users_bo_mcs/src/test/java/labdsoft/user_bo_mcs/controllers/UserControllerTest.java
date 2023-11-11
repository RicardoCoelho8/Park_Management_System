package labdsoft.user_bo_mcs.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import labdsoft.user_bo_mcs.UserMcsApplication;
import labdsoft.user_bo_mcs.communication.Publish;
import labdsoft.user_bo_mcs.communication.Subscribe;
import labdsoft.user_bo_mcs.model.Role;
import labdsoft.user_bo_mcs.model.UserOnCreation;
import labdsoft.user_bo_mcs.model.VehicleOnCreation;
import labdsoft.user_bo_mcs.model.VehicleType;
import labdsoft.user_bo_mcs.repositories.UserRepository;


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
        UserOnCreation userOnCreation = new UserOnCreation("John", "Doe", "johndoe@gmail.com", "password12cA&", "1234567890", 123456789, Role.CUSTOMER);
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userOnCreation)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").exists());
    }

    @Test
    void getAllUsers_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void addUserVehicle_Success() throws Exception {

        UserOnCreation userOnCreation = new UserOnCreation("John", "Doe", "johndoe@gmail.com", "password12cA&", "1234567890", 123456789, Role.CUSTOMER);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userOnCreation)))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
        Integer userId = JsonPath.read(result.getResponse().getContentAsString(), "$.id");


        VehicleOnCreation vehicleOnCreation = new VehicleOnCreation("AA-22-AA", VehicleType.ELECTRIC);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/{userId}/vehicle", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vehicleOnCreation)))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }
}
