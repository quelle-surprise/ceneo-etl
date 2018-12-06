package com.etl.etl.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jdk.internal.instrumentation.TypeMapping;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.type.ClobType;
import org.hibernate.type.StringNVarcharType;

import javax.persistence.*;
import java.sql.Clob;

@Data
@Entity
@Table(name = "reviews")
@Getter @Setter
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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
