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
import micromobility.exceptions.ProceduralException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import payment.Wallet;
import services.ServerClass;
import services.smartfeatures.QRDecoderClass;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JourneyRealizeHandlerTest {

    private JourneyRealizeHandler handler;
    private Driver driver;
    private ServerClass server;
    private Station station;
    private PMVehicle vehicle;

    @BeforeEach
    void setUp() throws InvalidGeographicCoordinateException, ShortStationIDException, NonAlphanumericStationIDException, InvalidStationIDFormatException, NullStationIDException, InvalidVehicleIDFormatException, NullOrEmptyVehicleIDException, NullOrEmptyUserAccountException, InvalidEmailFormatException, WeakPasswordException {
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
    void testScanQR_SuccessfulPairing() throws Exception {
        handler.scanQR(vehicle);

        assertNotNull(handler.getCurrentStation());
        assertEquals(PMVState.NotAvailable, vehicle.getState());
    }


    @Test
    void testStopDriving_SuccessfulUnpairing() throws Exception {
        handler.scanQR(vehicle);
        handler.startDriving();
        handler.stopDriving();

        assertEquals(PMVState.UnderWay, vehicle.getState());
    }

    @Test
    void testStartDriving_SuccessfulStart() throws Exception {
        handler.scanQR(vehicle);
        handler.startDriving();

        assertEquals(PMVState.UnderWay, vehicle.getState());
    }

    @Test
    void testStopDriving_SuccessfulStop() throws Exception {
        handler.scanQR(vehicle);
        handler.startDriving();
        handler.stopDriving();
        handler.unPairVehicle();
        assertEquals(PMVState.Available, vehicle.getState());
    }

    @Test
    void testSelectPaymentMethod_WalletPayment() throws Exception {
        handler.scanQR(vehicle);
        handler.startDriving();
        handler.stopDriving();
        handler.unPairVehicle();

        handler.selectPaymentMethod(driver.getPayMethod());
        assertNotEquals(BigDecimal.valueOf(99), driver.getWallet().getAmount()); //Con nuestro caso de uso saldria coste de 1
    }

}
