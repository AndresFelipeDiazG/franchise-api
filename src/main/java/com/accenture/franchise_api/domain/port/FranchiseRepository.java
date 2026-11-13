package com.accenture.franchise_api.domain.port;

import com.accenture.franchise_api.domain.model.Franchise;
import reactor.core.publisher.Mono;

public interface FranchiseRepository {
    Mono<Franchise> save(Franchise franchise);
    Mono<Franchise> findById(Long id);
    Mono<Boolean> existById(Long id);
}
