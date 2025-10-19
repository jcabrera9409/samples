package com.devskiller.dao;

import com.devskiller.model.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Long> {

    @Query("SELECT i FROM Item i LEFT JOIN i.reviews r GROUP BY i.id " +
            "HAVING COALESCE(AVG(r.rating), 0) < :rating")
    List<Item> findItemsWithAverageRatingLowerThan(Double rating);

}