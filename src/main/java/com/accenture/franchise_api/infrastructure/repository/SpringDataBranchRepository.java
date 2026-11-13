package com.accenture.franchise_api.infrastructure.repository;

import com.accenture.franchise_api.infrastructure.entity.BranchEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SpringDataBranchRepository extends ReactiveCrudRepository<BranchEntity,Long> {
}
