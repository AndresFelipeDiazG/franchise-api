package com.accenture.franchise_api.application.useCase;


import com.accenture.franchise_api.domain.exceptions.ResourceNotFoundException;
import com.accenture.franchise_api.domain.model.Branch;
import com.accenture.franchise_api.domain.port.BranchRepository;
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
class UpdateBranchNameUseCaseTest {

    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private UpdateBranchNameUseCase useCase;

    @Test
    void shouldUpdateBranchNameSuccessfully() {
        Branch existing = new Branch(10L, 1L, "Sucursal Norte");
        Branch updated = new Branch(10L, 1L, "Sucursal Centro");

        when(branchRepository.findById(10L)).thenReturn(Mono.just(existing));
        when(branchRepository.save(updated)).thenReturn(Mono.just(updated));

        StepVerifier.create(useCase.execute(10L, "Sucursal Centro"))
                .expectNext(updated)
                .verifyComplete();
    }

    @Test
    void shouldEmitNotFoundWhenBranchDoesNotExist() {
        when(branchRepository.findById(99L)).thenReturn(Mono.empty());

        StepVerifier.create(useCase.execute(99L, "Sucursal Centro"))
                .expectError(ResourceNotFoundException.class)
                .verify();

        verify(branchRepository, never()).save(any());
    }
}