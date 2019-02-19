package com.nortal.scard.command;

import com.nortal.scard.Channel;
import com.nortal.scard.command.Select;
import com.nortal.scard.sw.ResponseVerifier;
import org.apache.commons.codec.binary.Hex;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SelectTest {

    private static final ResponseAPDU successfulResponse = new ResponseAPDU(new byte[]{(byte)0x90, 0x00});
    private static final byte[] FID = new byte[]{0x01, 0x02};

    @Captor
    private ArgumentCaptor<CommandAPDU> commandCaptor;

    @Mock
    private Channel channel;
    @Mock
    private ResponseVerifier verifier;

    @InjectMocks
    private Select select;

    @Before
    public void setUp() {
        when(channel.transmit(any(CommandAPDU.class))).thenReturn(successfulResponse);
        doNothing().when(verifier).checkResponse(any(ResponseAPDU.class));
    }

    @Test
    public void selectMasterFileWithoutDataShouldTransmitApdu() {
        select.selectMasterFile();
        verify(channel).transmit(commandCaptor.capture());
        verifyCorrectCommand("00A4000C");
    }

    @Test
    public void selectChildDfShouldTransmitApduWithFileId() {
        select.selectChildDF(FID);
        verify(channel).transmit(commandCaptor.capture());
        verifyCorrectCommand("00A4010C020102");
    }

    @Test
    public void selectChildEfShouldTransmitApduWithFileId() {
        select.selectChildEF(FID);
        verify(channel).transmit(commandCaptor.capture());
        verifyCorrectCommand("00A4020C020102");
    }

    @Test
    public void selectByDfNameShouldTransmitApduWithAid() {
        select.selectByDFName(FID);
        verify(channel).transmit(commandCaptor.capture());
        verifyCorrectCommand("00A4040C020102");
    }

    @Test
    public void selectMasterFileForFcpShouldTransmitApduWithP2SetTo04AndIncludeLe() {
        select.selectMasterFileForFcp();
        verify(channel).transmit(commandCaptor.capture());
        verifyCorrectCommand("00A4000400");
    }

    @Test
    public void selectChildDfForFcpShouldTransmitApduWithP2SetTo04AndIncludeLe() {
        select.selectChildDFForFcp(FID);
        verify(channel).transmit(commandCaptor.capture());
        verifyCorrectCommand("00A4010402010200");
    }

    @Test
    public void selectChildEfForFcpShouldTransmitApduWithP2SetTo04AndIncludeLe() {
        select.selectChildEFForFcp(FID);
        verify(channel).transmit(commandCaptor.capture());
        verifyCorrectCommand("00A4020402010200");
    }

    @Test
    public void selectByDfNameForFcpShouldTransmitApduWithP2SetTo04AndIncludeLe() {
        select.selectByDFNameForFcp(FID);
        verify(channel).transmit(commandCaptor.capture());
        verifyCorrectCommand("00A4040402010200");
    }

    private void verifyCorrectCommand(String expected) {
        String commandString = Hex.encodeHexString(commandCaptor.getValue().getBytes(), false);
        assertThat(commandString, is(expected));
    }
}
