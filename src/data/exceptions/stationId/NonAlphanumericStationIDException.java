package data.exceptions.stationId;

public class NonAlphanumericStationIDException extends Exception {
    public NonAlphanumericStationIDException(String message) {
        super(message);
    }
}
