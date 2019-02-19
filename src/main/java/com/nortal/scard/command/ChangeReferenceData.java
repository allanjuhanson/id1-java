package com.nortal.scard.command;

import com.nortal.scard.Channel;
import com.nortal.scard.sw.ResponseVerifier;

import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

public class ChangeReferenceData extends CommandTransmitter {
    public static final byte CLA = 0x00;
    public static final byte INS = 0x24;
    public static final byte P1 = 0x00;

    public ChangeReferenceData(Channel channel) {
        super(channel);
    }

    public ChangeReferenceData(Channel channel, ResponseVerifier responseVerifier) {
        super(channel, responseVerifier);
    }

    public ResponseAPDU change(byte p1, byte p2, byte[] data) {
        return transmit(new CommandAPDU(CLA, INS, p1, p2, data));
    }

    public ResponseAPDU change(byte p2, byte[] data) {
        return change(P1, p2, data);
    }

}
