package micromobility;

import data.GeographicPoint;
import data.ServiceID;
import data.StationID;
import data.exceptions.geographic.InvalidGeographicCoordinateException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;

public class JourneyService {
    private LocalDateTime initDate;
    private StationID origStatID;
    private int initHour;
    private long duration;
    private float distance;
    private float avgSpeed;
    private GeographicPoint origionPoint;
    private GeographicPoint endPoint;
    private LocalDateTime endDate;
    private int endHour;
    private BigDecimal importe;
    private ServiceID serviceID;
    private boolean inProgress;
    private StationID endStatID;
    private Driver driver;
    private PMVehicle vehicle;

    //??? // The constructor/s
    // All the getter methods
    // Among the setter methods must appear these ones:

    private void setOrigin(StationID stationID, GeographicPoint gp) throws InvalidGeographicCoordinateException {
        this.origionPoint = gp;
        this.origStatID  = stationID;
    }

    public void setServiceInit (StationID st,GeographicPoint gp, PMVehicle vehicle, Driver driver,LocalDateTime date) throws InvalidGeographicCoordinateException {
        setOrigin(st,gp);
        this.initDate = date;
        this.inProgress = true;
        this.initHour = initDate.getHour();
        setActors(vehicle,driver);
    }

    public void setServiceFinish (GeographicPoint loc, LocalDateTime date, float avSp, float dist, int dur, BigDecimal imp) throws InvalidGeographicCoordinateException {
        this.endDate = date;
        this.inProgress = false;
        this.endPoint = loc;
        this.distance = dist;
        this.duration = (long) dur;
        this.avgSpeed = avSp;
        this.endHour = endDate.getHour();
        this.importe= imp;
        setId();
    }

    private void setActors(PMVehicle vehicle, Driver driver){
        this.driver = driver;
        this.vehicle= vehicle;
    }

    public LocalDateTime getInitDate(){
        return this.initDate;
    }

    public void setProgress(boolean progress){
        this.inProgress = progress;
    }

    public boolean getProgress(){
        return this.inProgress;
    }

    public ServiceID getServiceID(){
        return this.serviceID;
    }
    public Driver getDriver(){
        return this.driver;
    }
    public PMVehicle getVehicle(){
        return this.vehicle;
    }
    private void calculateImport(){
        double tarifaBase = 1.5;
        double tarifaPremium = 2.0;

        LocalDateTime inicioRango = endDate.with(LocalTime.of(12, 0));
        LocalDateTime finRango = endDate.with(LocalTime.of(23, 59));

        boolean esTarifaPremium = !endDate.isBefore(inicioRango) && !endDate.isAfter(finRango);

        double tarifaPorKm = esTarifaPremium ? tarifaPremium : tarifaBase;
        this.importe = BigDecimal.valueOf((tarifaPorKm * this.distance) + (this.duration * 0.1));
    }

    public void setId(){
        Random random = new Random();
        int randomNumber = 10 + random.nextInt(90);
        this.serviceID = new ServiceID(randomNumber + "-" + endDate.toString());
    }
    public StationID getEndStatID(){
        return this.endStatID;
    }

    public GeographicPoint getEndPoint(){
        return this.endPoint;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public float getAvgSpeed() {
        return avgSpeed;
    }

    public long getDuration() {
        return duration;
    }

    public float getDistance() {
        return distance;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImport(BigDecimal importe){
        this.importe = importe;
    }

    public GeographicPoint getOriginPoint() {
        return origionPoint;
    }
}

