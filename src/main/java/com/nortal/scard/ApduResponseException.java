package com.nortal.scard;

public class ApduResponseException extends SmartCardReaderException {

    private String sw;

    public ApduResponseException(String s, String sw) {
        super(s);
        this.sw = sw;
    }

    public String getSw() {
        return sw;
    }
}
