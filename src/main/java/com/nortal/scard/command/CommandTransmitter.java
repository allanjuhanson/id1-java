package com.nortal.scard.command;

import com.nortal.scard.Channel;
import com.nortal.scard.SmartCardReaderException;
import com.nortal.scard.sw.NotCheckingResponseVerifier;
import com.nortal.scard.sw.ResponseVerifier;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

public abstract class CommandTransmitter {
    private static final Logger LOG = LoggerFactory.getLogger(CommandTransmitter.class);

    private Channel channel;
    private ResponseVerifier responseVerifier;

    public CommandTransmitter(Channel channel) {
        this(channel, new NotCheckingResponseVerifier());
    }

    public CommandTransmitter(Channel channel, ResponseVerifier responseVerifier) {
        this.channel = channel;
        this.responseVerifier = responseVerifier;
    }

    public ResponseAPDU transmit(CommandAPDU commandAPDU) throws SmartCardReaderException {
        LOG.debug(">> {}", Hex.encodeHexString(commandAPDU.getBytes(), false));
        ResponseAPDU response = channel.transmit(commandAPDU);
        responseVerifier.checkResponse(response);
        LOG.debug("<< {} SW={}", Hex.encodeHexString(response.getData(), false), Integer.toHexString(response.getSW()));
        return response;
    }

}
