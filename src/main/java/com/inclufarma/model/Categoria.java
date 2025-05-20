package com.inclufarma.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Categoria {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID id;
    private String nome;
}