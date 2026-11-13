package com.accenture.franchise_api.infrastructure.adapter;

import com.accenture.franchise_api.domain.model.Franchise;
import com.accenture.franchise_api.domain.port.FranchiseRepository;
import com.accenture.franchise_api.infrastructure.entity.FranchiseEntity;
import com.accenture.franchise_api.infrastructure.repository.SpringDataBranchRepository;
import com.accenture.franchise_api.infrastructure.repository.SpringDataFranchiseRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class FranchiseRepositoryAdapter implements FranchiseRepository {

    private final SpringDataFranchiseRepository repository;

    public FranchiseRepositoryAdapter(SpringDataFranchiseRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Franchise> save(Franchise franchise) {
        FranchiseEntity entity = new FranchiseEntity(franchise.id(),  franchise.name());
        return repository.save(entity)
                .map(saved -> new Franchise(saved.getId(), saved.getName()));
    }

    @Override
    public Mono<Franchise> findById(Long id) {
        return repository.findById(id)
                .map(saved -> new Franchise(saved.getId(), saved.getName()));
    }

    @Override
    public Mono<Boolean> existById(Long id) {
        return repository.existsById(id);
    }
}
