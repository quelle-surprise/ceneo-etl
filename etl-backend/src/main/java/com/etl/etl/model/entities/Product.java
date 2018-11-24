package com.etl.etl.model.entities;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;

@Data
@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "product_id")
    private Integer productId;

    //TODO: Think about additional fields which could be scrapped and next displayed in application
    private String name;
    private Double price;
    private String category;
    private String basicInformations;

    public Product(Integer id, Integer productId, String name, Double price, String category, String basicInformations) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.category = category;
        this.basicInformations = basicInformations;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBasicInformations() {
        return basicInformations;
    }

    public void setBasicInformations(String basicInformations) {
        this.basicInformations = basicInformations;
    }
}
