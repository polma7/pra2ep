package services.smartfeatures;

import data.GeographicPoint;
import data.StationID;
import data.UserAccount;
import data.exceptions.geographic.InvalidGeographicCoordinateException;
import data.exceptions.stationId.InvalidStationIDFormatException;
import data.exceptions.stationId.NonAlphanumericStationIDException;
import data.exceptions.stationId.NullStationIDException;
import data.exceptions.stationId.ShortStationIDException;
import data.exceptions.useracc.InvalidEmailFormatException;
import data.exceptions.useracc.NullOrEmptyUserAccountException;
import data.exceptions.useracc.WeakPasswordException;
import micromobility.Driver;
import micromobility.JourneyRealizeHandler;
import micromobility.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import payment.Wallet;

import java.math.BigDecimal;
import java.net.ConnectException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UnboundedBTSignalTest {
    private UnboundedBTSignalClass unboundedSignal;
    private JourneyRealizeHandler handler;
    private Driver driver;
    private Wallet wallet;
    private UserAccount account;
    private Station station;
    private StationID stID;
    private GeographicPoint geographicPoint;

    @BeforeEach
    void setup () throws    NullOrEmptyUserAccountException, InvalidEmailFormatException,
                            WeakPasswordException, ShortStationIDException, NonAlphanumericStationIDException,
                            InvalidStationIDFormatException, NullStationIDException, InvalidGeographicCoordinateException {
        BigDecimal amount = new BigDecimal(10);
        account = new UserAccount("Dummy", "mail@mail.com", "1234*56789");
        wallet = new Wallet(amount);
        driver = new Driver("1", wallet, account);
        handler = new JourneyRealizeHandler(driver);

        stID = new StationID("AB123");
        geographicPoint = new GeographicPoint(0, 0);
        station = new Station(stID, geographicPoint);
        handler.setCurrentStation(station);
    }

    @Test
    void testBTbroadcastSuccess () {
        unboundedSignal = new UnboundedBTSignalClass(handler);
        assertDoesNotThrow(() -> unboundedSignal.BTbroadcast());
    }

    @Test
    void testBTbroadcastFailure () {
        unboundedSignal = new UnboundedBTSignalClass(handler, true);
        assertThrows(ConnectException.class, () -> {
            unboundedSignal.BTbroadcast();
        });
    }
}
