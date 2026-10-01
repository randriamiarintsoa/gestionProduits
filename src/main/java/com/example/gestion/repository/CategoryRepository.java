package com.example.gestion.repository;

import com.example.gestion.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByIdAndProductsIsNotEmpty(Long id);
}