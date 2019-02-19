package com.nortal.scard.id1;

import com.nortal.scard.Channel;
import com.nortal.scard.CodeVerificationException;
import com.nortal.scard.EstIdCard;
import com.nortal.scard.command.*;
import com.nortal.scard.model.*;
import com.nortal.scard.sw.AllowEndOfFileReachedResponseVerifier;
import com.nortal.scard.sw.CodeVerifyingResponseChecker;
import com.nortal.scard.sw.Sw9000ResponseVerifier;

import javax.smartcardio.ResponseAPDU;

import static com.nortal.scard.Utils.*;
import static com.nortal.scard.id1.FID.*;

public class ID1 implements EstIdCard {

    private Select fileSelector;
    private ReadBinary binaryReader;
    private Verify verifier;
    private ChangeReferenceData changer;
    private ResetRetryCounter resetter;
    private ManageSecurityEnvironment environment;
    private PerformSecurityOperation securityOperation;
    private InternalAuthenticate internalAuthenticate;

    public ID1(Channel channel) {
        this.fileSelector = new Select(channel, new Sw9000ResponseVerifier());
        this.binaryReader = new ReadBinary(channel, new AllowEndOfFileReachedResponseVerifier());
        this.verifier = new Verify(channel, new CodeVerifyingResponseChecker());
        this.changer = new ChangeReferenceData(channel, new CodeVerifyingResponseChecker());
        this.resetter = new ResetRetryCounter(channel, new Sw9000ResponseVerifier());
        this.environment = new ManageSecurityEnvironment(channel, new Sw9000ResponseVerifier());
        this.securityOperation = new PerformSecurityOperation(channel, new Sw9000ResponseVerifier());
        this.internalAuthenticate = new InternalAuthenticate(channel, new Sw9000ResponseVerifier());
    }

    @Override
    public String readDocumentNumber() {
        fileSelector.selectMasterFile();
        fileSelector.selectChildEF(DOCUMENT_NR_FID);
        ResponseAPDU response = binaryReader.read();
        return asUTF8String(response.getData());
    }

    @Override
    public PersonalData readPersonalData(PersonalDataRecord... records) {
        fileSelector.selectMasterFile();
        fileSelector.selectChildDF(PERSONAL_FILE_FID);
        PersonalDataBuilder builder = PersonalDataBuilder.builder();
        for (PersonalDataRecord record : records) {
            fileSelector.selectChildEF(FID.get(record));
            ResponseAPDU response = binaryReader.read();
            builder = builder.withRecord(record, response.getData());
        }
        return builder.build();
    }

    @Override
    public int codeRetryCounter(CodeType codeType) {
        selectCorrectAidForCode(codeType);
        try {
            verifier.verify(FID.get(codeType));
            //No exception means already in verified state and retry count = 3
            return 3;
        } catch (CodeVerificationException e) {
            String sw = e.getSw();
            String sw1 = sw.substring(0, 2);
            if ("63".equalsIgnoreCase(sw1)) {
                return Integer.parseInt(sw.substring(sw.length() - 1));
            }
            throw e;
        }
    }

    @Override
    public byte[] readCertificate(CertificateType certificateType) {
        fileSelector.selectMasterFile();
        if (certificateType == CertificateType.AUTH) {
            fileSelector.selectChildDF(ADF1_FID);
        } else {
            fileSelector.selectChildDF(ADF2_FID);
        }
        fileSelector.selectChildEF(FID.get(certificateType));
        return binaryReader.readUntilEndOfFile();
    }

    @Override
    public byte[] calculateSignature(byte[] pin2, byte[] hash) {
        fileSelector.selectMasterFile();
        fileSelector.selectChildDF(ADF2_FID);
        verifier.verify(FID.get(CodeType.PIN2), padCode(pin2));
        environment.setEnvForDigitalSignature(new byte[] {(byte) 0x80, 0x04, (byte) 0xFF, 0x15, 0x08, 0x00, (byte) 0x84, 0x01, (byte) 0x9F});
        ResponseAPDU response = securityOperation.computeDigitalSignature(padWithZeroes(hash));
        return response.getData();
    }

    @Override
    public byte[] calculateTLSChallengeResponse(byte[] pin1, byte[] challenge) {
        fileSelector.selectMasterFile();
        fileSelector.selectChildDF(ADF1_FID);
        verifier.verify(FID.get(CodeType.PIN1), padCode(pin1));
        environment.setEnvForAuthentication(new byte[] {(byte) 0x80, 0x04, (byte) 0xFF, 0x20, 0x08, 0x00, (byte) 0x84, 0x01, (byte) 0x81});
        return internalAuthenticate.authenticate(challenge).getData();
    }

    @Override
    public void changeCode(CodeType codeType, byte[] currentCode, byte[] newCode) {
        selectCorrectAidForCode(codeType);
        changer.change(FID.get(codeType), concat(padCode(currentCode), padCode(newCode)));
    }

    @Override
    public void unblockAndChangeCode(byte[] pukCode, CodeType type, byte[] newCode) {
        fileSelector.selectMasterFile();
        verifier.verify(FID.get(CodeType.PUK), padCode(pukCode));
        if (CodeType.PIN2 == type) {
            fileSelector.selectChildDF(ADF2_FID);
        }
        resetter.resetAndChange(FID.get(type), padCode(newCode));
    }

    @Override
    public byte[] decrypt(byte[] pin1, byte[] data) {
        fileSelector.selectMasterFile();
        verifier.verify(FID.get(CodeType.PIN1), padCode(pin1));
        fileSelector.selectChildDF(ADF1_FID);
        environment.setEnvForConfidentiality(new byte[] {(byte) 0x80, 0x04, (byte) 0xFF, 0x30, 0x04, 0x00, (byte) 0x84, 0x01, (byte) 0x81});
        byte[] prefix = new byte[] {0x00};
        return securityOperation.deriveSharedSecret(concat(prefix, data)).getData();
    }

    @Override
    public PersonalData readPersonalData() {
        return readPersonalData(PersonalDataRecord.values());
    }

    private void selectCorrectAidForCode(CodeType codeType) {
        fileSelector.selectMasterFile();
        if (CodeType.PIN2 == codeType) {
            fileSelector.selectChildDF(ADF2_FID);
        }
    }
}
