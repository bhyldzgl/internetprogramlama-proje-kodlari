package com.habersitesi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiErrorResponse {
    private LocalDateTime zaman;
    private int status;
    private String hata;
    private String mesaj;
    private String path;
}
