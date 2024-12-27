package services;

import data.*;
import data.exceptions.geographic.InvalidGeographicCoordinateException;
import data.exceptions.stationId.InvalidStationIDFormatException;
import data.exceptions.stationId.NonAlphanumericStationIDException;
import data.exceptions.stationId.NullStationIDException;
import data.exceptions.stationId.ShortStationIDException;
import data.exceptions.useracc.InvalidEmailFormatException;
import data.exceptions.useracc.NullOrEmptyUserAccountException;
import data.exceptions.useracc.WeakPasswordException;
import data.exceptions.vehicleid.InvalidVehicleIDFormatException;
import data.exceptions.vehicleid.NullOrEmptyVehicleIDException;
import micromobility.*;
import micromobility.exceptions.InvalidPairingArgsException;
import micromobility.exceptions.PMVNotAvailException;
import micromobility.exceptions.PairingNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ServerTest {
    private ServerClass server;
    private Station station1;
    private Station station2;
    private PMVehicle vehicle1;
    private PMVehicle vehicle2;
    private Driver driver;
    private VehicleID vehicleID1;
    private VehicleID vehicleID2;
    private StationID stationID1;
    private StationID stationID2;
    private GeographicPoint location1;
    private GeographicPoint location2;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @BeforeEach
    public void setUp() throws InvalidGeographicCoordinateException, NullOrEmptyUserAccountException, InvalidEmailFormatException, WeakPasswordException, InvalidVehicleIDFormatException, NullOrEmptyVehicleIDException, ShortStationIDException, NonAlphanumericStationIDException, InvalidStationIDFormatException, NullStationIDException {
        station1 = new Station(new StationID("AB123"), new GeographicPoint(40.4168f, -3.7038f));
        station2 = new Station(new StationID("CD456"), new GeographicPoint(41.3879f, 2.16992f));
        stationID1 = station1.getId();
        stationID2 = station2.getId();

        vehicle1 = new PMVehicle(new VehicleID("AAA-123-BC"));
        vehicle2 = new PMVehicle(new VehicleID("BBB-002-CD"));
        vehicle1.setAvailb();
        vehicle2.setAvailb();
        vehicleID1 = vehicle1.getVehicleID();
        vehicleID2 = vehicle2.getVehicleID();

        HashMap<Station, List<PMVehicle>> vehicles = new HashMap<>();
        vehicles.put(station1, new ArrayList<>(List.of(vehicle1)));
        vehicles.put(station2, new ArrayList<>(List.of(vehicle2)));

        location1 = new GeographicPoint(40.4168f, -3.7038f); // Madrid
        location2 = new GeographicPoint(41.3879f, 2.16992f); // Barcelona
        startTime = LocalDateTime.now();
        endTime = startTime.plusMinutes(30);

        UserAccount userAccount = new UserAccount("Driver1", "driver1@example.com", "1234567*");
        driver = new Driver("123456789", "BANK123", userAccount);

        server = new ServerClass(new ArrayList<>(List.of(station1, station2)), vehicles);
    }


    @Test
    public void testCheckPMVAvail() {
        assertDoesNotThrow(() -> server.checkPMVAvail(vehicleID1));
    }

    @Test
    public void testCheckPMVAvailNotAvailable() {
        vehicle1.setNotAvailb();
        assertThrows(PMVNotAvailException.class, () -> server.checkPMVAvail(vehicleID1));
    }

    @Test
    public void testRegisterPairing() {
        assertDoesNotThrow(() -> server.registerPairing(driver, vehicle1, stationID1, location1, startTime));
        assertEquals(PMVState.NotAvailable, vehicle1.getState());
    }

    @Test
    public void testRegisterPairingInvalidArgs() throws InvalidVehicleIDFormatException, NullOrEmptyVehicleIDException {
        VehicleID invalidVehicleID = new VehicleID("INVALID-VH");
        PMVehicle invalidVehicle = new PMVehicle(invalidVehicleID);
        assertThrows(InvalidPairingArgsException.class, () -> server.registerPairing(driver, invalidVehicle, stationID1, location1, startTime));
    }

    @Test
    public void testStopPairing() throws InvalidPairingArgsException, ConnectException, InvalidGeographicCoordinateException, PairingNotFoundException {
        server.registerPairing(driver, vehicle1, stationID1, location1, startTime);
        server.stopPairing(driver, vehicle1, stationID1, location2, endTime, 25.0f, 10.0f, 30, BigDecimal.valueOf(15.50));
        assertEquals(PMVState.Available, vehicle1.getState());
    }

    @Test
    public void testStopPairingInvalidArgs() {
        assertThrows(InvalidPairingArgsException.class, () -> server.stopPairing(driver, vehicle1, stationID1, location1, endTime, 25.0f, 10.0f, 30, BigDecimal.valueOf(15.50)));
    }


    @Test
    public void testUnPairRegisterService() throws InvalidPairingArgsException, ConnectException, InvalidGeographicCoordinateException, PairingNotFoundException {
        server.registerPairing(driver, vehicle1, stationID1, location1, startTime);
        JourneyService service = new JourneyService();
        service.setServiceInit(stationID1, location1, vehicle1, driver, startTime);
        server.unPairRegisterService(service);
        assertTrue(server.getActiveServices().isEmpty());
    }
}
