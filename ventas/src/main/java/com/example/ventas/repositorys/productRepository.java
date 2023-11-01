package com.example.ventas.repositorys;

import com.example.ventas.modelos.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface productRepository extends JpaRepository<Product, Long> {}
