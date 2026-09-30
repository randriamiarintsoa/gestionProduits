package com.example.gestion.service;

import com.example.gestion.entity.Product;
import com.example.gestion.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Product findById(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Produit introuvable"
                        )
                );
    }

    public Product create(Product product) {

        if (product.getPrice() < 0) {
            throw new IllegalArgumentException(
                    "Le prix ne peut pas être négatif"
            );
        }

        if (product.getStock() < 0) {
            throw new IllegalArgumentException(
                    "Le stock ne peut pas être négatif"
            );
        }

        return repository.save(product);
    }

    public Product update(
            Long id,
            Product product) {

        Product existing = findById(id);

        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setPrice(product.getPrice());
        existing.setStock(product.getStock());

        return repository.save(existing);
    }

    public void delete(Long id) {

        Product product = findById(id);

        repository.delete(product);
    }
}