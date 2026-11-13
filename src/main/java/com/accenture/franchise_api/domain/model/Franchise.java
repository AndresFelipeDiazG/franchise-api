package com.accenture.franchise_api.domain.model;

import com.accenture.franchise_api.domain.exceptions.DomainValidationException;

public record Franchise(Long id, String name) {
    public Franchise {
        if (name == null || name.isBlank()) {
            throw new DomainValidationException("El nombre de la franquicia no puede ser nula o estar vacío");
        }
        name = name.trim();
    }

    public static Franchise create(String name) {
        return new Franchise(null, name);
    }

    public Franchise withName(String newName) {
        return new Franchise(this.id, newName);
    }
}
