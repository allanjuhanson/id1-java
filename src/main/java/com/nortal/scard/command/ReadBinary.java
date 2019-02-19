package com.nortal.scard.command;

import com.nortal.scard.Channel;
import com.nortal.scard.SmartCardReaderException;
import com.nortal.scard.sw.ResponseVerifier;

import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.nortal.scard.Utils.concat;

public class ReadBinary extends CommandTransmitter {
    public static final byte CLA = 0x00;
    public static final byte INS = (byte) 0xB0;
    public static final byte P1 = 0x00;
    public static final byte P2 = 0x00;
    public static final byte LE = 0x00;

    public ReadBinary(Channel channel){
        super(channel);
    }

    public ReadBinary(Channel channel, ResponseVerifier responseVerifier) {
        super(channel, responseVerifier);
    }

    public ResponseAPDU read(byte p1, byte p2) {
        return transmit(new CommandAPDU(new byte[]{CLA, INS, p1, p2, LE}));
    }

    public ResponseAPDU read() {
        return read(P1, P2);
    }

    public ResponseAPDU readBinaryExtended(byte[] extendedLe) {
        return transmit(new CommandAPDU(concat(new byte[]{CLA, INS, P1, P2, LE}, extendedLe)));
    }

    public byte[] readUntilEndOfFile() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        boolean doneReading = false;
        while (!doneReading) {
            try {
                ResponseAPDU response = read((byte)(stream.size() >> 8), (byte)stream.size());
                String sw = Integer.toHexString(response.getSW());
                if ("6B00".equalsIgnoreCase(sw)) {
                    doneReading = true;
                }
                stream.write(response.getData());
            } catch (IOException e) {
                throw new SmartCardReaderException(e);
            }
        }
        return stream.toByteArray();
    }
}
