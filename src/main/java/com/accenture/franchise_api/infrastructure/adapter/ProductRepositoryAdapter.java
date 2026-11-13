package com.accenture.franchise_api.infrastructure.adapter;

import com.accenture.franchise_api.domain.model.BranchTopProduct;
import com.accenture.franchise_api.domain.model.Product;
import com.accenture.franchise_api.domain.port.ProductRepository;
import com.accenture.franchise_api.infrastructure.entity.ProductEntity;
import com.accenture.franchise_api.infrastructure.repository.SpringDataProductRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ProductRepositoryAdapter implements ProductRepository {

    private SpringDataProductRepository repository;

    public ProductRepositoryAdapter(SpringDataProductRepository repository) {
        this.repository = repository;
    }


    @Override
    public Mono<Product> save(Product product) {
        ProductEntity entity = new ProductEntity(product.id(), product.branchId(), product.name(), product.stock());
        return repository.save(entity)
                .map(saved -> new Product(saved.getId(), saved.getBranchId(), saved.getName(), saved.getStock()));
    }

    @Override
    public Mono<Product> findById(Long id) {
        return repository.findById(id)
                .map(saved -> new Product(saved.getId(), saved.getBranchId(), saved.getName(), saved.getStock()));
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id);
    }

    @Override
    public Flux<BranchTopProduct> findTopProductsByFranchiseId(Long franchiseId) {
        return repository.findTopProductsByBranchId(franchiseId);
    }
}
