package com.nortal.scard.sw;

import javax.smartcardio.ResponseAPDU;

public interface ResponseVerifier {
    String SW_9000 = "9000";

    void checkResponse(ResponseAPDU responseAPDU);
}
