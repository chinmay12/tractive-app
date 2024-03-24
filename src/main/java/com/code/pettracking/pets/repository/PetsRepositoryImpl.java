package com.code.pettracking.pets.repository;

import com.code.pettracking.pets.models.PetTypes;
import com.code.pettracking.pets.models.TrackerType;
import com.code.pettracking.pets.models.dto.PetsStatsDto;
import com.code.pettracking.pets.models.dto.PetsTrackerStatsDto;
import com.code.pettracking.pets.models.dto.PetsTypeStatsDto;
import com.code.pettracking.pets.models.entities.CatsTracker;
import com.code.pettracking.pets.models.entities.Pets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Repository
public class PetsRepositoryImpl implements PetsRepository {

    Logger logger = LoggerFactory.getLogger(PetsRepositoryImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Pets save(Pets pet) {

        String insert_query = "INSERT INTO pets(name, pet_type, tracker_type, owner_id, in_zone) VALUES(?,?,?,?,?)";

        String uuid =  UUID.randomUUID().toString().replace("-", "");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(insert_query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, pet.getName());
            ps.setString(2, pet.getPetType());
            ps.setString(3, pet.getTrackerType());
            ps.setInt(4, pet.getOwnerId());
            ps.setBoolean(5, pet.isInZone());
            return ps;
        }, keyHolder);
        Integer generatedPet = (Integer)keyHolder.getKey();
        if (generatedPet != null){
            pet.setId(generatedPet);
            pet.setName(pet.getName());
            return pet;
        }
        throw new RuntimeException("Failed to save pet");
    }

    @Override
    public Pets findPet(int petId){
        try {
            Pets pet = jdbcTemplate.queryForObject("SELECT * FROM pets WHERE id=?", BeanPropertyRowMapper.newInstance(Pets.class), petId);
            return pet;
        }
        catch (EmptyResultDataAccessException e){
            logger.warn("No pet found");
            return null;
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    public Pets findPet(int ownerId, String petName){
        try {
            Pets pet = jdbcTemplate.queryForObject("SELECT * FROM pets WHERE owner_id=? and name=?", BeanPropertyRowMapper.newInstance(Pets.class), ownerId, petName);
            return pet;
        }
        catch (EmptyResultDataAccessException e){
            logger.warn("No pet found");
            return null;
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<Pets> findPetsByTrackerType(TrackerType trackerType){
        try {
            List<Pets> pets = jdbcTemplate.query("SELECT * FROM pets WHERE tracker_type=?", BeanPropertyRowMapper.newInstance(Pets.class), trackerType.toString());
            return pets;
        }
        catch (EmptyResultDataAccessException e){
            logger.warn("No pets found");
            return null;
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<Pets> findPetsByPetType(PetTypes petType){
        try {
            List<Pets> pets = jdbcTemplate.query("SELECT * FROM pets WHERE pet_type=?", BeanPropertyRowMapper.newInstance(Pets.class), petType.toString());
            return pets;
        }
        catch (EmptyResultDataAccessException e){
            logger.warn("No pets found");
            return null;
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    public int updatePetZone(Pets pet){
        return jdbcTemplate.update(
                "update pets set in_zone = ? where id = ?", pet.isInZone(), pet.getId()
        );
    }

    @Override
    public int updatePetTracker(CatsTracker catsTracker){

        return jdbcTemplate.update(
                "update pets set in_zone = ? where  id = ?", false, catsTracker.getCatId()
        );
    }

    @Override
    public List<PetsTrackerStatsDto> countOutofZonePetByTracker(){
        try {
            List<PetsTrackerStatsDto> petsTypeStatsDtos = jdbcTemplate.query("SELECT tracker_type, count(id) as count FROM pets where in_zone = false GROUP BY tracker_type", new RowMapper() {
                public PetsTrackerStatsDto mapRow(ResultSet result, int rowNum) throws SQLException {
                    PetsTrackerStatsDto petsTrackerStatsDto = new PetsTrackerStatsDto();
                    petsTrackerStatsDto.setTrackerType(result.getString("tracker_type"));
                    petsTrackerStatsDto.setCount(result.getLong(2));
                    return petsTrackerStatsDto;
                }
            });
            return petsTypeStatsDtos;
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<PetsTypeStatsDto> countOutOfZonePetByPetType(){
        try {
            List<PetsTypeStatsDto> petsTypeStatsDtos = jdbcTemplate.query("SELECT pet_type, count(1) FROM pets where in_zone = false GROUP BY pet_type",
                    new RowMapper() {
                        public PetsTypeStatsDto mapRow(ResultSet result, int rowNum) throws SQLException {
                            PetsTypeStatsDto petsTypeStatsDto = new PetsTypeStatsDto();
                            petsTypeStatsDto.setPetType(result.getString("pet_type"));
                            petsTypeStatsDto.setCount(result.getLong(2));
                            return petsTypeStatsDto;
                        }
                    });
            return petsTypeStatsDtos;
        }
        catch (Exception e){
            throw e;
        }
    }

}
