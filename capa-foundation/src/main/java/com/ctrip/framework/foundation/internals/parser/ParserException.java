package com.ctrip.framework.foundation.internals.parser;

/**
 * @author Reckless Xu
 * 2021/12/5
 */
public class ParserException extends Exception{
    public ParserException(String message) {
        super(message);
    }

    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
