package com.habersitesi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "favoriler", uniqueConstraints = @UniqueConstraint(columnNames = {"kullanici_id", "haber_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Favori {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "kullanici_id")
    private Kullanici kullanici;

    @ManyToOne
    @JoinColumn(name = "haber_id")
    private Haber haber;
}
