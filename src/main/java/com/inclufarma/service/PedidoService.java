package com.inclufarma.service;

import com.inclufarma.dto.EnderecoDTO;
import com.inclufarma.dto.EnderecoPedidoDTO;
import com.inclufarma.dto.ItensPedidoDTO;
import com.inclufarma.model.*;
import com.inclufarma.repository.*;
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
    private final EnderecoRepository enderecoRepository;

    public List<ItensPedido> listarItensPedidoUsuarioLogado(UUID usuarioId) {
        try{
            return itensPedidoRepository.findByPedidosUsuarioId(usuarioId);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar endereços do usuário: " + e.getMessage(), e);
        }
    }

    public Pedidos criarPedido(List<ItensPedidoDTO> ItensDto, EnderecoPedidoDTO enderecoPedidoDTO) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Pedidos pedido = new Pedidos();

        Endereco endereco = new Endereco();
        endereco.setBairro(enderecoPedidoDTO.endereco().getBairro());
        endereco.setCep(enderecoPedidoDTO.endereco().getCep());
        endereco.setCidade(enderecoPedidoDTO.endereco().getCidade());
        endereco.setLogradouro(enderecoPedidoDTO.endereco().getLogradouro());
        endereco.setNumero(enderecoPedidoDTO.endereco().getNumero());

        enderecoRepository.save(endereco);

        pedido.setEndereco(endereco);
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
