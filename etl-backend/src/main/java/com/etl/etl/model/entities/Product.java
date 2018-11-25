package com.etl.etl.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name="products")
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "product_id")
    private Integer productId;
    private String productName;
    private String lowestPrice;
    private String category;

    public Product(Integer productId, String productName, String lowestPrice, String category) {
        this.productId = productId;
        this.productName = productName;
        this.lowestPrice = lowestPrice;
        this.category = category;
    }

}
