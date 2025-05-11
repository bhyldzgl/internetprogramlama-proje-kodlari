package com.habersitesi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roller")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RolTipi ad;

    public enum RolTipi {
        ADMIN, EDITOR, UYE
    }
}
