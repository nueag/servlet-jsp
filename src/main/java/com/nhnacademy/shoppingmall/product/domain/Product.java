package com.nhnacademy.shoppingmall.product.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Product {

    private int productId;
    private String productName;
    private double price;
    private int stock;
    private String productImage;
    private String productInfo;
    private LocalDateTime registerDate;
    private LocalDateTime latestUpdateAt;

    public Product() {

    }

}
