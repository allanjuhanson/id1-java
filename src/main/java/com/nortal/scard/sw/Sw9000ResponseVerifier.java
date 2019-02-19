package com.nortal.scard.sw;

import com.nortal.scard.ApduResponseException;

import javax.smartcardio.ResponseAPDU;

public class Sw9000ResponseVerifier implements ResponseVerifier {

    @Override
    public void checkResponse(ResponseAPDU responseAPDU) {
        String sw = Integer.toHexString(responseAPDU.getSW());
        if (!SW_9000.equalsIgnoreCase(sw)) {
            throw new ApduResponseException("unexpected trailing bytes in APDU response. expected SW=" + SW_9000 + " Got SW=" + sw, sw);
        }
    }
}
