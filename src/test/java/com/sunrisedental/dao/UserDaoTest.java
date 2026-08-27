package com.sunrisedental.dao;

import com.sunrisedental.model.User;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserDaoTest {
    private final UserDao userDao = new UserDao();
    private static final String TEST_USER = "test_doc";
    private static final String TEST_PASS = "pass1234";

    @Test
    @Order(1)
    @DisplayName("Test Authenticate Null Values Should Return Null")
    void testAuthenticateNull() {
        assertNull(userDao.authenticate(null, "somepass"));
        assertNull(userDao.authenticate("someuser", null));
        assertNull(userDao.authenticate(null, null));
    }

    @Test
    @Order(2)
    @DisplayName("Test Register New User")
    void testRegisterUser() {
        if (!userDao.userExists(TEST_USER)) {
            User user = new User(TEST_USER, TEST_PASS, "Test Doctor", "STAFF");
            boolean registered = userDao.registerUser(user);
            assertTrue(registered, "User should register successfully");
        }
    }

    @Test
    @Order(3)
    @DisplayName("Test Authenticate Valid Credentials")
    void testAuthenticateValid() {
        User user = userDao.authenticate(TEST_USER, TEST_PASS);
        assertNotNull(user, "User should authenticate successfully with correct credentials");
        assertEquals(TEST_USER, user.getUsername());
    }

    @Test
    @Order(4)
    @DisplayName("Test Authenticate Invalid Password")
    void testAuthenticateInvalidPassword() {
        User user = userDao.authenticate(TEST_USER, "wrongpassword");
        assertNull(user, "User should fail to authenticate with wrong password");
    }
}
