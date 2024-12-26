package data;

import data.UserAccount;
import data.exceptions.useracc.InvalidEmailFormatException;
import data.exceptions.useracc.NullOrEmptyUserAccountException;
import data.exceptions.useracc.WeakPasswordException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserAccTest {

    @Test
    public void testValidUserAccount() {
        assertDoesNotThrow(() -> {
            UserAccount user = new UserAccount("JohnDoe", "john.doe@example.com", "Password@123");
            assertEquals("JohnDoe", user.getUsername());
            assertEquals("john.doe@example.com", user.getMail());
        });
    }

    @Test
    public void testNullOrEmptyUsername() {
        Exception exception = assertThrows(NullOrEmptyUserAccountException.class, () -> {
            new UserAccount(null, "john.doe@example.com", "Password@123");
        });
        assertEquals("Username cannot be null or empty", exception.getMessage());

        exception = assertThrows(NullOrEmptyUserAccountException.class, () -> {
            new UserAccount("", "john.doe@example.com", "Password@123");
        });
        assertEquals("Username cannot be null or empty", exception.getMessage());
    }

    @Test
    public void testNullOrEmptyEmail() {
        Exception exception = assertThrows(NullOrEmptyUserAccountException.class, () -> {
            new UserAccount("JohnDoe", null, "Password@123");
        });
        assertEquals("Mail cannot be null or empty", exception.getMessage());

        exception = assertThrows(NullOrEmptyUserAccountException.class, () -> {
            new UserAccount("JohnDoe", "", "Password@123");
        });
        assertEquals("Mail cannot be null or empty", exception.getMessage());
    }

    @Test
    public void testNullOrEmptyPassword() {
        Exception exception = assertThrows(NullOrEmptyUserAccountException.class, () -> {
            new UserAccount("JohnDoe", "john.doe@example.com", null);
        });
        assertEquals("Password cannot be null or empty", exception.getMessage());

        exception = assertThrows(NullOrEmptyUserAccountException.class, () -> {
            new UserAccount("JohnDoe", "john.doe@example.com", "");
        });
        assertEquals("Password cannot be null or empty", exception.getMessage());
    }

    @Test
    public void testInvalidEmailFormat() {
        Exception exception = assertThrows(InvalidEmailFormatException.class, () -> {
            new UserAccount("JohnDoe", "invalid-email", "Password@123");
        });
        assertEquals("Invalid email format", exception.getMessage());
    }

    @Test
    public void testWeakPassword() {
        Exception exception = assertThrows(WeakPasswordException.class, () -> {
            new UserAccount("JohnDoe", "john.doe@example.com", "password"); // No tiene 8 caracteres ni especiales
        });
        assertEquals("Password is too weak. It must be at least 8 characters and contain a special character.", exception.getMessage());

        exception = assertThrows(WeakPasswordException.class, () -> {
            new UserAccount("JohnDoe", "john.doe@example.com", "WeakPass"); // No tiene carácter especial
        });
        assertEquals("Password is too weak. It must be at least 8 characters and contain a special character.", exception.getMessage());
    }

    @Test
    public void testEqualsMethod() throws Exception {
        UserAccount acc1 = new UserAccount("JohnDoe", "john.doe@example.com", "Password@123");
        UserAccount acc2 = new UserAccount("JohnDoe", "john.doe@example.com", "Password@123");
        UserAccount acc3 = new UserAccount("JaneDoe", "jane.doe@example.com", "Password@123");

        assertEquals(acc1, acc2);
        assertNotEquals(acc1, acc3);
    }

    @Test
    public void testToStringMethod() throws Exception {
        UserAccount user = new UserAccount("JohnDoe", "john.doe@example.com", "Password@123");
        assertEquals("User Account { Username = JohnDoe, Mail = john.doe@example.com }", user.toString());
    }
}