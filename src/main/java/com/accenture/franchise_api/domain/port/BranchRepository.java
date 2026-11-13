package com.accenture.franchise_api.domain.port;

import com.accenture.franchise_api.domain.model.Branch;
import reactor.core.publisher.Mono;

public interface BranchRepository {
    Mono<Branch> save(Branch branch);
    Mono<Branch> findById(Long id);
    Mono<Boolean> existsById(Long id);

}
