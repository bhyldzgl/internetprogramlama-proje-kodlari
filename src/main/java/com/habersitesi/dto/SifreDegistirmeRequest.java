package com.habersitesi.dto;

import lombok.Data;

@Data
public class SifreDegistirmeRequest {
    private String mevcutSifre;
    private String yeniSifre;
}
