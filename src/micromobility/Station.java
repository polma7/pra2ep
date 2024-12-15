package micromobility;

import data.GeographicPoint;
import data.StationID;

public class Station {
    private StationID stationID;
    private GeographicPoint location;
    private boolean availability;
    private int capacity;
    private int numVehicles;

    public Station(StationID id, GeographicPoint location, int capacity, int numVehicles){
        this.stationID = id;
        this.location = location;
        this.capacity = capacity;
        this.numVehicles = numVehicles;
        availability = capacity - numVehicles > 0;
    }
}