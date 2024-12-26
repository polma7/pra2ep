package data;

import data.VehicleID;
import data.exceptions.vehicleid.InvalidVehicleIDFormatException;
import data.exceptions.vehicleid.NullOrEmptyVehicleIDException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleIDTest {

    @Test
    public void testValidVehicleID() {
        assertDoesNotThrow(() -> {
            VehicleID vehicleID = new VehicleID("ABC-123-45");
            assertEquals("ABC-123-45", vehicleID.getId());
        });
    }

    @Test
    public void testNullOrEmptyVehicleID() {
        Exception exception = assertThrows(NullOrEmptyVehicleIDException.class, () -> {
            new VehicleID(null);
        });
        assertEquals("Vehicle ID cannot be null or empty", exception.getMessage());

        exception = assertThrows(NullOrEmptyVehicleIDException.class, () -> {
            new VehicleID("");
        });
        assertEquals("Vehicle ID cannot be null or empty", exception.getMessage());
    }

    @Test
    public void testInvalidVehicleIDFormat() {
        InvalidVehicleIDFormatException exception = assertThrows(InvalidVehicleIDFormatException.class, () -> {
            new VehicleID("AB-123-45");
        });
        assertEquals("Vehicle ID must be in the format: AAA-123-45 (3 letters, 3 digits, 2 alphanumeric characters)", exception.getMessage());

        exception = assertThrows(InvalidVehicleIDFormatException.class, () -> {
            new VehicleID("ABC-12-45");
        });
        assertEquals("Vehicle ID must be in the format: AAA-123-45 (3 letters, 3 digits, 2 alphanumeric characters)", exception.getMessage());

        exception = assertThrows(InvalidVehicleIDFormatException.class, () -> {
            new VehicleID("ABC-123-");
        });
        assertEquals("Vehicle ID must be in the format: AAA-123-45 (3 letters, 3 digits, 2 alphanumeric characters)", exception.getMessage());

        exception = assertThrows(InvalidVehicleIDFormatException.class, () -> {
            new VehicleID("ABC-123-456");
        });
        assertEquals("Vehicle ID must be in the format: AAA-123-45 (3 letters, 3 digits, 2 alphanumeric characters)", exception.getMessage());
    }

    @Test
    public void testEqualsMethod() throws Exception, InvalidVehicleIDFormatException {
        VehicleID id1 = new VehicleID("ABC-123-45");
        VehicleID id2 = new VehicleID("ABC-123-45");
        VehicleID id3 = new VehicleID("DEF-456-78");

        assertEquals(id1, id2);
        assertNotEquals(id1, id3);
    }

    @Test
    public void testToStringMethod() throws Exception, InvalidVehicleIDFormatException {
        VehicleID vehicleID = new VehicleID("ABC-123-45");
        assertEquals("VehicleID { ABC-123-45 }", vehicleID.toString());
    }
}