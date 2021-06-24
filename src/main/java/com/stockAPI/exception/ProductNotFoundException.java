package com.stockAPI.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends Throwable {
    public ProductNotFoundException(Long id){
        super("Product not found on ID" + id);
    }
}