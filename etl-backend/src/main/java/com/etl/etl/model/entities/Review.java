package com.etl.etl.model.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity
@Table(name = "reviews")
@Getter @Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private Integer productId;

    private String reviewContent;
    private String nameOfReviewer;
    private String reviewScore;

}
