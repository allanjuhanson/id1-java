package com.nortal.scard.command;

import com.nortal.scard.Channel;
import com.nortal.scard.sw.ResponseVerifier;

import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

public class Verify extends CommandTransmitter {
    public static final byte CLA = 0x00;
    public static final byte INS = 0x20;
    public static final byte P1 = 0x00;

    public Verify(Channel channel) {
        super(channel);
    }

    public Verify(Channel channel, ResponseVerifier responseVerifier) {
        super(channel, responseVerifier);
    }

    public ResponseAPDU verify(byte referenceDataQualifier) {
        return transmit(new CommandAPDU(CLA, INS, P1, referenceDataQualifier));
    }

    public ResponseAPDU verify(byte referenceDataQualifier, byte[] data) {
        return transmit(new CommandAPDU(CLA, INS, P1, referenceDataQualifier, data));
    }


}
