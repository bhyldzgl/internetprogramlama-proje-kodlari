package com.habersitesi.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "yorumlar")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Yorum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String icerik;
    private LocalDateTime yorumTarihi;

    @ManyToOne
    @JoinColumn(name = "haber_id")
    private Haber haber;

    @ManyToOne
    @JoinColumn(name = "kullanici_id")
    private Kullanici kullanici;
}
