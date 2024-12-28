package micromobility;

import data.GeographicPoint;
import data.StationID;
import data.exceptions.geographic.InvalidGeographicCoordinateException;
import micromobility.exceptions.*;
import services.ServerClass;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class JourneyRealizeHandler{
    private PMVehicle vehicle;
    private JourneyService service;
    private Station currentStation;
    private Driver driver;
    private ServerClass server;
    private boolean state;

    public JourneyRealizeHandler(Driver driver){
        this.driver = driver;
    }

    public ServerClass getServer() {
        return this.server;
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
        this.vehicle = vehicle;
        this.state = true;
        server.checkPMVAvail(vehicle.getVehicleID());
        this.service = new JourneyService();
        driver.setTripVehicle(vehicle);
        service.setServiceInit(currentStation.getId(),currentStation.getGP(),vehicle,driver, LocalDateTime.now());
        server.registerPairing(this.driver,vehicle,this.currentStation.getId(),this.currentStation.getGP(),service.getInitDate());
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
        var avgSp = calculateAvgSpeed();
        var distance = (int) calculateDistance();
        var dur = calculateDuraiton();
        var date = LocalDateTime.now();
        service.setServiceFinish(currentStation.getGP(), date, avgSp, distance,(int) dur, calculateImport(distance, (int) dur, avgSp, date));
        server.stopPairing(driver, vehicle, service.getEndStatID(), service.getEndPoint(),service.getEndDate(),service.getAvgSpeed(), service.getDistance(), (int) service.getDuration(), service.getImporte());
        service.setProgress(false);
        driver.unsetTripVehicle();
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
    private float calculateDistance()
    {
        GeographicPoint start = service.getOriginPoint();
        GeographicPoint end  = service.getEndPoint();

        return (float) start.CalculateDistance(end);
    }
    private BigDecimal calculateImport (float dis, int dur, float avSp,
                                  LocalDateTime date)
    {
        double tarifaBase = 1.5;
        double tarifaPremium = 2.0;

        LocalDateTime inicioRango = date.with(LocalTime.of(12, 0));
        LocalDateTime finRango = date.with(LocalTime.of(23, 59));

        boolean esTarifaPremium = !date.isBefore(inicioRango) && !date.isAfter(finRango);

        double tarifaPorKm = esTarifaPremium ? tarifaPremium : tarifaBase;
        return BigDecimal.valueOf((tarifaPorKm * dis) + (dur * 0.1) + (0.05 * avSp));
    }


    private boolean checkConnection(){
        return this.state;
    }

    private float calculateAvgSpeed(){
        var totalSeconds = calculateDuraiton();

        float totalHours = totalSeconds / 3600.0f;


        return calculateDistance() / totalHours;
    }

    private long calculateDuraiton(){
        Duration duration = Duration.between(service.getInitDate(), service.getEndDate());
        return duration.getSeconds() / 60; //para calcularlo en minutos
    }
    //(. . .) // Setter methods for injecting dependences
}
