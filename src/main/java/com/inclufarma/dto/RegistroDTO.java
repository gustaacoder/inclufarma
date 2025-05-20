package com.inclufarma.dto;

import com.inclufarma.enums.UserRole;

public record RegistroDTO(String nome, String senha, String email, UserRole role) {
}
