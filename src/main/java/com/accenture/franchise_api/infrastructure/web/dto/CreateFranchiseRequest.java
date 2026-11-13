package com.accenture.franchise_api.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateFranchiseRequest(
        @NotBlank(message = "El nombre de la franquicia es obligatoria")
        String name
        ) {
}
