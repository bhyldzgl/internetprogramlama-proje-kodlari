package com.habersitesi.dto;

import lombok.Data;

@Data
public class RolGuncelleRequest {
    private String email;
    private String yeniRol; // "ADMIN", "EDITOR", "UYE"
}
