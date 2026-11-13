package com.accenture.franchise_api.infrastructure.web.dto;

import com.accenture.franchise_api.domain.model.Franchise;

public record FranchiseResponse(Long id, String name) {
    public static FranchiseResponse fromDomain(Franchise franchise) {
        return new FranchiseResponse(franchise.id(),  franchise.name());
    }
}
