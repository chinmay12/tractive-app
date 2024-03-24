package com.code.pettracking.pets.repository;

import com.code.pettracking.pets.models.entities.Owners;
import com.code.pettracking.pets.models.entities.Pets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OwnerRepositoryImpl implements OwnerRepository{

    Logger logger = LoggerFactory.getLogger(OwnerRepositoryImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Owners findOwner(int ownerId) {
        try {
            Owners owner = jdbcTemplate.queryForObject("SELECT * FROM owners WHERE id=?", BeanPropertyRowMapper.newInstance(Owners.class), ownerId);
            return owner;
        }
        catch (EmptyResultDataAccessException e){
            logger.warn("No owner found");
            return null;
        }
        catch (Exception e){
            throw e;
        }
    }
}
