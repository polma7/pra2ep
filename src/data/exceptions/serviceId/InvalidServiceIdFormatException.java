package data.exceptions.serviceId;

public class InvalidServiceIdFormatException extends RuntimeException {
    public InvalidServiceIdFormatException(String message) {
        super(message);
    }
}
