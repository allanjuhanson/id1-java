package com.nortal.scard;

public class CodeVerificationException extends ApduResponseException {
    public CodeVerificationException(String s, String sw) {
        super(s, sw);
    }
}
