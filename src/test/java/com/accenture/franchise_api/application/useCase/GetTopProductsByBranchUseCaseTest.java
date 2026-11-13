package com.accenture.franchise_api.application.useCase;

import com.accenture.franchise_api.domain.exceptions.ResourceNotFoundException;
import com.accenture.franchise_api.domain.model.BranchTopProduct;
import com.accenture.franchise_api.domain.model.Franchise;
import com.accenture.franchise_api.domain.port.FranchiseRepository;
import com.accenture.franchise_api.domain.port.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetTopProductsByBranchUseCaseTest {

    @Mock
    private FranchiseRepository franchiseRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private GetTopProductByBranchUseCase useCase;

    @Test
    void shouldReturnTopProductsByBranchWhenFranchiseExists() {
        Franchise franchise = new Franchise(1L, "Frisby");
        BranchTopProduct northTop = new BranchTopProduct(10L, "Sucursal Norte", 100L, "Combo Familiar", 80);
        BranchTopProduct southTop = new BranchTopProduct(20L, "Sucursal Sur", 205L, "Pollo Entero", 120);

        when(franchiseRepository.findById(1L)).thenReturn(Mono.just(franchise));
        when(productRepository.findTopProductsByFranchiseId(1L)).thenReturn(Flux.just(northTop, southTop));

        StepVerifier.create(useCase.execute(1L))
                .expectNext(northTop, southTop)
                .verifyComplete();
    }

    @Test
    void shouldEmitNotFoundWhenFranchiseDoesNotExist() {
        when(franchiseRepository.findById(99L)).thenReturn(Mono.empty());

        StepVerifier.create(useCase.execute(99L))
                .expectError(ResourceNotFoundException.class)
                .verify();

        verifyNoInteractions(productRepository);
    }
}