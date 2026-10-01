package com.example.gestion.controller;

import com.example.gestion.dto.CategoryRequest;
import com.example.gestion.dto.CategoryResponse;
import com.example.gestion.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<CategoryResponse> findAll() {
        return service.findAll();
    }

    @PostMapping
    public CategoryResponse create(
            @Valid @RequestBody CategoryRequest request) {

        return service.create(request);
    }
}