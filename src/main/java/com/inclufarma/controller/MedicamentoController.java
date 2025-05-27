package com.inclufarma.controller;

import com.inclufarma.dto.MedicamentoDTO;
import com.inclufarma.model.Medicamento;
import com.inclufarma.repository.CategoriaRepository;
import com.inclufarma.repository.MedicamentoRepository;
import com.inclufarma.service.AuthenticationService;
import com.inclufarma.service.MedicamentoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/medicamento")
@AllArgsConstructor

public class MedicamentoController {

    private final MedicamentoService medicamentoService;
    private final MedicamentoRepository medicamentoRepository;

    @Operation(summary = "Listar medicamentos")
    @GetMapping("/listar")
    public List<Medicamento> listarMedicamentos() {
         return medicamentoService.findAllMedicamentos();
    }

    @Operation(summary = "Cadastrar novo medicamento")
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarMedicamento(@RequestBody MedicamentoDTO dto) {
         try {
             Medicamento novo = medicamentoService.criarMedicamento(dto);
             return new ResponseEntity<>(novo, HttpStatus.CREATED);
         } catch (IllegalArgumentException e) {
             return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
         }
     }

    @Operation(summary = "Atualizar medicamento")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarMedicamento(@PathVariable UUID id, @RequestBody MedicamentoDTO dto) {
        try {
            Medicamento atualizado = medicamentoService.atualizarMedicamento(id, dto);
            return ResponseEntity.ok(atualizado);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Deletar medicamento")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarMedicamento(@PathVariable UUID id) {
        try {
            Medicamento medicamento = medicamentoRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Medicamento não encontrado com id: " + id));
            medicamentoRepository.delete(medicamento);
            return ResponseEntity.ok("Medicamento deletado com sucesso");
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
