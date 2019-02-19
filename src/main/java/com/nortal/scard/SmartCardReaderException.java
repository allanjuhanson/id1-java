package com.nortal.scard;

public class SmartCardReaderException extends RuntimeException {

    public SmartCardReaderException(Throwable t) {
        super(t);
    }

    public SmartCardReaderException(String s, Throwable t) {
        super(s, t);
    }

    public SmartCardReaderException(String s) {
        super(s);
    }
}
