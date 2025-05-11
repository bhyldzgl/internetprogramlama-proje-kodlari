package com.habersitesi.dto;

import lombok.Data;

import java.util.Set;

@Data
public class KullaniciResponse {
    private String adSoyad;
    private String email;
    private Set<String> roller;
}
