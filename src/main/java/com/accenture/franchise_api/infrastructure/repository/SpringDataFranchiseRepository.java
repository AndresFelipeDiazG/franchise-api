package com.accenture.franchise_api.infrastructure.repository;

import com.accenture.franchise_api.infrastructure.entity.FranchiseEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SpringDataFranchiseRepository extends ReactiveCrudRepository<FranchiseEntity,Long> {
}
