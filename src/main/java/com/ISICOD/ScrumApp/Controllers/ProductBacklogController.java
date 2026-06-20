package com.ISICOD.ScrumApp.Controllers;

import com.ISICOD.ScrumApp.Entities.ProductBacklog;
import com.ISICOD.ScrumApp.Services.ProductBacklogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product-backlogs")
@RequiredArgsConstructor
public class ProductBacklogController {

    private final ProductBacklogService productBacklogService;

    @PostMapping
    public ResponseEntity<ProductBacklog> createProductBacklog(
            @RequestBody ProductBacklog productBacklog) {

        return ResponseEntity.ok(
                productBacklogService.createProductBacklog(productBacklog)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductBacklogById(
            @PathVariable Integer id) {

        return productBacklogService.getProductBacklogById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet( () ->{
                    Map<String ,Object> error = new HashMap<>();
                    error.put("message" , "not found");
                    error.put("id" , id);

                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
                });
    }

    @GetMapping
    public ResponseEntity<List<ProductBacklog>> getAllProductBacklogs() {

        return ResponseEntity.ok(
                productBacklogService.getAllProductBacklogs()
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductBacklog> updateProductBacklog(
            @PathVariable Integer id,
            @RequestBody ProductBacklog productBacklog) {

        return ResponseEntity.ok(
                productBacklogService.updateProductBacklog(id, productBacklog)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductBacklog(
            @PathVariable Integer id) {

        productBacklogService.deleteProductBacklog(id);

        return ResponseEntity.noContent().build();
    }
}