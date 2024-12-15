package micromobility;

import data.GeographicPoint;
import data.StationID;
import data.UserAccount;
import data.VehicleID;
import data.exceptions.geographic.InvalidGeographicCoordinateException;
import micromobility.exceptions.*;
import services.Server;
import services.smartfeatures.ArduinoMicroController;
import services.smartfeatures.QRDecoder;
import services.smartfeatures.UnbondedBTSignal;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.LocalDateTime;

public class JourneyRealizeHandler implements Server{
    private PMVehicle vehicle;
    private JourneyService service;
    private StationID stationID;
    private Driver driver;

    public JourneyRealizeHandler(Driver driver){
        this.driver = driver;
    }

    // Different input events that intervene
    // User interface input events
    public void scanQR (PMVehicle vehicle) throws ConnectException, InvalidPairingArgsException, CorruptedImgException, PMVNotAvailException, ProceduralException, InvalidGeographicCoordinateException {
        if(this.stationID == null){
            throw new ProceduralException("Station ID has not been registered correctly");
        }
        if(vehicle.getQr() == null){
            throw new CorruptedImgException("Qr code is corrupted");
        }
        checkPMVAvail(vehicle.getVehicleID());
        this.service = new JourneyService();
        service.setDate();
        service.setOrigin(stationID);
        this.vehicle.setNotAvailb();
        System.out.println("Pairing done, can start driving");
    }

    public void unPairVehicle ()
            throws ConnectException, InvalidPairingArgsException,
            PairingNotFoundException, ProceduralException
    {  }
    // Input events from the unbonded Bluetooth channel
    public void broadcastStationID (StationID stID) throws ConnectException {
        this.stationID = stID;
    }
    // Input events from the Arduino microcontroller channel
    public void startDriving () throws ConnectException, ProceduralException {

    }
    public void stopDriving ()
            throws ConnectException, ProceduralException
    {  }
    // Internal operations
    private void calculateValues (GeographicPoint gP, LocalDateTime date)
    {  }
    private void calculateImport (float dis, int dur, float avSp,
                                  LocalDateTime date)
    {  }

    @Override
    public void checkPMVAvail(VehicleID vhID) throws PMVNotAvailException, ConnectException {

    }

    @Override
    public void registerPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date) throws InvalidPairingArgsException, ConnectException {

    }

    @Override
    public void stopPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date, float avSp, float dist, int dur, BigDecimal imp) throws InvalidPairingArgsException, ConnectException {

    }

    @Override
    public void setPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date) {

    }

    @Override
    public void unPairRegisterService(JourneyService s) throws PairingNotFoundException {

    }

    @Override
    public void registerLocation(VehicleID veh, StationID st) {

    }


    //(. . .) // Setter methods for injecting dependences
}
