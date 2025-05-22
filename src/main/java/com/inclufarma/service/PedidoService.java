package com.inclufarma.service;

import com.inclufarma.dto.EnderecoDTO;
import com.inclufarma.dto.ItensPedidoDTO;
import com.inclufarma.model.ItensPedido;
import com.inclufarma.model.Medicamento;
import com.inclufarma.model.Pedidos;
import com.inclufarma.model.Usuario;
import com.inclufarma.repository.ItensPedidoRepository;
import com.inclufarma.repository.MedicamentoRepository;
import com.inclufarma.repository.PedidosRepository;
import com.inclufarma.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PedidoService {

    private final ItensPedidoRepository itensPedidoRepository;
    private final PedidosRepository pedidosRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final UsuarioRepository usuarioRepository;

    public List<ItensPedido> listarItensPedidoUsuarioLogado(UUID usuarioId) {
        try{
            return itensPedidoRepository.findByPedidoUsuarioId(usuarioId);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar endereços do usuário: " + e.getMessage(), e);
        }
    }

    public Pedidos criarPedido(List<ItensPedidoDTO> ItensDto, EnderecoDTO enderecoDTO) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Pedidos pedido = new Pedidos();
        pedido.setEndereco(enderecoDTO.endereco());
        pedido.setUsuario(usuario);
        pedidosRepository.save(pedido);

        for (ItensPedidoDTO dto : ItensDto) {
            Medicamento medicamento = medicamentoRepository.findById(dto.medicamento().getId()).orElseThrow();
            ItensPedido item = new ItensPedido(pedido, medicamento, dto.quantidade(), dto.precoUnitario());
            itensPedidoRepository.save(item);
        }

        return pedido;
    }
}
