package micromobility;

import data.GeographicPoint;
import data.StationID;
import data.UserAccount;
import data.VehicleID;
import data.exceptions.geographic.InvalidGeographicCoordinateException;
import micromobility.exceptions.*;
import services.Server;
import services.ServerClass;
import services.smartfeatures.ArduinoMicroController;
import services.smartfeatures.QRDecoder;
import services.smartfeatures.UnbondedBTSignal;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.LocalDateTime;

public class JourneyRealizeHandler{
    private PMVehicle vehicle;
    private JourneyService service;
    private Station station;
    private Driver driver;
    private ServerClass server;

    public JourneyRealizeHandler(Driver driver){
        this.driver = driver;
        this.server = new ServerClass();
    }

    // Different input events that intervene
    // User interface input events
    public void scanQR (PMVehicle vehicle) throws ConnectException, InvalidPairingArgsException, CorruptedImgException, PMVNotAvailException, ProceduralException, InvalidGeographicCoordinateException {
        if(this.station.getId() == null){
            throw new ProceduralException("Station ID has not been registered correctly");
        }
        if(vehicle.getQr() == null){
            throw new CorruptedImgException("Qr code is corrupted");
        }
        server.checkPMVAvail(vehicle.getVehicleID());
        this.service = new JourneyService();
        service.setDate();
        service.setOrigin(station.getId(),station.getGP());
        this.vehicle.setNotAvailb();
        driver.setTripVehicle(vehicle);
        service.setDriver(driver);
        service.setVehicle(vehicle);
        //Falta registerPairing en server
        server.registerPairing(this.driver.getUserAccount(),vehicle.getVehicleID(),this.station.getId(),this.station.getGP(),service.getInitDate());
        System.out.println("Pairing done, can start driving");
    }

    public void unPairVehicle ()
            throws ConnectException, InvalidPairingArgsException,
            PairingNotFoundException, ProceduralException
    {  }
    // Input events from the unbonded Bluetooth channel
    public void broadcastStationID (StationID stID) throws ConnectException {
        this.station.setId(stID);
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



    //(. . .) // Setter methods for injecting dependences
}
