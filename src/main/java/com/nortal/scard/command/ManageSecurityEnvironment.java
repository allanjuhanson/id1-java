package com.nortal.scard.command;

import com.nortal.scard.Channel;
import com.nortal.scard.sw.ResponseVerifier;

import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

public class ManageSecurityEnvironment extends CommandTransmitter {
    public static final byte CLA = 0x00;
    public static final byte INS = 0x22;
    public static final byte P1 = 0x41;

    public static final byte CRT_DST = (byte)0xB6;
    public static final byte CRT_AT = (byte)0xA4;
    public static final byte CRT_CT = (byte)0xB8;

    public ManageSecurityEnvironment(Channel channel) {
        super(channel);
    }

    public ManageSecurityEnvironment(Channel channel, ResponseVerifier responseVerifier) {
        super(channel, responseVerifier);
    }

    public ResponseAPDU setEnv(byte p2, byte[] data) {
        return transmit(new CommandAPDU(CLA, INS, P1, p2, data));
    }

    public ResponseAPDU setEnvForDigitalSignature(byte[] data) {
        return setEnv(CRT_DST, data);
    }

    public ResponseAPDU setEnvForAuthentication(byte[] data) {
        return setEnv(CRT_AT, data);
    }

    public ResponseAPDU setEnvForConfidentiality(byte[] data) {
        return setEnv(CRT_CT, data);
    }

}
