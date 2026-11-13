package com.accenture.franchise_api.domain.model;

import com.accenture.franchise_api.domain.exceptions.DomainValidationException;

public record Product(Long id, Long branchId, String name, Integer stock) {
    public Product {
        if(branchId == null || branchId <=0 ) {
            throw new DomainValidationException("Se requiere un Id de sucursal válido");
        }
        if(name == null || name.isEmpty()) {
            throw new DomainValidationException("El nombre del producto no puede ser nulo ni estar vacío");
        }
        if(stock == null || stock < 0) {
            throw new DomainValidationException("El stock del producto no puede ser nulo ni negativo");
        }
        name = name.trim();
    }

    public static Product create(Long branchId, String name, Integer stock) {
        return new Product(null, branchId, name, stock);
    }

    public Product withName(String newName) {
        return new Product(this.id, this.branchId, newName, this.stock);
    }

    public Product withStock(Integer newStock) {
        return new Product(this.id, this.branchId, this.name, newStock);
    }
}
