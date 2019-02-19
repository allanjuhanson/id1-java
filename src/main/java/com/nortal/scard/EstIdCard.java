package com.nortal.scard;

import com.nortal.scard.model.CertificateType;
import com.nortal.scard.model.CodeType;
import com.nortal.scard.model.PersonalData;
import com.nortal.scard.model.PersonalDataRecord;

public interface EstIdCard {

    /**
     * Read Document number from the card
     * @return Document number
     * @throws SmartCardReaderException When reading failed.
     */
    String readDocumentNumber() throws SmartCardReaderException;

    /**
     * Read personal information of the cardholder.
     *
     * @return Personal data of the cardholder.
     * @throws SmartCardReaderException When reading failed.
     */
    PersonalData readPersonalData() throws SmartCardReaderException;

    /**
     * Read personal information of the cardholder.
     *
     * @param records allows to specify which personal data records to read from the card
     * @return Personal data of the cardholder. Only the specified data is returned.
     * Data that was not read from the card has null values
     * @throws SmartCardReaderException When reading failed.
     */
    PersonalData readPersonalData(PersonalDataRecord... records) throws SmartCardReaderException;

    /**
     * Change PIN1/PIN2/PUK code.
     *
     * @param type Code type.
     * @param currentCode Current code.
     * @param newCode New code.
     * @throws SmartCardReaderException When changing failed.
     * @throws CodeVerificationException When current code is wrong.
     */
    void changeCode(CodeType type, byte[] currentCode, byte[] newCode) throws SmartCardReaderException, CodeVerificationException;

    /**
     * Unblock PIN1/PIN2 via PUK code and change it to a new value.
     *
     * When PIN1/PIN2 is not blocked yet it will be blocked before unblocking.
     *
     * @param pukCode PUK code.
     * @param type Code type.
     * @param newCode New code.
     * @throws SmartCardReaderException When changing failed.
     * @throws CodeVerificationException When PUK code is wrong.
     */
    void unblockAndChangeCode(byte[] pukCode, CodeType type, byte[] newCode) throws SmartCardReaderException, CodeVerificationException;

    /**
     * Read retry counter for PIN1/PIN2/PUK code.
     *
     * @param type Code type.
     * @return Code retry counter.
     */
    int codeRetryCounter(CodeType type) throws SmartCardReaderException;

    /**
     * Read certificate data of the cardholder.
     *
     * @param type Type of the certificate.
     * @return Certificate data.
     * @throws SmartCardReaderException When reading failed.
     */
    byte[] readCertificate(CertificateType type) throws SmartCardReaderException;

    /**
     * Calculate electronic signature with pre-calculated hash.
     *
     * @param pin2 PIN2 code.
     * @param hash Pre-calculated hash.
     * @return Signed data.
     * @throws SmartCardReaderException When calculating signature failed.
     * @throws CodeVerificationException When PIN2 code is wrong.
     */
    byte[] calculateSignature(byte[] pin2, byte[] hash) throws SmartCardReaderException, CodeVerificationException;

    /**
     * Calculate response for TLS challenge.
     *
     * @param pin1 PIN1 code.
     * @param challenge TLS challenge.
     * @return Encrypted TLS challenge.
     * @throws SmartCardReaderException When calculating signature failed.
     * @throws CodeVerificationException When PIN1 code is wrong.
     */
    byte[] calculateTLSChallengeResponse(byte[] pin1, byte[] challenge) throws SmartCardReaderException, CodeVerificationException;
    /**
     * Decrypt data.
     *
     * @param pin1 PIN1 code.
     * @param data Data to decrypt.
     *
     * @return Decrypt result.
     * @throws SmartCardReaderException When decrypting failed.
     * @throws CodeVerificationException When PIN1 code is wrong.
     */
    byte[] decrypt(byte[] pin1, byte[] data) throws SmartCardReaderException;
}
