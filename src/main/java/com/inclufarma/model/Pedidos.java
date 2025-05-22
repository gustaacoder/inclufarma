package com.inclufarma.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pedidos {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    private Usuario usuario;
    private LocalDateTime data;
    @ManyToOne
    private Endereco endereco;

    @PrePersist
    public void prePersist() {
            this.data = LocalDateTime.now();
    }

}
