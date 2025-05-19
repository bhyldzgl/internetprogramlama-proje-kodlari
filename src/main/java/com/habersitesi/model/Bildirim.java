package com.habersitesi.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "bildirimler")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bildirim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mesaj;

    private boolean okundu = false;

    private LocalDateTime tarih;

    @ManyToOne
    @JoinColumn(name = "kullanici_id")
    private Kullanici hedefKullanici;
}
