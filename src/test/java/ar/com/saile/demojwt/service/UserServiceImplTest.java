package ar.com.saile.demojwt.service;

import ar.com.saile.demojwt.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    private UserServiceImpl underTest;
    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void saveUser() {

    }

    @Test
    void addRole() {

    }

    @Test
    void addRoleToUser() {

    }

    @Test
    void getUsers() {

    }

    @Test
    void saveRole() {

    }

    @Test
    void loadUserByUsername() {

    }
}