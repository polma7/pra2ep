package data;

import data.exceptions.vehicleid.InvalidVehicleIDFormatException;
import data.exceptions.vehicleid.NullOrEmptyVehicleIDException;

public final class VehicleID {
    private final String id;
    private static final String ID_PATTERN = "^[A-Za-z]{3}-\\d{3}-[A-Za-z0-9]{2}$";

    public VehicleID(String id) throws NullOrEmptyVehicleIDException, InvalidVehicleIDFormatException {
        if (id == null || id.isEmpty()) {
            throw new NullOrEmptyVehicleIDException("Vehicle ID cannot be null or empty");
        }
        if (!id.matches(ID_PATTERN)) {
            throw new InvalidVehicleIDFormatException("Vehicle ID must be in the format: AAA-123-45 (3 letters, 3 digits, 2 alphanumeric characters)");
        }        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    @Override
    public boolean equals(Object o ){
        boolean eq;
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleID vId = (VehicleID) o;
        eq = vId.getId().equals(this.id);
        return eq;
    }

    @Override
    public String toString(){
        return "VehicleID { " + this.id + " }";
    }
}
