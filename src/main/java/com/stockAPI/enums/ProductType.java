package com.stockAPI.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductType {

    ELETRONICO("Eletronico"),
    ROUPA("Roupa"),
    COMIDA("Comida"),
    BEBIDA("Bebida"),
    BRINQUEDO("Brinquedo"),
    ELETRODOMESTICO("Eletrodomestico"),
    MOVEL("Movel");

    private final String type;
}
