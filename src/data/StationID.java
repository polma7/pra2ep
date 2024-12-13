package data;

import data.exceptions.stationId.InvalidStationIDFormatException;
import data.exceptions.stationId.NonAlphanumericStationIDException;
import data.exceptions.stationId.NullStationIDException;
import data.exceptions.stationId.ShortStationIDException;

public final class StationID {
    private final String id;
    private static final String ID_PATTERN = "[A-Z]{2,3}[0-9]{3,4}";

    public StationID(String id) throws NullStationIDException, ShortStationIDException, NonAlphanumericStationIDException, InvalidStationIDFormatException {
        if (id == null || id.isEmpty()) {
            throw new NullStationIDException("Station ID cannot be null or empty.");
        }
        if (id.length() < 5) {
            throw new ShortStationIDException("Station ID must be at least 3 characters long.");
        }
        if (!id.matches("[a-zA-Z0-9]+")) {
            throw new NonAlphanumericStationIDException("Station ID must only contain alphanumeric characters.");
        }
        if (!id.matches(ID_PATTERN)) {
            throw new InvalidStationIDFormatException("Station ID must match the format: 2-3 letters followed by 3-4 digits.");
        }
        this.id  = id;
    }
    public String getId(){
        return this.id;
    }

    @Override
    public boolean equals(Object o){
        boolean eq;
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StationID statID = (StationID) o;
        eq = statID.getId().equals(this.id);
        return eq;
    }

    @Override
    public String toString () {
        return "Station ID { ID:" + id + " }";
    }
}
