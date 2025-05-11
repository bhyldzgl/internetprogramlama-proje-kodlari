package com.habersitesi.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String adSoyad;
    private String email;
    private String sifre;
    private String rol; // "ADMIN", "EDITOR", "UYE"
}
