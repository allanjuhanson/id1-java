package com.nortal.scard.model;

import com.nortal.scard.Utils;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class PersonalDataBuilder {
    private static final Logger LOG = LoggerFactory.getLogger(PersonalDataBuilder.class);

    private static final DateTimeFormatter DATE_FORMAT = new DateTimeFormatterBuilder()
        .appendPattern("dd MM yyyy")
        .toFormatter();

    private PersonalData personalData;

    private PersonalDataBuilder(PersonalData personalData) {
        this.personalData = personalData;
    }

    public static PersonalDataBuilder builder() {
        return new PersonalDataBuilder(new PersonalData());
    }

    public PersonalDataBuilder withRecord(PersonalDataRecord record, byte[] data) {
        LOG.debug("{} - {} - {}", record.name(), Hex.encodeHexString(data), Utils.asUTF8String(data));
        switch (record) {
            case LAST_NAME:
                personalData.setLastName(Utils.asUTF8String(data)); break;
            case FIRST_NAME:
                personalData.setFirstName(Utils.asUTF8String(data)); break;
            case GENDER:
                personalData.setGender(Utils.asUTF8String(data)); break;
            case CITIZENSHIP:
                personalData.setCitizenship(Utils.asUTF8String(data)); break;
            case DATE_AND_PLACE_OF_BIRTH:
                parseDateAndPlaceOfBirth(data); break;
            case PERSONAL_CODE:
                personalData.setPersonalCode(Utils.asUTF8String(data)); break;
            case DOC_NUMBER:
                personalData.setDocumentNumber(Utils.asUTF8String(data)); break;
            case EXPIRY_DATE:
                parseExpiryDate(data); break;
            case ISSUED_DATE:
                parseIssuedDate(data); break;
            case RESIDENCE_PERMIT_TYPE:
                personalData.setResidencePermitType(Utils.asUTF8String(data)); break;
            case COMMENT_1:
                personalData.setComment1(Utils.asUTF8String(data)); break;
            case COMMENT_2:
                personalData.setComment2(Utils.asUTF8String(data)); break;
            case COMMENT_3:
                personalData.setComment3(Utils.asUTF8String(data)); break;
            case COMMENT_4:
                personalData.setComment4(Utils.asUTF8String(data)); break;
            case COMMENT_5:
                personalData.setComment5(Utils.asUTF8String(data)); break;
        }
        return this;
    }

    public PersonalData build() {
        return personalData;
    }

    private void parseDateAndPlaceOfBirth(byte[] data) {
        String dataString = Utils.asUTF8String(data);
        personalData.setDateOfBirth(parseDateOfBirth(dataString));
        personalData.setPlaceOfBirth(parsePlaceOfBirth(dataString));
    }

    private String parsePlaceOfBirth(String dateAndPlaceOfBirthString) {
        try {
            return dateAndPlaceOfBirthString
                .substring(dateAndPlaceOfBirthString.length() - 3, dateAndPlaceOfBirthString.length());
        } catch (Exception e) {
            LOG.warn(String.format("Could not parse place of birth %s", dateAndPlaceOfBirthString), e);
            return null;
        }
    }

    private static LocalDate parseDateOfBirth(String dateAndPlaceOfBirthString) {
        if (dateAndPlaceOfBirthString == null) {
            LOG.warn("Could not parse date of birth: no data");
            return null;
        }
        try {
            String dateOfBirthString = dateAndPlaceOfBirthString
                .substring(0, dateAndPlaceOfBirthString.length() - 4);
            return LocalDate.parse(dateOfBirthString, DATE_FORMAT);
        } catch (Exception e) {
            LOG.warn(String.format("Could not parse date of birth %s", dateAndPlaceOfBirthString), e);
            return null;
        }
    }

    private void parseExpiryDate(byte[] data) {
        String expiryDateString = Utils.asUTF8String(data);
        try {
            personalData.setExpiryDate(parseDate(expiryDateString));
        } catch (Exception e) {
            LOG.warn(String.format("Could not parse expiry date %s", expiryDateString), e);
        }
    }

    private void parseIssuedDate(byte[] data) {
        String issuedDateString = Utils.asUTF8String(data);
        try {
            personalData.setIssuedDate(parseDate(issuedDateString));
        } catch (Exception e) {
            LOG.warn(String.format("Could not parse issued date %s", issuedDateString), e);
        }
    }

    private LocalDate parseDate(String dateString) {
        return LocalDate.parse(dateString, DATE_FORMAT);
    }


}
