package micromobility;

import data.GeographicPoint;
import data.VehicleID;

import java.awt.image.BufferedImage;

public class PMVehicle {
    private VehicleID vehicleID;
    private PMVState state;
    private GeographicPoint location;
    private int chargeLevel;
    private BufferedImage qr;

    public PMVehicle(VehicleID id, int chargeLevel, PMVState state,GeographicPoint location, BufferedImage qr){
        this.vehicleID = id;
        this.chargeLevel = chargeLevel;
        this.state = state;
        this.location = location;
        this.qr = qr;
    }
    //??? // The constructor/s
    // All the getter methods
// The setter methods to be used are only the following ones
    public VehicleID getVehicleID() {
        return vehicleID;
    }

    public PMVState getState() {
        return state;
    }

    public GeographicPoint getLocation() {
        return location;
    }

    public int getChargeLevel() {
        return chargeLevel;
    }

    public BufferedImage getQr() {
        return qr;
    }

    public void setNotAvailb () { state = PMVState.NotAvailable; }
    public void setUnderWay () { state = PMVState.UnderWay; }
    public void setAvailb () { state = PMVState.Availbale; }
    public void setLocation (GeographicPoint gP) { location = gP; }
}
