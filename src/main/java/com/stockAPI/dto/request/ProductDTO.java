package com.stockAPI.dto.request;

import com.stockAPI.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long id;

    @NotEmpty
    @Size(min = 2, max = 40)
    private String name;

    @NotEmpty
    @Size(min = 2, max = 40)
    private String brand;

    @NotNull
    private int max;

    @NotNull
    private int quantity;

    @NotNull
    private double price;

    @Valid
    @Enumerated(EnumType.STRING)
    private ProductType type;

}
