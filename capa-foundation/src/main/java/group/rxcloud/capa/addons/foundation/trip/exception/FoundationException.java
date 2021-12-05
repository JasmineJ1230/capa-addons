package group.rxcloud.capa.addons.foundation.trip.exception;

/**
 * @author Reckless Xu
 * 2021/12/5
 */
public class FoundationException extends RuntimeException {

    public FoundationException(String message) {
        super(message);
    }

    public FoundationException(String message, Throwable cause) {
        super(message, cause);
    }
}