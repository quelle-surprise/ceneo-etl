package com.etl.etl.model.entities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="products")
@JsonAutoDetect
@NoArgsConstructor
public class Product {


    @Id
    @Column
    private Integer productId;
    @Column
    private String productName;
    @Column
    private String lowestPrice;
    @Column
    private String category;

    public Product(Integer productId, String productName, String lowestPrice, String category) {
        this.productId = productId;
        this.productName = productName;
        this.lowestPrice = lowestPrice;
        this.category = category;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(String lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
