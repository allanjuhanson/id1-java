package com.nortal.scard.model;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class Certificate {

    private byte[] data;
    private X509Certificate x509Certificate;

    public static Certificate of(byte[] data) throws CertificateException {
        Certificate certificate = new Certificate();
        certificate.data = data;
        certificate.parseX509Certificate();
        return certificate;
    }

    public byte[] getData() {
        return data;
    }

    public X509Certificate getX509Certificate() {
        return x509Certificate;
    }

    public String asHexString() {
        return Hex.encodeHexString(data);
    }

    public String asPemString() {
        return Base64.encodeBase64String(data);
    }

    private void parseX509Certificate() throws CertificateException {
        x509Certificate = (X509Certificate) CertificateFactory.getInstance("X.509")
            .generateCertificate(new ByteArrayInputStream(data));
    }

}
