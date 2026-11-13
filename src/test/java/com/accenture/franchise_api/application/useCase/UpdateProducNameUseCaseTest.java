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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateProductNameUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private UpdateProductNameUseCase useCase;

    @Test
    void shouldUpdateProductNameSuccessfully() {
        Product existing = new Product(100L, 10L, "Combo Familiar", 50);
        Product updated = new Product(100L, 10L, "Mega Combo", 50);

        when(productRepository.findById(100L)).thenReturn(Mono.just(existing));
        when(productRepository.save(updated)).thenReturn(Mono.just(updated));

        StepVerifier.create(useCase.execute(100L, "Mega Combo"))
                .expectNext(updated)
                .verifyComplete();
    }

    @Test
    void shouldEmitNotFoundWhenProductDoesNotExist() {
        when(productRepository.findById(999L)).thenReturn(Mono.empty());

        StepVerifier.create(useCase.execute(999L, "Mega Combo"))
                .expectError(ResourceNotFoundException.class)
                .verify();

        verify(productRepository, never()).save(any());
    }
}