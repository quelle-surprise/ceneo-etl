package com.etl.etl.repository;

import com.etl.etl.model.Items;
import org.springframework.data.repository.CrudRepository;

public interface ItemsRepository extends CrudRepository<Items, Long> {

}