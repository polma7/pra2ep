package data;

import data.ServiceID;
import data.exceptions.serviceId.InvalidServiceIdFormatException;
import data.exceptions.serviceId.NullServiceIdException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceIDTest {

    @Test
    public void testValidServiceID() {
        assertDoesNotThrow(() -> {
            ServiceID serviceID = new ServiceID("AB-2024-12-25T14:30:00");
            assertEquals("AB-2024-12-25T14:30:00", serviceID.getId());
        });
    }

    @Test
    public void testNullServiceID() {
        Exception exception = assertThrows(NullServiceIdException.class, () -> {
            new ServiceID(null);
        });
        assertEquals("Service ID cannot be null or empty.", exception.getMessage());
    }

    @Test
    public void testEmptyServiceID() {
        Exception exception = assertThrows(NullServiceIdException.class, () -> {
            new ServiceID("");
        });
        assertEquals("Service ID cannot be null or empty.", exception.getMessage());
    }

    @Test
    public void testShortServiceID() {
        Exception exception = assertThrows(InvalidServiceIdFormatException.class, () -> {
            new ServiceID("A-2024-12-25T14:30:00");
        });
        assertEquals("Service ID must match the format: 2 letters, hyphen, and LocalDateTime (yyyy-MM-ddTHH:mm:ss).", exception.getMessage());
    }

    @Test
    public void testInvalidFormatServiceID() {
        Exception exception = assertThrows(InvalidServiceIdFormatException.class, () -> {
            new ServiceID("ABC-2024-12-25T14:30:00");
        });
        assertEquals("Service ID must match the format: 2 letters, hyphen, and LocalDateTime (yyyy-MM-ddTHH:mm:ss).", exception.getMessage());

        assertEquals("Service ID must match the format: 2 letters, hyphen, and LocalDateTime (yyyy-MM-ddTHH:mm:ss).", exception.getMessage());

        exception = assertThrows(InvalidServiceIdFormatException.class, () -> {
            new ServiceID("AB-2024-12-25T14:30");
        });
        assertEquals("Service ID must match the format: 2 letters, hyphen, and LocalDateTime (yyyy-MM-ddTHH:mm:ss).", exception.getMessage());

        exception = assertThrows(InvalidServiceIdFormatException.class, () -> {
            new ServiceID("AB-xyz-12-25T14:30:00");
        });
        assertEquals("Service ID must match the format: 2 letters, hyphen, and LocalDateTime (yyyy-MM-ddTHH:mm:ss).", exception.getMessage());
    }

    @Test
    public void testServiceIDEquals() {
        assertDoesNotThrow(() -> {
            ServiceID serviceID1 = new ServiceID("AB-2024-12-25T14:30:00");
            ServiceID serviceID2 = new ServiceID("AB-2024-12-25T14:30:00");
            assertEquals(serviceID1, serviceID2);
        });
    }

    @Test
    public void testServiceIDNotEquals() {
        assertDoesNotThrow(() -> {
            ServiceID serviceID1 = new ServiceID("AB-2024-12-25T14:30:00");
            ServiceID serviceID2 = new ServiceID("AC-2024-12-25T14:30:00");
            assertNotEquals(serviceID1, serviceID2);
        });
    }

    @Test
    public void testServiceIDToString() {
        assertDoesNotThrow(() -> {
            ServiceID serviceID = new ServiceID("AB-2024-12-25T14:30:00");
            assertEquals("Service ID { ID:AB-2024-12-25T14:30:00 }", serviceID.toString());
        });
    }
}
