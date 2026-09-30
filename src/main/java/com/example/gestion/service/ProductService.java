package com.example.gestion.service;

import com.example.gestion.dto.ProductRequest;
import com.example.gestion.dto.ProductResponse;
import com.example.gestion.entity.Product;
import com.example.gestion.exception.ProductNotFoundException;
import com.example.gestion.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<ProductResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ProductResponse findById(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Produit introuvable"));

        return toResponse(product);
    }

    public ProductResponse create(ProductRequest request) {

        Product product = new Product();

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        Product savedProduct = repository.save(product);

        return toResponse(savedProduct);
    }

    public ProductResponse update(Long id, ProductRequest request) {

        Product product = repository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Produit introuvable"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        Product updatedProduct = repository.save(product);

        return toResponse(updatedProduct);
    }

    public void delete(Long id) {

        Product product = repository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Produit introuvable"));

        repository.delete(product);
    }

    private ProductResponse toResponse(Product product) {

        ProductResponse response = new ProductResponse();

        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());

        return response;
    }
    public List<ProductResponse> searchByName(String name) {

    return repository.findByNameContainingIgnoreCase(name)
            .stream()
            .map(this::toResponse)
            .toList();
}
}