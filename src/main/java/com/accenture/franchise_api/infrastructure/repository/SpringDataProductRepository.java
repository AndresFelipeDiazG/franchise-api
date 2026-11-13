package com.accenture.franchise_api.infrastructure.repository;

import com.accenture.franchise_api.domain.model.BranchTopProduct;
import com.accenture.franchise_api.infrastructure.entity.ProductEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface SpringDataProductRepository extends ReactiveCrudRepository<ProductEntity,Long> {
    @Query("""
            SELECT DISTINCT ON (b.id)
            b.id AS branch_id,
            b.name AS branch_name,
            b.id AS product_id,
            p.name AS product_name,
            p.stock AS stock
            FROM branches b
            INNER JOIN products p ON p.branch_id = b.id
            WHERE b.franchise_id = :franchiseId
            ORDER BY b.id ASC, p.stock DESC, p.id ASC
            """)
    Flux<BranchTopProduct> findTopProductsByBranchId(Long franchiseId);
}
