package com.accenture.franchise_api.application.useCase;

import com.accenture.franchise_api.domain.exceptions.DomainValidationException;
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
public class CreateFranchiseUseCaseTest {
    @Mock
    private FranchiseRepository franchiseRepository;

    @InjectMocks
    private CreateFranchiseUseCase useCase;

    @Test
    void shouldCreateAndSaveFranchiseSuccessfully() {
        Franchise saved = new Franchise(1L, "Frisby");
        when(franchiseRepository.save(any(Franchise.class))).thenReturn(Mono.just(saved));

        StepVerifier.create(useCase.execute("  Frisby  "))
                .expectNextMatches(f -> f.id().equals(1L) && f.name().equals("Frisby"))
                .verifyComplete();

        verify(franchiseRepository, times(1)).save(any(Franchise.class));
    }

    @Test
    void shouldEmitValidationErrorWhenNameIsBlank() {
        StepVerifier.create(useCase.execute("   "))
                .expectError(DomainValidationException.class)
                .verify();

        verifyNoInteractions(franchiseRepository);
    }
}
