package com.accenture.franchise_api.domain.model;

import com.accenture.franchise_api.domain.exceptions.DomainValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FranchiseTest {

    @Test
    void shouldCreateFranchisenAndTrimName() {
        Franchise franchise = Franchise.create("Frisby Colombia");

        assertNull(franchise.id());
        assertEquals("Frisby Colombia", franchise.name());
    }

    @Test
    void shouldUpdateNameInmutably() {
        Franchise original = new Franchise(1L, "Frisby");
        Franchise updated = original.withName("Updated Frisby");

        assertEquals(1L, updated.id());
        assertEquals("Updated Frisby", updated.name());
        assertEquals("Frisby", original.name());
    }

    @Test
    void shouldThrowExceptionNameisNullOrBlank() {
        assertThrows(DomainValidationException.class, () -> Franchise.create(null));
        assertThrows(DomainValidationException.class, () -> Franchise.create(" "));
    }
}
