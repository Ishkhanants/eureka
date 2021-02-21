package com.eureka.capstone.service;

import com.eureka.capstone.domain.Product;

import java.time.LocalDate;
import java.util.List;

public interface ProductService {
    Product createProduct(Product product);

    Product getProductById(long id);

    Product getProductByStartDate(LocalDate date);

    void updateProduct(Product product);

    List<Product> getAllProducts();

    void deleteProductById(long id);
}
