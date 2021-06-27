package com.stockAPI.builder;

import com.stockAPI.dto.request.ProductDTO;
import com.stockAPI.enums.ProductType;
import lombok.Builder;

@Builder
public class ProductDTOBuilder {
    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Tablet";

    @Builder.Default
    private String brand = "Samsung";

    @Builder.Default
    private int max = 50;

    @Builder.Default
    private int quantity = 10;

    @Builder.Default
    private Double price = 500.0;

    @Builder.Default
    private ProductType type = ProductType.ELETRONICO;

    public ProductDTO toProductDTO() {
        return new ProductDTO(id,name,brand,max,quantity,price,type);
    }
}
