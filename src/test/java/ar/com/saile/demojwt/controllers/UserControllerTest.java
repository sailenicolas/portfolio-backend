package ar.com.saile.demojwt.controllers;

import ar.com.saile.demojwt.domain.AppRole;
import ar.com.saile.demojwt.domain.AppUser;
import ar.com.saile.demojwt.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link UserController#getUsers()}
     */
    @Test
    void testGetUsers() throws Exception {

        when(this.userService.getUsers()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/users");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link UserController#getUsers()}
     */
    @Test
    void testGetUsers2() throws Exception {

        when(this.userService.getUsers()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/users");
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void addUser() {

    }

    @Test
    void addRole() {

    }

    @Test
    void getUsers() {

    }

    @Test
    void roleToUser() {

    }

    /**
     * Method under test: {@link UserController#addRole(AppRole)}
     */
    @Test
    void testAddRole() throws Exception {

        AppRole appRole = new AppRole();
        appRole.setId(123L);
        appRole.setName("Name");
        when(this.userService.saveRole((AppRole) any())).thenReturn(appRole);

        AppRole appRole1 = new AppRole();
        appRole1.setId(123L);
        appRole1.setName("Name");
        String content = (new ObjectMapper()).writeValueAsString(appRole1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"id\":123,\"name\":\"Name\"}"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost"));
    }

    /**
     * Method under test: {@link UserController#addUser(AppUser)}
     */
    @Test
    void testAddUser() throws Exception {

        AppUser appUser = new AppUser();
        appUser.setEmail("jane.doe@example.org");
        appUser.setId(123L);
        appUser.setName("Name");
        appUser.setPassword("iloveyou");
        appUser.setRoleCollection(new ArrayList<>());
        appUser.setUsername("janedoe");
        when(this.userService.saveUser((AppUser) any())).thenReturn(appUser);

        AppUser appUser1 = new AppUser();
        appUser1.setEmail("jane.doe@example.org");
        appUser1.setId(123L);
        appUser1.setName("Name");
        appUser1.setPassword("iloveyou");
        appUser1.setRoleCollection(new ArrayList<>());
        appUser1.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(appUser1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"name\":\"Name\",\"email\":\"jane.doe@example.org\",\"username\":\"janedoe\",\"password\":\"iloveyou\","
                                        + "\"roleCollection\":[]}"));
    }

    /**
     * Method under test: {@link UserController#RoleToUser(RoleToUser)}
     */
    @Test
    void testRoleToUser() throws Exception {

        doNothing().when(this.userService).addRoleToUser((String) any(), (String) any());

        RoleToUser roleToUser = new RoleToUser();
        roleToUser.setRoleNane("Role Nane");
        roleToUser.setUserName("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(roleToUser);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/roleToUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}