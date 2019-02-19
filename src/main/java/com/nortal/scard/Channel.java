package com.nortal.scard;

import javax.smartcardio.ATR;
import javax.smartcardio.CardException;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

public interface Channel {

    ResponseAPDU transmit(CommandAPDU commandAPDU);
    ATR getAtr();
    boolean isCardPresent() throws CardException;
}
