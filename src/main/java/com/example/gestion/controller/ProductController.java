package com.example.gestion.controller;

import com.example.gestion.dto.ProductRequest;
import com.example.gestion.dto.ProductResponse;
import com.example.gestion.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // GET /api/products
    @GetMapping
    public List<ProductResponse> findAll() {
        return service.findAll();
    }

    // GET /api/products/{id}
    @GetMapping("/{id}")
    public ProductResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    // POST /api/products
    @PostMapping
    public ProductResponse create(
            @Valid @RequestBody ProductRequest request) {

        return service.create(request);
    }

    // PUT /api/products/{id}
    @PutMapping("/{id}")
    public ProductResponse update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {

        return service.update(id, request);
    }

    // DELETE /api/products/{id}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}