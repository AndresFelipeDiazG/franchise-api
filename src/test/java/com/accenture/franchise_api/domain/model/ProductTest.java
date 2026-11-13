package com.accenture.franchise_api.domain.model;

import com.accenture.franchise_api.domain.exceptions.DomainValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {
    @Test
    void shouldCreateProductWithZeroOrPositiveStockTrinName() {
        Product product = Product.create(5L, "Hamburguesa Doble", 0);

        assertNull(product.id());
        assertEquals(5L, product.branchId());
        assertEquals("Hamburguesa Doble", product.name());
        assertEquals(0, product.stock());
    }

    @Test
    void shouldUpdateNameAndStockInmmutably() {
        Product original = new Product(5L, 10L, "Hamburguesa Doble", 15);
        Product renamed = original.withName("Hamburguesa Especial");
        Product restocked = renamed.withStock(50);

        assertEquals(5L, restocked.id());
        assertEquals(10L, restocked.branchId());
        assertEquals("Hamburguesa Especial", restocked.name());
        assertEquals(50, restocked.stock());
        assertEquals("Hamburguesa Doble", original.name());
        assertEquals(15, original.stock());
    }

    @Test
    void shouldThrowExceptionWhenBranchIdIsNullOrBlank() {
        assertThrows(DomainValidationException.class, () -> Product.create(null,"Hamburguesa",10 ));
        assertThrows(DomainValidationException.class, () -> Product.create(0L,"Hamburguesa",10));
        assertThrows(DomainValidationException.class, () -> Product.create(-2L,"Hamburguesa",10));
    }

    @Test
    void shouldThrowExceptionWhenNameIsNullOrBlank() {
        assertThrows(DomainValidationException.class, () -> Product.create(1L,null,10 ));
        assertThrows(DomainValidationException.class, () -> Product.create(0L," ",10));
    }

    @Test
    void shouldThrowExceptionWhenStockIsNullOrNegative() {
        assertThrows(DomainValidationException.class, () -> Product.create(1L,"Hamburguesa",null ));
        assertThrows(DomainValidationException.class, () -> Product.create(0L,"Hamburguesa",-1));

        Product existing = new Product(5L, 10L, "Hamburguesa Doble", 15);
        assertThrows(DomainValidationException.class, () -> existing.withStock(-1));
    }
}
