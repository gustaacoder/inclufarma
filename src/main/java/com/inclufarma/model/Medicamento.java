package com.inclufarma.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Medicamento {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID id;
    private String nome;
    private String principio_ativo;
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
    private String descricao;
    private Float preco;
    private Integer estoque;
    private String imagem;
}
