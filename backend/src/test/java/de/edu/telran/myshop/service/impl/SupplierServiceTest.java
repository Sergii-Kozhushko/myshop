package de.edu.telran.myshop.service.impl;

import de.edu.telran.myshop.entity.Supplier;
import de.edu.telran.myshop.exception.InvalidSupplierParameterException;
import de.edu.telran.myshop.repository.ProductRepository;
import de.edu.telran.myshop.repository.SupplierRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SupplierServiceTest {
    @Mock
    SupplierRepository supplierRepository;
    @InjectMocks
    SupplierServiceImpl supplierService;

    @Test
    public void testGetAllSuppliers_ReturnsListOfSuppliers() {
        List<Supplier> suppliers = Arrays.asList(new Supplier(), new Supplier());

        when(supplierRepository.findAll()).thenReturn(suppliers);

        List<Supplier> result = supplierService.getAll();

        assertThat(result).isEqualTo(suppliers);
    }

    @Test
    public void testCreateSupplier_ValidSupplier_Success() {
        Supplier supplierToCreate = Supplier.builder().name("supplier 1")
                .build();


        when(supplierRepository.saveAndFlush(any(Supplier.class))).thenReturn(supplierToCreate);

        Supplier createdSupplier = supplierService.create(supplierToCreate);

        assertThat(createdSupplier).isEqualTo(supplierToCreate);
    }

    @Test
    public void testGetSupplierById_ValidId_ReturnsSupplier() {
        Long supplierId = 1L;
        Supplier supplier = new Supplier();
        supplier.setId(supplierId);

        when(supplierRepository.findById(anyLong())).thenReturn(Optional.of(supplier));

        Supplier result = supplierService.getById(supplierId);

        assertThat(result).isEqualTo(supplier);
    }

    @Test
    @Transactional
    public void testDeleteSupplier_ValidId_DeletesSupplier() {
        Long supplierId = 1L;

        when(supplierRepository.findById(anyLong())).thenReturn(Optional.of(new Supplier()));

        supplierService.delete(supplierId);

        verify(supplierRepository).deleteById(supplierId);
    }

    @Test
    public void testDeleteSupplier_SupplierNotFound_ThrowsException() {
        Long supplierId = 1L;

        when(supplierRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(InvalidSupplierParameterException.class, () -> supplierService.delete(supplierId));
        verify(supplierRepository, never()).deleteById(anyLong());
    }


    @Test
    public void testFindMaxId_ReturnsMaxId() {
        Long maxId = 10L;

        when(supplierRepository.maxSupplierId()).thenReturn(maxId);

        Long result = supplierService.findMaxId();

        assertThat(result).isEqualTo(maxId);
    }
}
