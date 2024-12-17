package micromobility;

import data.GeographicPoint;
import data.StationID;
import data.exceptions.geographic.InvalidGeographicCoordinateException;

import java.time.LocalDateTime;

public class JourneyService {
    private LocalDateTime initDate;
    private StationID origStatID;
    private int initHour;
    private int duration;
    private float distance;
    private float avgSpeed;
    private GeographicPoint origionPoint;
    private GeographicPoint endPoint;
    private LocalDateTime endDate;
    private int endHour;
    private  float importe;
    //private ServiceID serviceID;
    private boolean inProgress;
    private StationID endStatID;
    private Driver driver;
    private PMVehicle vehicle;

    //??? // The constructor/s
    // All the getter methods
    // Among the setter methods must appear these ones:
    public void setDate(){
        initDate = LocalDateTime.now();
        initHour = initDate.getHour();
    }

    public void setOrigin(StationID stationID, GeographicPoint gp) throws InvalidGeographicCoordinateException {
        this.origionPoint = gp;
        this.origStatID  = stationID;
    }
    public void setServiceInit () throws InvalidGeographicCoordinateException {
        this.initDate = LocalDateTime.now();
        this.inProgress = true;
        this.origionPoint = new GeographicPoint(41.61674F,0.62218F);
        this.initHour = initDate.getHour();
    }
    public void setServiceFinish () throws InvalidGeographicCoordinateException {
        this.endDate = LocalDateTime.now();
        this.inProgress = false;
        this.endPoint = new GeographicPoint(41.65674F,0.66218F);
        this.distance = (float) origionPoint.CalculateDistance(endPoint);
        this.duration = initDate.compareTo(endDate);
        this.avgSpeed = distance / duration;
        this.endHour = endDate.getHour();
    }

    void setDriver(Driver driver){
        this.driver = driver;
    }

    void setVehicle(PMVehicle vehicle){
        this.vehicle = vehicle;
    }

    public LocalDateTime getInitDate(){
        return this.initDate;
    }
}

