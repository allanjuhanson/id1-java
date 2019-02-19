package com.nortal.scard.command;

import com.nortal.scard.Channel;
import com.nortal.scard.sw.ResponseVerifier;

import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

import static com.nortal.scard.Utils.concat;

public class InternalAuthenticate extends CommandTransmitter {
    public static final byte CLA = 0x00;
    public static final byte INS = (byte)0x88;
    public static final byte P1 = 0x00;
    public static final byte P2 = 0x00;

    public InternalAuthenticate(Channel channel) {
        super(channel);
    }

    public InternalAuthenticate(Channel channel, ResponseVerifier responseVerifier) {
        super(channel, responseVerifier);
    }

    public ResponseAPDU authenticate(byte[] data) {
        return transmit(new CommandAPDU(concat(new byte[]{0x00, (byte)0x88, (byte)0x00, (byte)0x00, (byte)data.length}, data, new byte[]{0x00})));
    }
}
