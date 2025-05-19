package com.habersitesi.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

    @Column(name = "gorsel_yolu")
    private String gorselYolu;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "haber_etiket",
            joinColumns = @JoinColumn(name = "haber_id"),
            inverseJoinColumns = @JoinColumn(name = "etiket_id")
    )
    private Set<Etiket> etiketler = new HashSet<>();
}
