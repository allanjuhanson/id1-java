package com.nortal.scard.sw;

import com.nortal.scard.ApduResponseException;

import javax.smartcardio.ResponseAPDU;

public class AllowEndOfFileReachedResponseVerifier implements ResponseVerifier {

    private static final String SW_6B00 = "6B00";

    @Override
    public void checkResponse(ResponseAPDU responseAPDU) {
        String sw = Integer.toHexString(responseAPDU.getSW());
        if (!(SW_9000.equalsIgnoreCase(sw) || SW_6B00.equalsIgnoreCase(sw))) {
            throw new ApduResponseException("unexpected trailing bytes in APDU response SW=" + sw, sw);
        }
    }
}
