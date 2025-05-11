package com.habersitesi.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "haberler")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Haber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String baslik;
    private String icerik;
    private LocalDateTime yayinTarihi;

    @ManyToOne
    @JoinColumn(name = "yazar_id")
    private Kullanici yazar;

    @ManyToOne
    @JoinColumn(name = "kategori_id")
    private Kategori kategori;
}
