package com.stockAPI.controller;

import com.stockAPI.dto.request.ProductDTO;
import com.stockAPI.dto.response.MessageResponseDTO;

import com.stockAPI.entity.Product;
import com.stockAPI.exception.ProductNotFoundException;
import com.stockAPI.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StockController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO createProduct(@RequestBody @Valid ProductDTO productDTO){
        return productService.createProduct(productDTO);
    }

    @GetMapping()
    public List<ProductDTO> listAll(){
        return productService.listAll();
    }

    @GetMapping("/{id}")
    public ProductDTO findByID(@PathVariable Long id) throws ProductNotFoundException {
        return productService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteByID(@PathVariable Long id) throws ProductNotFoundException {
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    public MessageResponseDTO updateByID(@PathVariable Long id, @RequestBody @Valid ProductDTO productDTO) throws ProductNotFoundException {
        return productService.updateById(id,productDTO);
    }
}
