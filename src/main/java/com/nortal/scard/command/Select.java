package com.nortal.scard.command;

import com.nortal.scard.Channel;
import com.nortal.scard.sw.ResponseVerifier;

import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

import static com.nortal.scard.Utils.concat;

public class Select extends CommandTransmitter {

    public static final byte CLA = 0x00;
    public static final byte INS = (byte)0xA4;

    public static final byte P1_MF = 0x00;
    public static final byte P1_CHILD_DF = 0x01;
    public static final byte P1_CHILD_EF = 0x02;
    public static final byte P1_BY_ADF_NAME = 0x04;

    public static final byte P2_NO_FCP = 0x0C;
    public static final byte P2_FCP = 0x04;

    public Select(Channel channel) {
        super(channel);
    }

    public Select(Channel channel, ResponseVerifier responseVerifier) {
        super(channel, responseVerifier);
    }

    public ResponseAPDU select(byte p1, byte p2, byte[] data) {
        return transmit(new CommandAPDU(CLA, INS, p1, p2, data));
    }

    public ResponseAPDU select(byte p1, byte p2, byte[] data, byte le) {
        return transmit(new CommandAPDU(concat(new byte[]{CLA, (byte)INS, (byte)p1, (byte)p2, (byte)data.length}, data, new byte[]{(byte)le})));
    }

    public ResponseAPDU select(byte p1, byte p2) {
        return transmit(new CommandAPDU(CLA, INS, p1, p2));
    }

    public ResponseAPDU select(byte p1, byte p2, byte le) {
        return transmit(new CommandAPDU(new byte[]{CLA, (byte)INS, (byte)p1, (byte)p2, (byte)le}));
    }

    public ResponseAPDU selectMasterFile(byte[] fid) {
        return select(P1_MF, P2_NO_FCP, fid);
    }

    public ResponseAPDU selectMasterFile() {
        return select(P1_MF, P2_NO_FCP);
    }

    public ResponseAPDU selectChildDF(byte[] fid) {
        return select(P1_CHILD_DF, P2_NO_FCP, fid);
    }

    public ResponseAPDU selectChildEF(byte[] fid) {
        return select(P1_CHILD_EF, P2_NO_FCP, fid);
    }

    public ResponseAPDU selectByDFName(byte[] aid) {
        return select(P1_BY_ADF_NAME, P2_NO_FCP, aid);
    }

    public ResponseAPDU selectMasterFileForFcp(byte[] fid) {
        return select(P1_MF, P2_FCP, fid, (byte)0x00);
    }

    public ResponseAPDU selectMasterFileForFcp() {
        return select(P1_MF, P2_FCP, (byte)0x00);
    }

    public ResponseAPDU selectChildDFForFcp(byte[] fid) {
        return select(P1_CHILD_DF, P2_FCP, fid, (byte)0x00);
    }

    public ResponseAPDU selectChildEFForFcp(byte[] fid) {
        return select(P1_CHILD_EF, P2_FCP, fid, (byte)0x00);
    }

    public ResponseAPDU selectByDFNameForFcp(byte[] aid) {
        return select(P1_BY_ADF_NAME, P2_FCP, aid, (byte)0x00);
    }
}
