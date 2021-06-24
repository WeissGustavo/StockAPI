package com.stockAPI.service;

import com.stockAPI.dto.request.ProductDTO;
import com.stockAPI.dto.response.MessageResponseDTO;
import com.stockAPI.entity.Product;
import com.stockAPI.exception.ProductNotFoundException;
import com.stockAPI.mapper.ProductMapper;
import com.stockAPI.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductService {
    private final ProductRepository productRepository;

    private final ProductMapper productMapper;


    public MessageResponseDTO createProduct(ProductDTO productDTO) {
        Product productToSave = productMapper.toModel(productDTO);
        Product savedProduct = productRepository.save(productToSave);
        return createMessageResponseDTO(savedProduct.getId(),"Created product successfully on ID ");
    }

    public List<ProductDTO> listAll() {
        List<Product> allProducts = productRepository.findAll();
        return allProducts.stream().map(productMapper::toDTO).collect(Collectors.toList());
    }

    public ProductDTO findById(Long id) throws ProductNotFoundException {
        Product product = VerifyIfExists(id);
        return productMapper.toDTO(product);
    }

    public void deleteById(Long id) throws ProductNotFoundException {
        VerifyIfExists(id);
        productRepository.deleteById(id);
    }

    public MessageResponseDTO updateById(Long id, ProductDTO productDTO) throws ProductNotFoundException {
        VerifyIfExists(id);
        Product productToUpdate = productMapper.toModel(productDTO);
        Product updatedProduct = productRepository.save(productToUpdate);
        return createMessageResponseDTO(updatedProduct.getId(),"Product updated on ID");
    }


    private Product VerifyIfExists(Long id) throws ProductNotFoundException {
       return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    private MessageResponseDTO createMessageResponseDTO(Long id,String s) {
       MessageResponseDTO message =  MessageResponseDTO.builder().message(s + id).build();
       return message;
    }


}
