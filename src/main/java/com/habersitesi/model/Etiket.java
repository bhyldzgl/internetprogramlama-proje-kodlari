package com.habersitesi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "etiketler")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Etiket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ad;

    @ManyToMany(mappedBy = "etiketler")
    private Set<Haber> haberler = new HashSet<>();
}
