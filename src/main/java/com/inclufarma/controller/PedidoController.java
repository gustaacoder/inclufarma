package com.inclufarma.controller;

import com.inclufarma.dto.CriarPedidoRequestDTO;
import com.inclufarma.dto.EnderecoDTO;
import com.inclufarma.dto.EnderecoPedidoDTO;
import com.inclufarma.dto.ItensPedidoDTO;
import com.inclufarma.model.Endereco;
import com.inclufarma.model.ItensPedido;
import com.inclufarma.model.Pedidos;
import com.inclufarma.model.Usuario;
import com.inclufarma.repository.EnderecoRepository;
import com.inclufarma.repository.ItensPedidoRepository;
import com.inclufarma.repository.PedidosRepository;
import com.inclufarma.service.AuthenticationService;
import com.inclufarma.service.EnderecoService;
import com.inclufarma.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/pedido")
public class PedidoController {

    private final AuthenticationService authenticationService;
    private final PedidoService pedidoService;

    @Operation(summary = "Listar pedidos do usuário logado")
    @GetMapping("/listarPedidos")
    public List<ItensPedido> listarPedidosDoUsuarioLogado() {
        Usuario usuarioLogado = authenticationService.getUsuarioLogado();
        return pedidoService.listarItensPedidoUsuarioLogado(usuarioLogado.getId());
    }

    @Operation(summary = "Criar pedido")
    @PostMapping("/criarPedido")
    public ResponseEntity<?> criarPedido(@RequestBody CriarPedidoRequestDTO requestDTO) {
        try {
            List<ItensPedidoDTO> ItensDto = requestDTO.itensDto();
            EnderecoPedidoDTO enderecoPedidoDTO = requestDTO.enderecoPedidoDTO();

            Pedidos novoPedido = pedidoService.criarPedido(ItensDto, enderecoPedidoDTO);
            return new ResponseEntity<>(novoPedido, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
