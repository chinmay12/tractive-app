package com.code.pettracking.pets.repository;

import com.code.pettracking.pets.models.entities.CatsTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CatsTrackerRepositoryImpl implements  CatsTrackerRepository{


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int update(CatsTracker catsTracker) {
        return jdbcTemplate.update(
                "update cats_tracker set lost_tracker = ? where  pet_id = ?", catsTracker.isLostTracker(), catsTracker.getCatId()
        );
    }
}
