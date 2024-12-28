package services.smartfeatures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import micromobility.exceptions.ProceduralException;
import services.exceptions.PMVPhisicalException;

import static org.junit.jupiter.api.Assertions.*;

public class ArduinoMicroControllerTest {
    private ArduinoMicroControllerClass controller;

    public void setUp() {
        controller = new ArduinoMicroControllerClass(false, false, false);
    }

    @Test
    public void testSetBTconnection() {
        assertDoesNotThrow(() -> controller.setBTconnection());
        assertTrue(controller.isBTConnected());
    }

    @Test
    public void testSetBTconnectionFails() {
        controller = new ArduinoMicroControllerClass(true, false, false);
        assertThrows(ConnectException.class, () -> controller.setBTconnection());
        assertFalse(controller.isBTConnected());
    }

    @Test
    public void testStartDriving() throws ConnectException {
        controller.setBTconnection();
        assertDoesNotThrow(() -> controller.startDriving());
        assertTrue(controller.isVehicleDriving());
    }

    @Test
    public void testStartDrivingWithoutBTConnection() {
        assertThrows(ConnectException.class, () -> controller.startDriving());
        assertFalse(controller.isVehicleDriving());
    }

    @Test
    public void testStartDrivingFails() throws ConnectException {
        controller = new ArduinoMicroControllerClass(false, true, false);
        controller.setBTconnection();

        Exception exception = assertThrows(Exception.class, () -> controller.startDriving());
        assertTrue(
                exception instanceof PMVPhisicalException ||
                        exception instanceof ProceduralException
        );
        assertFalse(controller.isVehicleDriving());
    }

    @Test
    public void testStopDriving() throws ConnectException, PMVPhisicalException, ProceduralException {
        controller.setBTconnection();
        controller.startDriving();
        assertDoesNotThrow(() -> controller.stopDriving());
        assertFalse(controller.isVehicleDriving());
    }

    @Test
    public void testStopDrivingWithoutBTConnection() {
        assertThrows(ConnectException.class, () -> controller.stopDriving());
        assertFalse(controller.isVehicleDriving());
    }

    @Test
    public void testStopDrivingFails() throws ConnectException, PMVPhisicalException, ProceduralException {
        controller = new ArduinoMicroControllerClass(false, false, true);
        controller.setBTconnection();
        controller.startDriving();

        Exception exception = assertThrows(Exception.class, () -> controller.stopDriving());
        assertTrue(
                exception instanceof PMVPhisicalException ||
                        exception instanceof ProceduralException
        );
        assertTrue(controller.isVehicleDriving());
    }

    @Test
    public void testUndoBTconnection() throws ConnectException {
        controller.setBTconnection();
        controller.undoBTconnection();
        assertFalse(controller.isBTConnected());
    }

    @Test
    public void testUndoBTconnectionWithoutPriorConnection() {
        controller.undoBTconnection();
        assertFalse(controller.isBTConnected());
    }
}
