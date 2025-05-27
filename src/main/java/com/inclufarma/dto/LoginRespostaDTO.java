package com.inclufarma.dto;

import com.inclufarma.enums.UserRole;

public record LoginRespostaDTO(String token, String nome, UserRole role) {
}
