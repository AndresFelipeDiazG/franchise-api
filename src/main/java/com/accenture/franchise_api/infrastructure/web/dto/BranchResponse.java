package com.accenture.franchise_api.infrastructure.web.dto;

import com.accenture.franchise_api.domain.model.Branch;

public record BranchResponse(Long id, Long franchiseId, String name) {
    public static BranchResponse fromDomain(Branch branch) {
        return new BranchResponse(branch.id(), branch.franchiseID(), branch.name());
    }
}
