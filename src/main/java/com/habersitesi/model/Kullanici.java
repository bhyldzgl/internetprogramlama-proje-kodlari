package com.habersitesi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "kullanicilar")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Kullanici {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String sifre;

    @Column(nullable = false)
    private String adSoyad;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "kullanici_rolleri",
            joinColumns = @JoinColumn(name = "kullanici_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private Set<Rol> roller;
}
