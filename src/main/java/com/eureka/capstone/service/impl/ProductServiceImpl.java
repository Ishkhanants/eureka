package com.eureka.capstone.service.impl;

import com.eureka.capstone.domain.Product;
import com.eureka.capstone.exception.notfound.ProductNotFoundException;
import com.eureka.capstone.repository.ProductRepository;
import com.eureka.capstone.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    @Override
    public Product createProduct(Product product) {
        return repository.save(product);
    }

    @Override
    public Product getProductById(long id) {
        return repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public Product getProductByStartDate(LocalDate date) {
        return repository.findByStartDate(date).orElseThrow(() -> new ProductNotFoundException(
                String.format("Product with such " + "date: %s is not found!", date)));
    }

    @Override
    public void updateProduct(Product product) {

    }

    @Override
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    @Override
    public void deleteProductById(long id) {
        repository.deleteById(id);
    }
}
