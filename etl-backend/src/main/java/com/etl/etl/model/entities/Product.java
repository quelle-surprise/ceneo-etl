package com.etl.etl.model.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    private Integer productId;
    private String name;
    private Double price;
    private String category;
    private String basicInformations;
}
