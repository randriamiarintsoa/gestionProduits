package com.example.gestion.controller;

import com.example.gestion.dto.ProductRequest;
import com.example.gestion.dto.ProductResponse;
import com.example.gestion.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // =========================
    // RECHERCHE
    // =========================

    @GetMapping("/search")
    public List<ProductResponse> search(@RequestParam String name) {
        return service.searchByName(name);
    }

    // =========================
    // PAGINATION + TRI
    // =========================

    @GetMapping("/page")
    public Page<ProductResponse> findAllPaginated(Pageable pageable) {
        return service.findAll(pageable);
    }

    // =========================
    // CRUD
    // =========================

    @GetMapping
    public List<ProductResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ProductResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ProductResponse create(
            @Valid @RequestBody ProductRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public ProductResponse update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
    @GetMapping("/search/page")
    public Page<ProductResponse> searchPaginated(
            @RequestParam String name,
            Pageable pageable) {

        return service.searchByName(name, pageable);
    }
}