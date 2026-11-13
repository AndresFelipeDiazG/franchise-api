package com.accenture.franchise_api.application.useCase;


import com.accenture.franchise_api.domain.exceptions.ResourceNotFoundException;
import com.accenture.franchise_api.domain.model.Franchise;
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
public class UpdateFranchiseNameUseCaseTest {
    @Mock
    private FranchiseRepository franchiseRepository;

    @InjectMocks
    private UpdateFranchiseNameUseCase useCase;

    @Test
    void shouldUpdateFranchiseNameSuccessfully() {
        Franchise existing = new Franchise(1L, "Frisby");
        Franchise updated = new Franchise(1L, "Frisby Colombia");

        when(franchiseRepository.findById(1L)).thenReturn(Mono.just(existing));
        when(franchiseRepository.save(updated)).thenReturn(Mono.just(updated));

        StepVerifier.create(useCase.execute(1L, "Frisby Colombia"))
                .expectNext(updated)
                .verifyComplete();
    }

    @Test
    void shouldEmitNotFoundWhenFranchiseDoesNotExist() {
        when(franchiseRepository.findById(99L)).thenReturn(Mono.empty());

        StepVerifier.create(useCase.execute(99L, "Nuevo Nombre"))
                .expectError(ResourceNotFoundException.class)
                .verify();

        verify(franchiseRepository, never()).save(any());
    }
}
