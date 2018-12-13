package com.etl.etl.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;
    @Column
    private String reviewContent;
    @Column
    private String nameOfReviewer;
    @Column
    private String reviewScore;

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", reviewContent='" + reviewContent + '\'' +
                ", nameOfReviewer='" + nameOfReviewer + '\'' +
                ", reviewScore='" + reviewScore + '\'' +
                '}';
    }
}
