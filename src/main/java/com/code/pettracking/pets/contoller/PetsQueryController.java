package com.code.pettracking.pets.contoller;

import com.code.pettracking.pets.models.PetTypes;
import com.code.pettracking.pets.models.dto.*;
import com.code.pettracking.pets.service.PetsFinderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PetsQueryController {

    Logger logger = LoggerFactory.getLogger(PetsQueryController.class);

    @Autowired
    private PetsFinderService petsFinderService;

    @GetMapping("pets/{id}")
    @ApiOperation(value = "API to fetch given pet details",
            notes = "Provide pet id",  response = PetsRespDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "",response = PetsRespDto.class),
            @ApiResponse(code = 404, message = "Pet data does not exist", response = ErrorRespDto.class),
            @ApiResponse(code = 500, message = "Internal server error", response =  ErrorRespDto.class)
    })
    public ResponseEntity<Object> fetchPet(@PathVariable("id") Integer petId){
        try {

            PetsRespDto existingPet = petsFinderService.findExistingPet(petId);
            if(existingPet == null){
                return new ResponseEntity<>("Pet not found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(existingPet, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ErrorRespDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("pets/type/{petType}")
    @ApiOperation(value = "API to fetch pets of particular type",
            notes = "Provide the type of pet",  response = PetsRespDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "",response = PetsRespDto.class),
            @ApiResponse(code = 500, message = "Internal server error", response =  ErrorRespDto.class)
    })
    public ResponseEntity<Object> fetPetsByType(@PathVariable("petType") String petType){
        try {

            List<PetsRespDto> pets = petsFinderService.findPetsByType(PetTypes.valueOf(petType));
            return new ResponseEntity<>(pets, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ErrorRespDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("out-of-zone/pets")
    @ApiOperation(value = "API to fetch number of pets currently outside the power saving zone grouped by pet type\n" +
            "and tracker type",
            notes = "Provide query parameter groupBy value of either by pet-type or tracker-type",  response = PetsStatsDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "",response = PetsStatsDto.class),
            @ApiResponse(code = 400, message = "Group query param is required",response = PetsStatsDto.class),
            @ApiResponse(code = 500, message = "Internal server error", response =  ErrorRespDto.class)
    })
    public ResponseEntity<Object> fetchPetsOutSideZone(@RequestParam("groupBy") String groupBy){
        try {
            PetsStatsDto petsStatsDto = new PetsStatsDto();
            if(Strings.isBlank(groupBy)){
                return new ResponseEntity<>(new ErrorRespDto("Group query param is required"), HttpStatus.BAD_REQUEST);
            }
            if(groupBy.equals("tracker-type")){
                List<PetsTrackerStatsDto> petsTrackerStatsDtos = petsFinderService.countPetsByTracker();
                petsStatsDto.setPetsTrackerStatsDtoList(petsTrackerStatsDtos);
            }
            else if(groupBy.equals("pet-type")){
                List<PetsTypeStatsDto> petsTypeStatsDtos = petsFinderService.countPetsByType();
                petsStatsDto.setPetsTypeStatsDtoList(petsTypeStatsDtos);
            }
            else{
                return new ResponseEntity<>(new ErrorRespDto("Invalid value for group by query param"), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(petsStatsDto, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ErrorRespDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
