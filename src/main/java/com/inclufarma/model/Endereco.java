package com.inclufarma.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID id;
    private String logradouro;
    @ManyToOne
    @JoinColumn(name = "cidade_id")
    private Cidade cidade;
    private Integer cep;
    private String numero;
}
