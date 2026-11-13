package com.accenture.franchise_api.application.useCase;

import com.accenture.franchise_api.domain.exceptions.ResourceNotFoundException;
import com.accenture.franchise_api.domain.model.Product;
import com.accenture.franchise_api.domain.port.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteProductFromBranchUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private DeleteProductBranchUseCase useCase;

    @Test
    void shouldDeleteProductWhenItBelongsToBranch() {
        Product product = new Product(100L, 10L, "Combo Familiar", 50);

        when(productRepository.findById(100L)).thenReturn(Mono.just(product));
        when(productRepository.deleteById(100L)).thenReturn(Mono.empty());

        StepVerifier.create(useCase.execute(10L, 100L))
                .verifyComplete();

        verify(productRepository).deleteById(100L);
    }

    @Test
    void shouldEmitNotFoundWhenProductBelongsToAnotherBranch() {
        Product productFromOtherBranch = new Product(100L, 20L, "Combo Familiar", 50);

        when(productRepository.findById(100L)).thenReturn(Mono.just(productFromOtherBranch));

        StepVerifier.create(useCase.execute(100L, 10L))
                .expectError(ResourceNotFoundException.class)
                .verify();

        verify(productRepository, never()).deleteById(anyLong());
    }
}