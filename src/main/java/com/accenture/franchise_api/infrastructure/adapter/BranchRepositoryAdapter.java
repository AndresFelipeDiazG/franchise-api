package com.accenture.franchise_api.infrastructure.adapter;

import com.accenture.franchise_api.domain.model.Branch;
import com.accenture.franchise_api.domain.port.BranchRepository;
import com.accenture.franchise_api.infrastructure.entity.BranchEntity;
import com.accenture.franchise_api.infrastructure.repository.SpringDataBranchRepository;
import reactor.core.publisher.Mono;

public class BranchRepositoryAdapter implements BranchRepository {

    private final SpringDataBranchRepository repository;

    public BranchRepositoryAdapter(SpringDataBranchRepository repository) {
        this.repository = repository;
    }


    @Override
    public Mono<Branch> save(Branch branch) {
        BranchEntity entity = new BranchEntity(branch.id(), branch.franchiseID(),  branch.name());
        return repository.save(entity)
                .map(saved -> new Branch(saved.getId(), saved.getFranchiseID(), saved.getName()));
    }

    @Override
    public Mono<Branch> findById(Long id) {
        return repository.findById(id)
                .map(saved -> new Branch(saved.getId(), saved.getFranchiseID(), saved.getName()));
    }

    @Override
    public Mono<Boolean> existsById(Long id) {
        return  repository.existsById(id);
    }
}
