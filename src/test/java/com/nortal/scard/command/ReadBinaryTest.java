package com.nortal.scard.command;

import com.nortal.scard.Channel;
import com.nortal.scard.sw.ResponseVerifier;
import org.apache.commons.codec.DecoderException;
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

import static com.nortal.scard.Utils.concat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReadBinaryTest {

    private static final String FIRST_PART_231_BYTES = "3082040330820365A00302010202106E048ACDECE337A95BC861350B5D79F6300A06082A8648CE3D0403043060310B3009060355040613024545311B3019060355040A0C12534B20494420536F6C7574696F6E732041533117301506035504610C0E4E545245452D3130373437303133311B301906035504030C1254455354206F662045535445494432303138301E170D3138313031383130333232305A170D3233313031373231353935395A307F310B3009060355040613024545312A302806035504030C214AC395454F52472C4A41414B2D4B524953544A414E2C33383030313038353731";
    private static final String SECOND_PART_231_BYTES = "383110300E06035504040C074AC395454F524731163014060355042A0C0D4A41414B2D4B524953544A414E311A301806035504051311504E4F45452D33383030313038353731383076301006072A8648CE3D020106052B8104002203620004FB511E245BE6D7BB864758BE449859EED7A153753E136E7A83AF4896B96CD015F3123429FDEA102022CA53BAC8608CB880C556632B961AACE5E9667372D481B3B9AE346BC90408B1BDB7586F3A776C90AC9EC295DE9EB31A80C47039586D840FA38201C3308201BF30090603551D1304023000300E0603551D0F0101FF0404030203883047060355";
    private static final String LAST_PART_112_BYTES = "B3222D51DE32A10B3AB17E05476D9A8B09CB47B3C201E8825AFF90C99E97E1DCA08054D21153000102413DB0DD2A6FA6BEB95553214BA9E3D85F65D5A1467DA8AB17EB713710BA657269B1673488841A8A55C8CC1325A69F9F154DD2F44AD118900B9183501A8DC17419E7";

    private static final ResponseAPDU successfulResponse1 = createResponse(FIRST_PART_231_BYTES, "9000");
    private static final ResponseAPDU successfulResponse2 = createResponse(SECOND_PART_231_BYTES, "9000");
    private static final ResponseAPDU successfulResponse3 = createResponse(LAST_PART_112_BYTES, "9000");
    private static final ResponseAPDU outOfBoundsResponse = createResponse(null, "6B00");
    private static final ResponseAPDU successfulResponse4 = createResponse(LAST_PART_112_BYTES, "9000");


    @Captor
    private ArgumentCaptor<CommandAPDU> commandCaptor;

    @Mock
    private Channel channel;
    @Mock
    private ResponseVerifier verifier;

    @InjectMocks
    private ReadBinary readBinary;

    @Before
    public void setUp() {
        when(channel.transmit(any(CommandAPDU.class))).thenReturn(successfulResponse1, successfulResponse2, successfulResponse3, outOfBoundsResponse, successfulResponse4);
        doNothing().when(verifier).checkResponse(any(ResponseAPDU.class));
    }

    @Test
    public void shouldReadBinary() {
        ResponseAPDU response = readBinary.read();
        verify(channel, times(1)).transmit(commandCaptor.capture());
        verifyCorrectCommand("00B0000000");
        assertThat(response, is(successfulResponse1));
    }

    @Test
    public void shouldReadUntilEndOfFileResponse() {
        byte[] response = readBinary.readUntilEndOfFile();
        verify(channel, times(4)).transmit(commandCaptor.capture());
        assertThat(Hex.encodeHexString(response, false), is(FIRST_PART_231_BYTES + SECOND_PART_231_BYTES + LAST_PART_112_BYTES));
        verifyCorrectCommand("00B0000000", Hex.encodeHexString(commandCaptor.getAllValues().get(0).getBytes(), false));
        verifyCorrectCommand("00B000E700", Hex.encodeHexString(commandCaptor.getAllValues().get(1).getBytes(), false));
        verifyCorrectCommand("00B001CE00", Hex.encodeHexString(commandCaptor.getAllValues().get(2).getBytes(), false));
        verifyCorrectCommand("00B0023900", Hex.encodeHexString(commandCaptor.getAllValues().get(3).getBytes(), false));
    }

    private void verifyCorrectCommand(String expected) {
        String commandString = Hex.encodeHexString(commandCaptor.getValue().getBytes(), false);
        verifyCorrectCommand(commandString, expected);
    }

    private void verifyCorrectCommand(String expected, String actual) {
        assertThat(actual, is(expected));
    }

    private static ResponseAPDU createResponse(String data, String sw) {
        try {
            if (data == null || data.length() == 0) {
                return new ResponseAPDU(Hex.decodeHex(sw));
            } else {
                return new ResponseAPDU(concat(Hex.decodeHex(data), Hex.decodeHex(sw)));
            }
        } catch (DecoderException e) {
            return null;
        }
    }
}
