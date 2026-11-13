package com.accenture.franchise_api.domain.model;

import com.accenture.franchise_api.domain.exceptions.DomainValidationException;

public record Branch(Long id, Long franchiseID, String name) {

    public Branch {
        if (franchiseID == null || franchiseID <= 0) {
            throw new DomainValidationException("El id de la franquicia no existe");
        }
        if (name == null || name.isBlank()) {
            throw new DomainValidationException("El nombre no puede ser nulo o estar vacío");
        }
        name = name.trim();
    }

    public static Branch create(Long franchiseID, String name) {
        return new Branch(null, franchiseID, name);
    }

    public Branch withName(String newName) {
        return new Branch(this.id, this.franchiseID, newName);
    }
}
