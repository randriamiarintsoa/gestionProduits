package com.example.gestion.service;

import com.example.gestion.dto.CategoryRequest;
import com.example.gestion.dto.CategoryResponse;
import com.example.gestion.entity.Category;
import com.example.gestion.exception.CategoryNotFoundException;
import com.example.gestion.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import com.example.gestion.exception.CategoryUsedException;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    // =========================
    // LISTE
    // =========================

    public List<CategoryResponse> findAll() {

        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // =========================
    // RECHERCHE PAR ID
    // =========================

    public CategoryResponse findById(Long id) {

        Category category = repository.findById(id)
                .orElseThrow(() ->
                        new CategoryNotFoundException(
                                "Catégorie introuvable"
                        )
                );

        return toResponse(category);
    }

    // =========================
    // CREATION
    // =========================

    public CategoryResponse create(CategoryRequest request) {

        Category category = new Category();

        category.setName(request.getName());

        Category savedCategory = repository.save(category);

        return toResponse(savedCategory);
    }

    // =========================
    // MODIFICATION
    // =========================

    public CategoryResponse update(
            Long id,
            CategoryRequest request) {

        Category category = repository.findById(id)
                .orElseThrow(() ->
                        new CategoryNotFoundException(
                                "Catégorie introuvable"
                        )
                );

        category.setName(request.getName());

        Category updatedCategory = repository.save(category);

        return toResponse(updatedCategory);
    }

    // =========================
    // SUPPRESSION
    // =========================

  public void delete(Long id) {

    Category category = repository.findById(id)
            .orElseThrow(() ->
                    new CategoryNotFoundException(
                            "Catégorie introuvable"
                    )
            );

    if (!category.getProducts().isEmpty()) {
        throw new CategoryUsedException(
                "Impossible de supprimer la catégorie car elle est utilisée par des produits"
        );
    }

    repository.delete(category);
}

    // =========================
    // ENTITY → RESPONSE
    // =========================

    private CategoryResponse toResponse(Category category) {

        CategoryResponse response = new CategoryResponse();

        response.setId(category.getId());
        response.setName(category.getName());

        return response;
    }
}