package micromobility;

import data.GeographicPoint;
import data.StationID;
import data.UserAccount;
import data.VehicleID;
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
import micromobility.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import payment.Wallet;
import services.ServerClass;
import services.smartfeatures.QRDecoderClass;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JourneyRealizeHandlerFailTest {

    private JourneyRealizeHandler handler;
    private Driver driver;
    private PMVehicle vehicle;
    private Station station;
    private ServerClass server;

    @BeforeEach
    public void setUp() throws NullOrEmptyUserAccountException, InvalidEmailFormatException, WeakPasswordException, ShortStationIDException, NonAlphanumericStationIDException, InvalidStationIDFormatException, NullStationIDException, InvalidVehicleIDFormatException, NullOrEmptyVehicleIDException, InvalidGeographicCoordinateException {
        UserAccount userAccount = new UserAccount("Driver1", "driver1@example.com", "1234567*");
        driver = new Driver("123456789", new Wallet(BigDecimal.valueOf(100L)), userAccount);


        station = new Station(new StationID("CD456"), new GeographicPoint(41.3879f, 2.16992f));

        BufferedImage validImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        vehicle = new PMVehicle(new VehicleID("AAA-123-BC"), validImage);
        vehicle.setAvailb();

        HashMap<Station, List<PMVehicle>> vehicles = new HashMap<>();
        vehicles.put(station, new ArrayList<>(List.of(vehicle)));
        server = new ServerClass(new ArrayList<>(List.of(station)), vehicles);

        handler = new JourneyRealizeHandler(driver);
        handler.setCurrentStation(station);
        handler.setServer(server);
        handler.setDecoder(new QRDecoderClass());
    }

    @Test
    public void testScanQR_throwsProceduralException_whenStationIdIsNull() {
        station.setId(null);
        handler.setCurrentStation(station);
        ProceduralException exception = assertThrows(ProceduralException.class, () -> {
            handler.scanQR(vehicle);
        });

        assertEquals("Station ID has not been registered correctly", exception.getMessage());
    }

    @Test
    public void testUnPairVehicle_throwsConnectException_whenNotConnected() {
        handler.setCurrentStation(station);
        handler.setServer(null);

        ConnectException exception = assertThrows(ConnectException.class, () -> {
            handler.unPairVehicle();
        });

        assertEquals("Connection has failed", exception.getMessage());
    }

    @Test
    public void testUnPairVehicle_throwsPairingNotFoundException_whenServiceIsNull() throws InvalidGeographicCoordinateException, CorruptedImgException, InvalidPairingArgsException, ProceduralException, PMVNotAvailException, ConnectException {
        handler.scanQR(vehicle);
        handler.startDriving();
        handler.stopDriving();
        handler.setService(null);
        PairingNotFoundException exception = assertThrows(PairingNotFoundException.class, () -> {
            handler.unPairVehicle();
        });

        assertEquals("The pairing has not been found", exception.getMessage());
    }

    @Test
    public void testStartDriving_throwsProceduralException_whenVehicleIsNotAvailable() throws InvalidGeographicCoordinateException, CorruptedImgException, InvalidPairingArgsException, ProceduralException, PMVNotAvailException, ConnectException {
        handler.scanQR(vehicle);
        handler.getVehicle().setUnderWay();
        ProceduralException exception = assertThrows(ProceduralException.class, () -> {
            handler.startDriving();
        });

        assertEquals("PMVehicle has incorrect state", exception.getMessage());
    }

    @Test
    public void testStopDriving_throwsProceduralException_whenTripIsNotInProgress() throws InvalidGeographicCoordinateException, CorruptedImgException, InvalidPairingArgsException, ProceduralException, PMVNotAvailException, ConnectException {
        handler.scanQR(vehicle);
        handler.startDriving();
        JourneyService service = new JourneyService();
        handler.setService(service);

        ProceduralException exception = assertThrows(ProceduralException.class, () -> {
            handler.stopDriving();
        });

        assertEquals("The trip is not in progress, cannot stop driving", exception.getMessage());
    }

    @Test
    public void testStopDriving_throwsProceduralException_whenVehicleIsNotUnderway() throws InvalidGeographicCoordinateException, CorruptedImgException, InvalidPairingArgsException, ProceduralException, PMVNotAvailException, ConnectException {
        handler.scanQR(vehicle);
        handler.startDriving();
        handler.getVehicle().setAvailb();

        ProceduralException exception = assertThrows(ProceduralException.class, () -> {
            handler.stopDriving();
        });

        assertEquals("The vehicle is not underway", exception.getMessage());
    }

    @Test
    public void testUnPairVehicle_throwsProceduralException_whenVehicleIsNotUnderway() throws InvalidGeographicCoordinateException, CorruptedImgException, InvalidPairingArgsException, ProceduralException, PMVNotAvailException, ConnectException {
        handler.scanQR(vehicle);
        handler.startDriving();
        handler.stopDriving();
        handler.getVehicle().setNotAvailb();
        ProceduralException exception = assertThrows(ProceduralException.class, () -> {
            handler.unPairVehicle();
        });

        assertEquals("The vehicle is not underway", exception.getMessage());
    }

    @Test
    public void testUnPairVehicle_throwsProceduralException_whenCurrentPlaceIsNotAStation() throws InvalidGeographicCoordinateException, CorruptedImgException, InvalidPairingArgsException, ProceduralException, PMVNotAvailException, ConnectException {
        handler.scanQR(vehicle);
        handler.startDriving();
        handler.stopDriving();
        handler.setCurrentStation(null);
        ProceduralException exception = assertThrows(ProceduralException.class, () -> {
            handler.unPairVehicle();
        });

        assertEquals("The vehicle is not in a PMV Station", exception.getMessage());
    }




}
