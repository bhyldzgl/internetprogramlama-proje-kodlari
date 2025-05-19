package com.habersitesi.dto;

import lombok.Data;

@Data
public class YorumRequest {
    private String icerik;
    private Long haberId;
    private Long parentYorumId; // ← Eğer varsa, bu yorum bir cevaptır
}
