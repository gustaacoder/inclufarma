package com.inclufarma.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/pedido")
public class PedidoController {

    private final AuthenticationService authenticationService;
    private final ItensPedidoRepository itensPedidoRepository;
    private final PedidoService pedidoService;

    @Operation(summary = "Listar pedidos do usuário logado")
    @GetMapping("/listarPedidos")
    public List<Pedidos> listarPedidosDoUsuarioLogado() {
        Usuario usuarioLogado = authenticationService.getUsuarioLogado();
        return pedidoService.listarItensPedidoUsuarioLogado(usuarioLogado.getId());
    }
}
