package com.nortal.scard.command;

import com.nortal.scard.Channel;
import com.nortal.scard.command.ManageSecurityEnvironment;
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
public class ManageSecurityEnvironmentTest {

    private static final ResponseAPDU successfulResponse = new ResponseAPDU(new byte[]{(byte)0x90, 0x00});
    private static final byte[] DATA = new byte[]{0x01, 0x02, 0x03};

    @Captor
    private ArgumentCaptor<CommandAPDU> commandCaptor;

    @Mock
    private Channel channel;
    @Mock
    private ResponseVerifier verifier;

    @InjectMocks
    private ManageSecurityEnvironment manageSecurityEnvironment;

    @Before
    public void setUp() {
        when(channel.transmit(any(CommandAPDU.class))).thenReturn(successfulResponse);
        doNothing().when(verifier).checkResponse(any(ResponseAPDU.class));
    }

    @Test
    public void shouldSetEnvironmentForDigitalSignature() {
        ResponseAPDU response = manageSecurityEnvironment.setEnvForDigitalSignature(DATA);
        verify(channel).transmit(commandCaptor.capture());
        assertThat(response, is(successfulResponse));
        verifyCorrectCommand("002241B603010203");
    }

    @Test
    public void shouldSetEnvironmentForAuthentication() {
        ResponseAPDU response = manageSecurityEnvironment.setEnvForAuthentication(DATA);
        verify(channel).transmit(commandCaptor.capture());
        assertThat(response, is(successfulResponse));
        verifyCorrectCommand("002241A403010203");
    }

    @Test
    public void shouldSetEnvironmentForDecryption() {
        ResponseAPDU response = manageSecurityEnvironment.setEnvForConfidentiality(DATA);
        verify(channel).transmit(commandCaptor.capture());
        assertThat(response, is(successfulResponse));
        verifyCorrectCommand("002241B803010203");
    }

    private void verifyCorrectCommand(String expected) {
        String commandString = Hex.encodeHexString(commandCaptor.getValue().getBytes(), false);
        assertThat(commandString, is(expected));
    }

}
