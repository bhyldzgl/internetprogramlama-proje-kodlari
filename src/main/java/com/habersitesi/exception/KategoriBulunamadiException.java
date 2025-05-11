package com.habersitesi.exception;

public class KategoriBulunamadiException extends RuntimeException {
    public KategoriBulunamadiException(Long id) {
        super("Kategori bulunamadı (ID: " + id + ")");
    }
}
