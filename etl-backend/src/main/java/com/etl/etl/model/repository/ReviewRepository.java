package com.etl.etl.model.repository;

import com.etl.etl.model.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    //TODO: Implement queries for Reviews
}
