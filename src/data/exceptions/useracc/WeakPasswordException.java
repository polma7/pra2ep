package data.exceptions.useracc;

public class WeakPasswordException extends Exception {
    public WeakPasswordException(String message) {
        super(message);
    }
}
