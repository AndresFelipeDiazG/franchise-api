package com.accenture.franchise_api.application.useCase;

import com.accenture.franchise_api.domain.exceptions.ResourceNotFoundException;
import com.accenture.franchise_api.domain.model.Branch;
import com.accenture.franchise_api.domain.model.Product;
import com.accenture.franchise_api.domain.port.BranchRepository;
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
class AddProductToBranchUseCaseTest {

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private AddProductToBranchUseCase useCase;

    @Test
    void shouldAddProductWhenBranchExists() {
        Branch branch = new Branch(10L, 1L, "Sucursal Norte");
        Product savedProduct = new Product(100L, 10L, "Combo Familiar", 50);

        when(branchRepository.findById(10L)).thenReturn(Mono.just(branch));
        when(productRepository.save(any(Product.class))).thenReturn(Mono.just(savedProduct));

        StepVerifier.create(useCase.execute(10L, "Combo Familiar", 50))
                .expectNext(savedProduct)
                .verifyComplete();
    }

    @Test
    void shouldEmitNotFoundWhenBranchDoesNotExist() {
        when(branchRepository.findById(99L)).thenReturn(Mono.empty());

        StepVerifier.create(useCase.execute(99L, "Combo Familiar", 50))
                .expectError(ResourceNotFoundException.class)
                .verify();

        verifyNoInteractions(productRepository);
    }
}
