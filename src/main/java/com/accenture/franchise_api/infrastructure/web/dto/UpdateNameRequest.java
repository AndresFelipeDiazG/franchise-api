package com.accenture.franchise_api.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateNameRequest(
        @NotBlank( message = "El nuevo nombre es obligatorio")
        String name
) {
}
