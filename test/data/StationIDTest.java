package data;

import data.StationID;
import data.exceptions.stationId.InvalidStationIDFormatException;
import data.exceptions.stationId.NonAlphanumericStationIDException;
import data.exceptions.stationId.NullStationIDException;
import data.exceptions.stationId.ShortStationIDException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StationIDTest {

    @Test
    public void testValidStationID() {
        assertDoesNotThrow(() -> {
            StationID stationID = new StationID("AB123");
            assertEquals("AB123", stationID.getId());
        });
    }

    @Test
    public void testNullStationID() {
        Exception exception = assertThrows(NullStationIDException.class, () -> {
            new StationID(null);
        });
        assertEquals("Station ID cannot be null or empty.", exception.getMessage());
    }

    @Test
    public void testEmptyStationID() {
        Exception exception = assertThrows(NullStationIDException.class, () -> {
            new StationID("");
        });
        assertEquals("Station ID cannot be null or empty.", exception.getMessage());
    }

    @Test
    public void testShortStationID() {
        Exception exception = assertThrows(ShortStationIDException.class, () -> {
            new StationID("A12");
        });
        assertEquals("Station ID must be at least 3 characters long.", exception.getMessage());
    }

    @Test
    public void testNonAlphanumericStationID() {
        Exception exception = assertThrows(NonAlphanumericStationIDException.class, () -> {
            new StationID("AB@12");
        });
        assertEquals("Station ID must only contain alphanumeric characters.", exception.getMessage());
    }

    @Test
    public void testInvalidFormatStationID() {
        Exception exception = assertThrows(InvalidStationIDFormatException.class, () -> {
            new StationID("AB12345");
        });
        assertEquals("Station ID must match the format: 2-3 letters followed by 3-4 digits.", exception.getMessage());
    }

    @Test
    public void testEqualsMethod() throws Exception {
        StationID id1 = new StationID("AB123");
        StationID id2 = new StationID("AB123");
        StationID id3 = new StationID("CD456");

        assertEquals(id1, id2);
        assertNotEquals(id1, id3);
    }

    @Test
    public void testToString() throws Exception {
        StationID stationID = new StationID("XY789");
        assertEquals("Station ID { ID:XY789 }", stationID.toString());
    }
}