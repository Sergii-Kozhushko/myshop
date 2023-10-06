//package de.edu.telran.myshop.repository;
//
//import de.edu.telran.myshop.entity.Supplier;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.annotation.DirtiesContext;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
//@DataJpaTest // make app-context only with jpa-layer
//// don't replace actual bd with test h2
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
////@Transactional // roll-back after test execution
////@TestPropertySource(properties = {
////        "spring.jpa.hibernate.ddl-auto=validate"
////})
//
//class SupplierRepositoryTest {
//    @Autowired
//    private SupplierRepository supplierRepository;
//
//
//    @Test
//    void saveSupplier_checkSaved() {
//        Supplier supplierToSave = Supplier.builder().
//                name("supplier 1")
//                .phone("+491703456789")
//                .email("supplylink@hotmail.com")
//                .active(true)
//                .build();
//        supplierRepository.save(supplierToSave);
//        Supplier result = supplierRepository.findById(supplierToSave.getId()).orElse(null);
//        assertNotNull(result);
//        assertEquals(result.getId(), supplierToSave.getId());
//        assertEquals(result.getName(), supplierToSave.getName());
//        assertEquals(result.getPhone(), supplierToSave.getPhone());
//        assertEquals(result.getEmail(), supplierToSave.getEmail());
//    }
//
//    @Test
//    void updateSupplier_checkUpdated() {
//        Supplier supplierToSave = Supplier.builder()
//                .name("supplier 2")
//                .phone("+491703456780")
//                .email("supplier2@example.com")
//                .active(false)
//                .build();
//
//        supplierRepository.save(supplierToSave);
//
//        Supplier updatedSupplier = Supplier.builder()
//                .name("updated supplier 2")
//                .phone("+491703456781")
//                .email("updated_supplier2@example.com")
//                .active(true)
//                .build();
//        updatedSupplier.setId(supplierToSave.getId());
//
//        supplierRepository.save(updatedSupplier);
//
//        Supplier result = supplierRepository.findById(updatedSupplier.getId()).orElse(null);
//        assertNotNull(result);
//        assertEquals(result.getId(), updatedSupplier.getId());
//        assertEquals(result.getName(), updatedSupplier.getName());
//        assertEquals(result.getPhone(), updatedSupplier.getPhone());
//        assertEquals(result.getEmail(), updatedSupplier.getEmail());
//    }
//
//    @Test
//    @DirtiesContext
//    void deleteSupplier_checkDeleted() {
//        Supplier supplierToSave = Supplier.builder()
//                .name("supplier 3")
//                .phone("+491703456782")
//                .email("supplier3@example.com")
//                .active(true)
//                .build();
//
//        supplierRepository.save(supplierToSave);
//
//        supplierRepository.deleteById(supplierToSave.getId());
//
//        assertFalse(supplierRepository.existsById(supplierToSave.getId()));
//    }
//
//}