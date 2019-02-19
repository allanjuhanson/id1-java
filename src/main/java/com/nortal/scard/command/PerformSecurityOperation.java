package com.nortal.scard.command;

import com.nortal.scard.Channel;
import com.nortal.scard.sw.ResponseVerifier;

import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

import static com.nortal.scard.Utils.concat;

public class PerformSecurityOperation extends CommandTransmitter {
    public static final byte CLA = 0x00;
    public static final byte INS = 0x2A;

    public static final byte P1_SIGNATURE = (byte)0x9E;
    public static final byte P2_SIGNATURE = (byte)0x9A;

    public static final byte P1_DECIPHER = (byte)0x80;
    public static final byte P2_DECIPHER = (byte)0x86;

    public PerformSecurityOperation(Channel channel) {
        super(channel);
    }

    public PerformSecurityOperation(Channel channel, ResponseVerifier responseVerifier) {
        super(channel, responseVerifier);
    }

    public ResponseAPDU performSecurityOperation(byte p1, byte p2, byte[] data) {
        return transmit(new CommandAPDU(concat(new byte[]{CLA, INS, p1, p2, (byte)data.length}, data, new byte[]{0x00})));
    }

    public ResponseAPDU computeDigitalSignature(byte[] data) {
        return performSecurityOperation(P1_SIGNATURE, P2_SIGNATURE, data);
    }

    public ResponseAPDU deriveSharedSecret(byte[] data) {
        return performSecurityOperation(P1_DECIPHER, P2_DECIPHER, data);
    }
}
