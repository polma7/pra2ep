package micromobility;

import data.GeographicPoint;
import data.ServiceID;
import data.StationID;
import data.exceptions.geographic.InvalidGeographicCoordinateException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
        this.serviceID = null;
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

    public void setId() {
        if (serviceID == null) {
            Random random = new Random();
            char firstLetter = (char) ('A' + random.nextInt(26));
            char secondLetter = (char) ('A' + random.nextInt(26));
            String letterPart = "" + firstLetter + secondLetter;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            String formattedDate = endDate.format(formatter);

            String id = letterPart +"-" + formattedDate;

            this.serviceID = new ServiceID(id);
        }
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
    public void setEndDate(){
        this.endDate = LocalDateTime.now();
    }
}

