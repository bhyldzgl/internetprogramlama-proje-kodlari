package com.habersitesi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "kategoriler")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Kategori {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ad;
}
