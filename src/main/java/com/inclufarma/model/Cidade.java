package com.inclufarma.model;

import com.inclufarma.repository.CidadeRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cidade {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID id;
    private String nome;
}
