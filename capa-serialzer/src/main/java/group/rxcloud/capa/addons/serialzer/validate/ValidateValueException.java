package group.rxcloud.capa.addons.serialzer.validate;

public class ValidateValueException extends RuntimeException {

    public ValidateValueException(String message) {
        super(message);
    }

    public ValidateValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
