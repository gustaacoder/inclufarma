package com.inclufarma.security;

import com.inclufarma.model.Usuario;
import com.inclufarma.repository.UsuarioRepository;
import com.inclufarma.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // Lista de rotas públicas
        List<String> publicPaths = List.of(
                "/api/auth/login",
                "/api/auth/register",
                "/api/categoria/listarCategoria",
                "/api/medicamento/listar",
                "/swagger-ui",
                "/v3/api-docs"
        );

        // Se for rota pública, não validar token
        if (publicPaths.stream().anyMatch(path::startsWith)) {
            filterChain.doFilter(request, response);
            return;
        }

        var token = this.recoverToken(request);

        System.out.println("Token recebido: " + token);

        if (token != null) {
            try {
                var email = tokenService.validateToken(token);

                if (email == null) {
                    System.out.println("Token inválido ou expirado");
                    filterChain.doFilter(request, response);
                    return;
                }

                System.out.println("Email recuperado do token: " + email);

                Usuario usuario = usuarioRepository.findByEmailIgnoreCase(email)
                        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

                var authentication = new UsernamePasswordAuthenticationToken(
                        email,
                        null,
                        usuario.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                System.out.println("Erro na validação do token: " + e.getMessage());
                filterChain.doFilter(request, response);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader == null) return null;

        return authorizationHeader.replace("Bearer ", "");
    }
}
