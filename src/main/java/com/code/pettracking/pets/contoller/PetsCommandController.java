package com.code.pettracking.pets.contoller;


import com.code.pettracking.pets.models.dto.ErrorRespDto;
import com.code.pettracking.pets.models.dto.PetsRespDto;
import com.code.pettracking.pets.models.dto.PetsUpdateDto;
import com.code.pettracking.pets.service.PetsFinderService;
import com.code.pettracking.pets.service.PetsUpdateService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PetsCommandController {


    Logger logger = LoggerFactory.getLogger(PetsCommandController.class);

    @Autowired
    PetsFinderService petsFinderService;

    @Autowired
    PetsUpdateService petsUpdateService;

    @PutMapping("pets/{id}")
    @ApiOperation(value = "API to update zone of pet", response = PetsUpdateDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Pet data updated successfully",response = PetsRespDto.class),
            @ApiResponse(code = 400, message = "Pet data does not exists", response = ErrorRespDto.class),
            @ApiResponse(code = 500, message = "Internal server error", response =  ErrorRespDto.class)
    })
    public ResponseEntity<Object> updateInZoneForPet(@PathVariable("id") Integer petId, @RequestBody PetsUpdateDto petsUpdateDto){
        try {
            PetsRespDto existingPet = petsFinderService.findExistingPet(petId);

            if(existingPet == null){
                return new ResponseEntity<>(new ErrorRespDto("Pet does not exist"), HttpStatus.BAD_REQUEST);
            }
            petsUpdateDto.setPetId(petId);
            petsUpdateService.updateInZone(petsUpdateDto);
            existingPet.setInZone(petsUpdateDto.isInZone());
            logger.info("Pet zone was successfully successfully updated for pet id {}", petId);
            return new ResponseEntity<>(existingPet, HttpStatus.ACCEPTED);
        }
        catch (Exception e){
            logger.error("Failed to update pet because of {}", e.getMessage());
            return new ResponseEntity<>(new ErrorRespDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("lost-tracker/pets/{id}")
    @ApiOperation(value = "API to update loss of tracker of pet", response = PetsRespDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Pet lost tracker data updated successfully",response = PetsRespDto.class),
            @ApiResponse(code = 404, message = "Pet data does not exists", response = ErrorRespDto.class),
            @ApiResponse(code = 500, message = "Internal server error", response =  ErrorRespDto.class)
    })
    public ResponseEntity<Object> updateLostTrackerForPet(@PathVariable("id") Integer petId, @RequestBody PetsUpdateDto petsUpdateDto){
        try {
            PetsRespDto existingPet = petsFinderService.findExistingPet(petId);

            if(existingPet == null){
                return new ResponseEntity<>(new ErrorRespDto("Pet does not exist"), HttpStatus.NOT_FOUND);
            }

            if(!existingPet.getPetType().equalsIgnoreCase("Cat")){
                return new ResponseEntity<>(new ErrorRespDto("Operation not supported for given pet type"), HttpStatus.NOT_ACCEPTABLE);
            }
            petsUpdateDto.setPetId(petId);
            petsUpdateService.updatePetTracker(petsUpdateDto);
            logger.info("Pet lost tracker data successfully updated for  pet id {}", petId);
            return new ResponseEntity<>("Tracker loss data updated", HttpStatus.ACCEPTED);
        }
        catch (Exception e){
            logger.error("Failed to update pet because of {}", e.getMessage());
            return new ResponseEntity<>(new ErrorRespDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
