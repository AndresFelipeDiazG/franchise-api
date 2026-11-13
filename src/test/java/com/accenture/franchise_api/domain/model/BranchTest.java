package com.accenture.franchise_api.domain.model;

import com.accenture.franchise_api.domain.exceptions.DomainValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BranchTest {
    @Test
    void shouldCreateBranchAndTrimName() {
        Branch branch = Branch.create(10L, "Sucursal Norte");

        assertNull(branch.id());
        assertEquals(10L, branch.franchiseID());
        assertEquals("Sucursal Norte", branch.name());
    }

    @Test
    void shouldUpdateNameInmmutably() {
        Branch original = new Branch(1L,10L, "Sucursal Norte");
        Branch updated = original.withName("Sucursal Norte");

        assertEquals(1L, updated.id());
        assertEquals(10L, updated.franchiseID());
        assertEquals("Sucursal Norte", updated.name());
        assertEquals("Sucursal Norte", original.name());
    }

    @Test
    void shouldThrowExceptionWhenFranchiseIdIsNull() {
        assertThrows(DomainValidationException.class, () -> Branch.create(null, "Sucursal Norte"));
        assertThrows(DomainValidationException.class, () -> Branch.create(0L, "Sucursal Norte"));
        assertThrows(DomainValidationException.class, () -> Branch.create(-1L, "Sucursal Norte"));
    }

    @Test
    void shouldThrowExceptionWhenNameIsNullOrBlank() {
        assertThrows(DomainValidationException.class, () -> Branch.create(10L, null));
        assertThrows(DomainValidationException.class, () -> Branch.create(0L, " "));
    }
}
