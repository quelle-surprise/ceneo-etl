package com.etl.etl.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etl.etl.model.entities.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
