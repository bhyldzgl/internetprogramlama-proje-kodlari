package com.habersitesi.exception;

public class KullaniciBulunamadiException extends RuntimeException {
    public KullaniciBulunamadiException(String email) {
        super("Kullanıcı bulunamadı: " + email);
    }
}
