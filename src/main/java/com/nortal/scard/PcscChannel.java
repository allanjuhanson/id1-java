package com.nortal.scard;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.smartcardio.*;

public class PcscChannel implements Channel {

    private static final Logger LOG = LoggerFactory.getLogger(PcscChannel.class);

    public static final String PROTOCOL_ANY = "*";
    public static final String PROTOCOL_T0 = "T=0";
    public static final String PROTOCOL_T1 = "T=1";
    public static final String PROTOCOL_CL = "T=CL";

    private CardTerminal terminal;
    private Card card;
    private CardChannel channel;

    public PcscChannel(CardTerminal terminal) {
        this.terminal = terminal;
    }

    @Override
    public ResponseAPDU transmit(CommandAPDU commandAPDU) {
        try {
            return channel.transmit(commandAPDU);
        } catch (CardException e) {
            LOG.error("Error occurred while transmitting command", e);
            throw new ChannelException(e);
        }
    }

    @Override
    public ATR getAtr() {
        return card.getATR();
    }

    @Override
    public boolean isCardPresent() throws CardException {
        return terminal.isCardPresent();
    }

    public void connect() throws CardException {
        connect(PROTOCOL_T1);
    }

    public void connect(String protocol) throws CardException {
        card = terminal.connect(protocol);
        LOG.debug("card: {}", card);
        channel = card.getBasicChannel();
        LOG.debug("ATR: {}", Hex.encodeHexString(card.getATR().getBytes(), false));
    }

    public void disconnect() throws CardException {
        card.disconnect(false);
    }
}
