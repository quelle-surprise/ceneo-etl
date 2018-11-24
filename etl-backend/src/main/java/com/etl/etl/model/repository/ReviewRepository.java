package com.etl.etl.model.repository;

import com.etl.etl.model.entities.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review,Long> {
    //TODO: Implement queries for Reviews
}
