package com.devgen.shop.spring_boot.model;

import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Product {
    private long id;
    private String name;
    private Category category;
    private double price;
}
