package com.accenture.franchise_api.application.useCase;

import com.accenture.franchise_api.domain.exceptions.ResourceNotFoundException;
import com.accenture.franchise_api.domain.model.Branch;
import com.accenture.franchise_api.domain.model.Franchise;
import com.accenture.franchise_api.domain.port.BranchRepository;
import com.accenture.franchise_api.domain.port.FranchiseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddBranchToFranchiseUseCaseTest {
    @Mock
    private FranchiseRepository franchiseRepository;

    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private AddBranchToFranchiseUseCase useCase;

    @Test
    void shouldAddBranchWhenFranchiseExists() {
        Franchise franchise = new Franchise(1L, "Frisby");
        Branch savedBranch = new Branch(10L, 1L, "Sucursal Norte");

        when(franchiseRepository.findById(1L)).thenReturn(Mono.just(franchise));
        when(branchRepository.save(any(Branch.class))).thenReturn(Mono.just(savedBranch));

        StepVerifier.create(useCase.execute(1L, "Sucursal Norte"))
                .expectNext(savedBranch)
                .verifyComplete();

        verify(branchRepository).save(any(Branch.class));
    }

    @Test
    void shouldEmitNotFoundWhenFranchiseDoesNotExist() {
        when(franchiseRepository.findById(99L)).thenReturn(Mono.empty());

        StepVerifier.create(useCase.execute(99L, "Sucursal Norte"))
                .expectError(ResourceNotFoundException.class)
                .verify();

        verifyNoInteractions(branchRepository);
    }
}
