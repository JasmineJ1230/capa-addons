package group.rxcloud.capa.addons.serialzer.exception;

import java.text.ParseException;

public class ParseRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ParseRuntimeException(ParseException ex) {
        super(ex);
    }

}
