package com.nortal.scard.command;

import com.nortal.scard.Channel;
import com.nortal.scard.sw.ResponseVerifier;

import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

public class ResetRetryCounter extends CommandTransmitter {
    public static final byte CLA = 0x00;
    public static final byte INS = 0x2C;
    public static final byte P1_UNBLOCK_AND_CHANGE = 0x02;
    public static final byte P1_UNBLOCK = 0x03;

    public ResetRetryCounter(Channel channel) {
        super(channel);
    }

    public ResetRetryCounter(Channel channel, ResponseVerifier responseVerifier) {
        super(channel, responseVerifier);
    }

    public ResponseAPDU reset(byte p1, byte p2, byte[] data) {
        return transmit(new CommandAPDU(CLA, INS, p1, p2, data));
    }

    public ResponseAPDU reset(byte p1, byte p2) {
        return transmit(new CommandAPDU(CLA, INS, p1, p2));
    }

    public ResponseAPDU resetAndChange(byte referenceDataQualifier, byte[] newReferenceData) {
        return reset(P1_UNBLOCK_AND_CHANGE, referenceDataQualifier, newReferenceData);
    }

    public ResponseAPDU reset(byte referenceDataQualifier) {
        return reset(P1_UNBLOCK, referenceDataQualifier);
    }
}
