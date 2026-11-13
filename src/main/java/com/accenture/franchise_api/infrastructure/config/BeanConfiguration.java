package com.accenture.franchise_api.infrastructure.config;

import com.accenture.franchise_api.application.useCase.*;
import com.accenture.franchise_api.domain.port.BranchRepository;
import com.accenture.franchise_api.domain.port.FranchiseRepository;
import com.accenture.franchise_api.domain.port.ProductRepository;
import org.springframework.context.annotation.Bean;

public class BeanConfiguration {
    @Bean
    public CreateFranchiseUseCase createFranchiseUseCase(FranchiseRepository franchiseRepository) {
        return new CreateFranchiseUseCase(franchiseRepository);
    }

    @Bean
    public UpdateFranchiseNameUseCase updateFranchiseNameUseCase(FranchiseRepository franchiseRepository) {
        return new UpdateFranchiseNameUseCase(franchiseRepository);
    }

    @Bean
    public AddBranchToFranchiseUseCase addBranchToFranchiseUseCase(FranchiseRepository franchiseRepository, BranchRepository branchRepository) {
        return new AddBranchToFranchiseUseCase(franchiseRepository, branchRepository);
    }

    @Bean
    public UpdateBranchNameUseCase updateBranchNameUseCase(BranchRepository branchRepository) {
        return new UpdateBranchNameUseCase(branchRepository);
    }

    @Bean
    public AddProductToBranchUseCase addProductToBranchUseCase(BranchRepository branchRepository, ProductRepository productRepository) {
        return new AddProductToBranchUseCase(branchRepository, productRepository);
    }

    @Bean
    public DeleteProductBranchUseCase deleteProductBranchUseCase(ProductRepository productRepository) {
        return new DeleteProductBranchUseCase(productRepository);
    }

    @Bean
    public UpdateProductNameUseCase updateProductNameUseCase(ProductRepository productRepository) {
        return new UpdateProductNameUseCase(productRepository);
    }

    @Bean
    public UpdateProductStockUseCase updateProductStockUseCase(ProductRepository productRepository) {
        return new UpdateProductStockUseCase(productRepository);
    }

    @Bean
    public GetTopProductByBranchUseCase getTopProductByBranchUseCase(BranchRepository branchRepository, ProductRepository productRepository, FranchiseRepository franchiseRepository) {
        return new GetTopProductByBranchUseCase(franchiseRepository, branchRepository, productRepository);
    }
}
