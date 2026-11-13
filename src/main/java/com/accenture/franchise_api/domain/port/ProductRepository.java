package com.accenture.franchise_api.domain.port;

import com.accenture.franchise_api.domain.model.BranchTopProduct;
import com.accenture.franchise_api.domain.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    Mono<Product> save(Product product);
    Mono<Product> findById(Long id);
    Mono<Void> deleteById(Long id);
    Flux<BranchTopProduct> findTopProductsByFranchiseId(Long franchiseId);
}
