package com.example.gestion.service;

import com.example.gestion.dto.CategoryRequest;
import com.example.gestion.dto.CategoryResponse;
import com.example.gestion.entity.Category;
import com.example.gestion.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<CategoryResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public CategoryResponse create(CategoryRequest request) {

        Category category = new Category();
        category.setName(request.getName());

        Category savedCategory = repository.save(category);

        return toResponse(savedCategory);
    }

    private CategoryResponse toResponse(Category category) {

        CategoryResponse response = new CategoryResponse();

        response.setId(category.getId());
        response.setName(category.getName());

        return response;
    }
}