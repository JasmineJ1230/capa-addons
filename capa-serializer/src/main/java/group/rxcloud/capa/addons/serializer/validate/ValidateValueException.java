package group.rxcloud.capa.addons.serializer.validate;

public class ValidateValueException extends RuntimeException {

    public ValidateValueException(String message) {
        super(message);
    }

    public ValidateValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
