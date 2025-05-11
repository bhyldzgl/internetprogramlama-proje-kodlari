package com.habersitesi.exception;

public class HaberBulunamadiException extends RuntimeException {
    public HaberBulunamadiException(Long id) {
        super("Haber bulunamadı, ID: " + id);
    }
}
