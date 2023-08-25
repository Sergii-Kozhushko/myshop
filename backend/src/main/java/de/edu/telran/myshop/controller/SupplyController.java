package de.edu.telran.myshop.controller;

import de.edu.telran.myshop.config.URILinks;
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
@RequestMapping(URILinks.SUPPLY_URI)
@RequiredArgsConstructor
public class SupplyController {
    private final SupplyServiceImpl supplyService;
    private final SupplyItemServiceImpl supplyItemService;


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
                .created(new URI(URILinks.SUPPLY_URI))
                .body(supplyService.create(supply));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSupply(@PathVariable final Long id) {

        supplyService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Supply> getSupplyById(@PathVariable("id") Long id
    ) {

        return ResponseEntity.ok(supplyService.getById(id));
    }

    @PutMapping("/update")
    public ResponseEntity<Supply> updateSupply(@RequestBody final Supply supply
    ) throws URISyntaxException {

        return ResponseEntity.created(new URI(URILinks.SUPPLY_URI))
                .body(supplyService.update(supply));
    }

    @GetMapping("/max-id/")
    public ResponseEntity<Long> getSupplyById() {

        return ResponseEntity.ok(supplyService.findMaxId());
    }


    @GetMapping("/get-items/{id}")
    public ResponseEntity<List<SupplyItem>> getSupplyItemsById(@PathVariable("id") Long id
    ) {

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
