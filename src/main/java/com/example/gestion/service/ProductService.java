package com.example.gestion.service;

import com.example.gestion.dto.ProductRequest;
import com.example.gestion.dto.ProductResponse;
import com.example.gestion.entity.Category;
import com.example.gestion.entity.Product;
import com.example.gestion.exception.ProductNotFoundException;
import com.example.gestion.repository.CategoryRepository;
import com.example.gestion.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;

    public ProductService(
            ProductRepository repository,
            CategoryRepository categoryRepository) {

        this.repository = repository;
        this.categoryRepository = categoryRepository;
    }

    // =========================
    // LISTE DES PRODUITS
    // =========================

    public List<ProductResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // =========================
    // PAGINATION + TRI
    // =========================

    public Page<ProductResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(this::toResponse);
    }

    // =========================
    // RECHERCHE PAR NOM
    // =========================

    public List<ProductResponse> searchByName(String name) {
        return repository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // =========================
    // RECHERCHE + PAGINATION
    // =========================

    public Page<ProductResponse> searchByName(
            String name,
            Pageable pageable) {

        return repository
                .findByNameContainingIgnoreCase(name, pageable)
                .map(this::toResponse);
    }

    // =========================
    // RECHERCHE PAR ID
    // =========================

    public ProductResponse findById(Long id) {

        Product product = repository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Produit introuvable"
                        )
                );

        return toResponse(product);
    }

    // =========================
    // CREATION
    // =========================

    public ProductResponse create(ProductRequest request) {

        Product product = new Product();

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        if (request.getCategoryId() != null) {

            Category category = categoryRepository
                    .findById(request.getCategoryId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Catégorie introuvable"
                            )
                    );

            product.setCategory(category);
        }

        Product savedProduct = repository.save(product);

        return toResponse(savedProduct);
    }

    // =========================
    // MODIFICATION
    // =========================

    public ProductResponse update(
            Long id,
            ProductRequest request) {

        Product product = repository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Produit introuvable"
                        )
                );

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        if (request.getCategoryId() != null) {

            Category category = categoryRepository
                    .findById(request.getCategoryId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Catégorie introuvable"
                            )
                    );

            product.setCategory(category);

        } else {

            product.setCategory(null);
        }

        Product updatedProduct = repository.save(product);

        return toResponse(updatedProduct);
    }

    // =========================
    // SUPPRESSION
    // =========================

    public void delete(Long id) {

        Product product = repository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Produit introuvable"
                        )
                );

        repository.delete(product);
    }

    // =========================
    // CONVERSION ENTITY → DTO
    // =========================

    private ProductResponse toResponse(Product product) {

        ProductResponse response = new ProductResponse();

        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());

        if (product.getCategory() != null) {

            response.setCategoryId(
                    product.getCategory().getId()
            );

            response.setCategoryName(
                    product.getCategory().getName()
            );
        }

        return response;
    }
}