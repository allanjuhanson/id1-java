package com.nortal.scard.id1;

import com.nortal.scard.model.CertificateType;
import com.nortal.scard.model.CodeType;
import com.nortal.scard.model.PersonalDataRecord;

import java.util.HashMap;
import java.util.Map;

import static com.nortal.scard.model.CertificateType.*;
import static com.nortal.scard.model.PersonalDataRecord.*;

public class FID {

    public static byte[] DOCUMENT_NR_FID = new byte[] {(byte)0xD0, 0x03};
    public static byte[] PERSONAL_FILE_FID = new byte[] {(byte)0x50, 0x00};
    public static byte[] ADF1_FID = new byte[] {(byte)0xAD, (byte)0xF1};
    public static byte[] ADF2_FID = new byte[] {(byte)0xAD, (byte)0xF2};

    private static final Map<CertificateType, byte[]> CERT_FID_MAP = new HashMap<>();
    private static final Map<PersonalDataRecord, byte[]> PERSONAL_DATA_RECORD_FID_MAP = new HashMap<>();
    private static final Map<CodeType, Byte> VERIFY_PIN_MAP = new HashMap<>();

    static {
        VERIFY_PIN_MAP.put(CodeType.PIN1, (byte) 0x01);
        VERIFY_PIN_MAP.put(CodeType.PIN2, (byte) 0x85);
        VERIFY_PIN_MAP.put(CodeType.PUK, (byte) 0x02);
    }


    static {
        CERT_FID_MAP.put(AUTH, new byte[]{0x34, 0x01});
        CERT_FID_MAP.put(SIGN, new byte[]{0x34, 0x1F});
    }

    static {
        PERSONAL_DATA_RECORD_FID_MAP.put(LAST_NAME, new byte[]{0x50, 0x01});
        PERSONAL_DATA_RECORD_FID_MAP.put(FIRST_NAME, new byte[]{0x50, 0x02});
        PERSONAL_DATA_RECORD_FID_MAP.put(GENDER, new byte[]{0x50, 0x03});
        PERSONAL_DATA_RECORD_FID_MAP.put(CITIZENSHIP, new byte[]{0x50, 0x04});
        PERSONAL_DATA_RECORD_FID_MAP.put(DATE_AND_PLACE_OF_BIRTH, new byte[]{0x50, 0x05});
        PERSONAL_DATA_RECORD_FID_MAP.put(PERSONAL_CODE, new byte[]{0x50, 0x06});
        PERSONAL_DATA_RECORD_FID_MAP.put(DOC_NUMBER, new byte[]{0x50, 0x07});
        PERSONAL_DATA_RECORD_FID_MAP.put(EXPIRY_DATE, new byte[]{0x50, 0x08});
        PERSONAL_DATA_RECORD_FID_MAP.put(ISSUED_DATE, new byte[]{0x50, 0x09});
        PERSONAL_DATA_RECORD_FID_MAP.put(RESIDENCE_PERMIT_TYPE, new byte[]{0x50, 0x0A});
        PERSONAL_DATA_RECORD_FID_MAP.put(COMMENT_1, new byte[]{0x50, 0x0B});
        PERSONAL_DATA_RECORD_FID_MAP.put(COMMENT_2, new byte[]{0x50, 0x0C});
        PERSONAL_DATA_RECORD_FID_MAP.put(COMMENT_3, new byte[]{0x50, 0x0D});
        PERSONAL_DATA_RECORD_FID_MAP.put(COMMENT_4, new byte[]{0x50, 0x0E});
        PERSONAL_DATA_RECORD_FID_MAP.put(COMMENT_5, new byte[]{0x50, 0x0F});
    }

    public static Byte get(CodeType codeType) {
        return VERIFY_PIN_MAP.get(codeType);
    }

    public static byte[] get(CertificateType certificateType) {
        return CERT_FID_MAP.get(certificateType);
    }

    public static byte[] get(PersonalDataRecord personalDataRecord) {
        return PERSONAL_DATA_RECORD_FID_MAP.get(personalDataRecord);
    }
}
