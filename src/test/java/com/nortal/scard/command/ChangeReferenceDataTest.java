package com.nortal.scard.command;

import com.nortal.scard.Channel;
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
public class ChangeReferenceDataTest {

    private static final ResponseAPDU successfulResponse = new ResponseAPDU(new byte[]{(byte)0x90, 0x00});
    private static final byte[] REF_DATA = new byte[]{0x01, 0x02, 0x03};
    private static final byte OBJECT_REF = 0x01;

    @Captor
    private ArgumentCaptor<CommandAPDU> commandCaptor;

    @Mock
    private Channel channel;
    @Mock
    private ResponseVerifier verifier;

    @InjectMocks
    private ChangeReferenceData changeReferenceData;

    @Before
    public void setUp() {
        when(channel.transmit(any(CommandAPDU.class))).thenReturn(successfulResponse);
        doNothing().when(verifier).checkResponse(any(ResponseAPDU.class));
    }

    @Test
    public void changeReferenceDataShouldTransmitApduWithObjectRefAndData() {
        ResponseAPDU response = changeReferenceData.change(OBJECT_REF, REF_DATA);
        verify(channel).transmit(commandCaptor.capture());
        assertThat(Hex.encodeHexString(commandCaptor.getValue().getBytes()), is("0024000103010203"));
        assertThat(response, is(successfulResponse));
    }
}
