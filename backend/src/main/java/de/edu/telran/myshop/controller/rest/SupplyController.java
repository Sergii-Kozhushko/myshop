/**
 * ProductController.java
 *
 * @author Sergii Kozhushko, sergiikozhushko@gmail.com
 * Date of creation: 27-Jun-2023 18:48
 */

package de.edu.telran.myshop.controller.rest;

import de.edu.telran.myshop.entity.Supply;
import de.edu.telran.myshop.entity.SupplyItem;
import de.edu.telran.myshop.service.impl.SupplyItemServiceImpl;
import de.edu.telran.myshop.service.impl.SupplyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(SupplyController.MAIN_PATH)
@RequiredArgsConstructor
public class SupplyController {
    private final SupplyServiceImpl supplyService;
    private final SupplyItemServiceImpl supplyItemService;

    public static final String MAIN_PATH = "/supply";
    public static final String ID_COLUMN = "id";

    @GetMapping("/all")
    public ResponseEntity<List<Supply>> getAllSupplies() {
        return ResponseEntity.ok(supplyService.getAll());
    }

    @GetMapping("/all-sort-date-desc")
    public ResponseEntity<List<Supply>> getAllSuppliesSortDateDesc() {
        return ResponseEntity.ok(supplyService.getAllSortByDateDesc());
    }

    @PostMapping("/add")
    @Transactional
    public ResponseEntity<Supply> createSupply(@RequestBody final Supply supply)
            throws URISyntaxException {

        return ResponseEntity
                .created(new URI(MAIN_PATH))
                .body(supplyService.create(supply));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSupply(@PathVariable final Long id) throws Exception {

        supplyService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Supply> getSupplyById(@PathVariable("id") Long id
    ) throws Exception {

        return ResponseEntity.ok(supplyService.getById(id));
    }

    @PutMapping("/update")
    public ResponseEntity<Supply> updateSupply(@RequestBody final Supply supply
    ) throws Exception {

        return ResponseEntity.created(new URI(MAIN_PATH))
                .body(supplyService.update(supply));
    }

    @GetMapping("/max-id/")
    public ResponseEntity<Long> getSupplyById() {

        return ResponseEntity.ok(supplyService.findMaxId());
    }

    // TODO  Разобраться с эксепшеном здесь и ниже
    @GetMapping("/get-items/{id}")
    public ResponseEntity<List<SupplyItem>> getSupplyItemsById(@PathVariable("id") Long id
    ) throws Exception {

        return ResponseEntity.ok(supplyItemService.getAllBySupply(id));
    }

    @DeleteMapping("/delete-items/{id}")
    public ResponseEntity<Void> deleteAllItems(@PathVariable final Long id) {
        supplyItemService.deleteAllItems(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/add-items")
    @Transactional
    public ResponseEntity<Void> addItems(@RequestBody final List<SupplyItem> items) {
        supplyItemService.addItems(items);
        return ResponseEntity.noContent().build();
    }


}
