package com.eureka.capstone.exception.notfound;

public class ProductNotFoundException extends NotFoundException{
    public ProductNotFoundException() {
        super();
    }

    public ProductNotFoundException(long id) {
        super(String.format("Product with id %d not found!", id));
    }

    public ProductNotFoundException(String info) {
        super(info);
    }
}
