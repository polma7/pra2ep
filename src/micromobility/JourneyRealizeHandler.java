package micromobility;

import data.GeographicPoint;
import data.StationID;
import data.exceptions.geographic.InvalidGeographicCoordinateException;
import micromobility.exceptions.*;
import services.ServerClass;

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
        service.setOrigin(station.getId(), station.getGP());
        this.vehicle.setNotAvailb();
        driver.setTripVehicle(vehicle);
        service.setDriver(driver);
        service.setVehicle(vehicle);
        //Falta registerPairing en server
        server.registerPairing(this.driver.getUserAccount(),vehicle.getVehicleID(),this.station.getId(),this.station.getGP(),service.getInitDate());
        System.out.println("Pairing done, can start driving");
    }

    public void unPairVehicle () throws ConnectException, InvalidPairingArgsException,
            PairingNotFoundException, ProceduralException
    {
        if(!checkConnection()){
            throw new ConnectException("Connection has failed");
        }

    }
    // Input events from the unbonded Bluetooth channel
    public void broadcastStationID (StationID stID) throws ConnectException {
        this.station.setId(stID);
    }

    // Input events from the Arduino microcontroller channel
    public void startDriving () throws ConnectException, ProceduralException {
        if(!vehicle.getState().equals(PMVState.NotAvailable)){
            throw new ProceduralException("PMVehicle has incorrect state");
        }
        if(service == null){
            throw new ProceduralException("An instance of JourneyService has not been created yet");
        }
        vehicle.setUnderWay();
        service.setProgress(true);

    }
    public void stopDriving ()
            throws ConnectException, ProceduralException
    {
        if(!service.getProgress()){
            throw new ProceduralException("The trip is not in progress, cannot stop driving");
        }
        if(!vehicle.getState().equals(PMVState.UnderWay)){
            throw new ProceduralException("The vehicle is not underway");
        }
    }
    // Internal operations
    private void calculateValues (GeographicPoint gP, LocalDateTime date)
    {
    }
    private void calculateImport (float dis, int dur, float avSp,
                                  LocalDateTime date)
    {  }


    private boolean checkConnection(){
        return true;
    }
    //(. . .) // Setter methods for injecting dependences
}
