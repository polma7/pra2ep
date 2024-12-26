package micromobility;

import data.GeographicPoint;
import data.ServiceID;
import data.StationID;
import data.exceptions.geographic.InvalidGeographicCoordinateException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

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
    private double importe;
    private ServiceID serviceID;
    private boolean inProgress;
    private StationID endStatID;
    private Driver driver;
    private PMVehicle vehicle;

    //??? // The constructor/s
    // All the getter methods
    // Among the setter methods must appear these ones:

    public void setOrigin(StationID stationID, GeographicPoint gp) throws InvalidGeographicCoordinateException {
        this.origionPoint = gp;
        this.origStatID  = stationID;
    }
    public void setServiceInit (StationID st,GeographicPoint gp, PMVehicle vehicle, Driver driver) throws InvalidGeographicCoordinateException {
        setOrigin(st,gp);
        this.initDate = LocalDateTime.now();
        this.inProgress = true;
        this.initHour = initDate.getHour();
        setActors(vehicle,driver);
    }
    public void setServiceFinish (GeographicPoint gp) throws InvalidGeographicCoordinateException {
        this.endDate = LocalDateTime.now();
        this.inProgress = false;
        this.endPoint = gp;
        this.distance = (float) origionPoint.CalculateDistance(endPoint);
        this.duration = ChronoUnit.MINUTES.between(initDate, endDate);
        this.avgSpeed = distance / duration;
        this.endHour = endDate.getHour();
        calculateImport();
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

    private void calculateImport(){
        double tarifaBase = 1.5;
        double tarifaPremium = 2.0;

        LocalDateTime inicioRango = endDate.with(LocalTime.of(12, 0));
        LocalDateTime finRango = endDate.with(LocalTime.of(23, 59));

        boolean esTarifaPremium = !endDate.isBefore(inicioRango) && !endDate.isAfter(finRango);

        double tarifaPorKm = esTarifaPremium ? tarifaPremium : tarifaBase;
        this.importe = (tarifaPorKm * this.distance) + (this.duration * 0.1);
    }

}

