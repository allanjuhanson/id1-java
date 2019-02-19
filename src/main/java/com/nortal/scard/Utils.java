package com.nortal.scard;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

public class Utils {
    private Utils() {}

    public static String asUTF8String(byte[] data) {
        return new String(data, Charset.forName("UTF-8")).trim();
    }

    public static byte[] concat(byte[] ... byteArrays) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        for (byte[] byteArray : byteArrays) {
            try {
                stream.write(byteArray);
            } catch (IOException e) {
                throw new SmartCardReaderException(e);
            }
        }
        return stream.toByteArray();
    }

    public static byte[] padCode(byte[] code) {
        byte[] padded = Arrays.copyOf(code, 12);
        Arrays.fill(padded, code.length, padded.length, (byte) 0xFF);
        return padded;

    }

    /**
     * ID1 only has ECC keys so we don't need to pad it as we do RSA hashes,
     * but we need to pad hashes that are smaller than the key size with zeroes in front to resize
     * them to 48bytes in length because of API restrictions on the chip
     *
     * @param hash that needs to be signed
     * @return zero padded hash with 48 byte length or same hash if it's longer than 48 bytes
     * @throws SmartCardReaderException when padding the hash fails
     */
    public static byte[] padWithZeroes(byte[] hash) throws SmartCardReaderException {
        if (hash.length >= 48) {
            return hash;
        }
        try (ByteArrayOutputStream toSign = new ByteArrayOutputStream()) {
            toSign.write(new byte[48 - hash.length]);
            toSign.write(hash);
            return toSign.toByteArray();
        } catch (IOException e) {
            throw new SmartCardReaderException("Failed to Add padding to hash", e);
        }
    }
}
