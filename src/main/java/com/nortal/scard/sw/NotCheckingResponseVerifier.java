package com.nortal.scard.sw;

import javax.smartcardio.ResponseAPDU;

public class NotCheckingResponseVerifier implements ResponseVerifier {

    @Override
    public void checkResponse(ResponseAPDU responseAPDU) {}
}
