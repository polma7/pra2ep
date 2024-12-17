package data.tests;
import data.GeographicPoint;
import data.exceptions.geographic.InvalidGeographicCoordinateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GeographicPointTest {

    @Test
    public void testValidCoordinates() {
        assertDoesNotThrow(() -> new GeographicPoint(45.0f, 90.0f));
        assertDoesNotThrow(() -> new GeographicPoint(-45.0f, -90.0f));
        assertDoesNotThrow(() -> new GeographicPoint(0.0f, 0.0f));
    }

    @Test
    public void testInvalidLatitudeTooLow() {
        InvalidGeographicCoordinateException exception = assertThrows(
                InvalidGeographicCoordinateException.class,
                () -> new GeographicPoint(-91.0f, 0.0f)
        );
        assertEquals("Latitude must be between -90 and 90: -91.0", exception.getMessage());
    }

    @Test
    public void testInvalidLatitudeTooHigh() {
        InvalidGeographicCoordinateException exception = assertThrows(
                InvalidGeographicCoordinateException.class,
                () -> new GeographicPoint(91.0f, 0.0f)
        );
        assertEquals("Latitude must be between -90 and 90: 91.0", exception.getMessage());
    }

    @Test
    public void testInvalidLongitudeTooLow() {
        InvalidGeographicCoordinateException exception = assertThrows(
                InvalidGeographicCoordinateException.class,
                () -> new GeographicPoint(0.0f, -181.0f)
        );
        assertEquals("Longitude must be between -180 and 180: -181.0", exception.getMessage());
    }

    @Test
    public void testInvalidLongitudeTooHigh() {
        InvalidGeographicCoordinateException exception = assertThrows(
                InvalidGeographicCoordinateException.class,
                () -> new GeographicPoint(0.0f, 181.0f)
        );
        assertEquals("Longitude must be between -180 and 180: 181.0", exception.getMessage());
    }

    @Test
    public void testEqualsMethod() throws InvalidGeographicCoordinateException {
        GeographicPoint point1 = new GeographicPoint(40.0f, 50.0f);
        GeographicPoint point2 = new GeographicPoint(40.0f, 50.0f);
        GeographicPoint point3 = new GeographicPoint(-40.0f, -50.0f);

        assertEquals(point1, point2);
        assertNotEquals(point1, point3);
    }

    @Test
    public void testCalculateDistance() throws InvalidGeographicCoordinateException, InvalidGeographicCoordinateException {
        GeographicPoint point1 = new GeographicPoint(40.7128f, -74.0060f); // Nueva York
        GeographicPoint point2 = new GeographicPoint(34.0522f, -118.2437f); // Los Ángeles

        double distance = point1.CalculateDistance(point2);

        assertEquals(3935, distance, 1.0);
    }

    @Test
    public void testToString() throws InvalidGeographicCoordinateException {
        GeographicPoint point = new GeographicPoint(40.7128f, -74.0060f); // Nueva York
        assertEquals("Geographic point {latitude='40.7128'longitude='-74.0060'}", point.toString());
    }
}
