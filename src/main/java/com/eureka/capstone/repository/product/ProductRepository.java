package com.eureka.capstone.repository.product;

import com.eureka.capstone.domain.product.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByStartDate(LocalDate date);

}
