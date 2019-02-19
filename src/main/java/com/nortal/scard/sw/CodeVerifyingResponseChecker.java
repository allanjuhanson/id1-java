package com.nortal.scard.sw;

import com.nortal.scard.ApduResponseException;
import com.nortal.scard.CodeVerificationException;

import javax.smartcardio.ResponseAPDU;

public class CodeVerifyingResponseChecker implements ResponseVerifier {

    private static final String SW1_63 = "63";
    private static final String SW_6983 = "6983";
    private static final String SW_6984 = "6984";

    @Override
    public void checkResponse(ResponseAPDU responseAPDU) {
        String sw = Integer.toHexString(responseAPDU.getSW());
        String sw1 = sw.substring(0, 2);

        if (SW1_63.equalsIgnoreCase(sw1) || SW_6983.equalsIgnoreCase(sw) || SW_6984.equalsIgnoreCase(sw)) {
            throw new CodeVerificationException("Code verification failed SW=" + sw, sw);
        }

        else if (!SW_9000.equalsIgnoreCase(sw)) {
            throw new ApduResponseException("unexpected trailing bytes in APDU response. expected SW=" + SW_9000 + " Got SW=" + sw, sw);
        }
    }
}
