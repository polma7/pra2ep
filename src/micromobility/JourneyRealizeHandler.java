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
    private Station currentStation;
    private Driver driver;
    private ServerClass server;

    public JourneyRealizeHandler(Driver driver){
        this.driver = driver;
        this.server = new ServerClass();
    }

    // Different input events that intervene
    // User interface input events
    public void scanQR (PMVehicle vehicle) throws ConnectException, InvalidPairingArgsException, CorruptedImgException, PMVNotAvailException, ProceduralException, InvalidGeographicCoordinateException {
        if(this.currentStation.getId() == null){
            throw new ProceduralException("Station ID has not been registered correctly");
        }
        if(vehicle.getQr() == null){
            throw new CorruptedImgException("Qr code is corrupted");
        }
        server.checkPMVAvail(vehicle.getVehicleID());
        this.service = new JourneyService();
        this.vehicle.setNotAvailb();
        driver.setTripVehicle(vehicle);
        service.setServiceInit(currentStation.getId(),currentStation.getGP(),vehicle,driver);
        //Falta registerPairing en server
        server.registerPairing(this.driver.getUserAccount(),vehicle.getVehicleID(),this.currentStation.getId(),this.currentStation.getGP(),service.getInitDate());
        System.out.println("Pairing done, can start driving");
    }

    public void unPairVehicle () throws ConnectException, InvalidPairingArgsException,
            PairingNotFoundException, ProceduralException, InvalidGeographicCoordinateException {
        if(!checkConnection()){
            throw new ConnectException("Connection has failed");
        }
        if(service == null){
            throw new PairingNotFoundException("The pairing has not been found");
        }
        if(!service.getProgress()){
            throw new ProceduralException("The trip is not in progress, cannot stop driving");
        }
        if(!vehicle.getState().equals(PMVState.UnderWay)){
            throw new ProceduralException("The vehicle is not underway");
        }
        if(this.currentStation == null){
            throw new ProceduralException("The vehicle is not in a PMV Station");
        }
        service.setServiceFinish(currentStation.getGP());
    }
    // Input events from the unbonded Bluetooth channel
    public void broadcastStationID (StationID stID) throws ConnectException {
        this.currentStation = server.getStation(stID);
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
