package com.code.pettracking.pets.contoller;


import com.code.pettracking.pets.exceptions.RecordAlreadyExists;
import com.code.pettracking.pets.models.dto.ErrorRespDto;
import com.code.pettracking.pets.models.dto.PetCreationResponseDto;
import com.code.pettracking.pets.models.dto.PetsCreationDto;
import com.code.pettracking.pets.models.entities.Owners;
import com.code.pettracking.pets.service.OwnerService;
import com.code.pettracking.pets.service.PetsService;
import com.code.pettracking.pets.utils.PetTrackerTypeLookUp;
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
public class PetsController {

    Logger logger = LoggerFactory.getLogger(PetsController.class);
    @Autowired
    private PetsService petsService;

    @Autowired
    private OwnerService ownerService;

    @PostMapping("pets")
    @ApiOperation(value = "API to create new pet entity", response = PetCreationResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "",response = PetCreationResponseDto.class),
            @ApiResponse(code = 400, message = "Pet data already exists", response = ErrorRespDto.class),
            @ApiResponse(code = 500, message = "Internal server error", response =  ErrorRespDto.class)
    })
    public ResponseEntity<Object> createPetEntity(@RequestBody PetsCreationDto petsCreationDto){
        try {
            Owners existingOwner = ownerService.findOwner(petsCreationDto.getOwnerId());

            if(existingOwner == null){
                return new ResponseEntity<>(new ErrorRespDto("Owner does not exist"), HttpStatus.NOT_FOUND);
            }
            if(!PetTrackerTypeLookUp.validPetTrackerTypeAssociation(petsCreationDto.getPetType(), petsCreationDto.getTrackerType())){
                return new ResponseEntity<>(new ErrorRespDto("Invalid tracker type for pet"), HttpStatus.BAD_REQUEST);
            }
            PetCreationResponseDto petCreationResponseDto = petsService.save(petsCreationDto);
            logger.info("Pet was successfully added for  owner id {}", petsCreationDto.getOwnerId());
            return new ResponseEntity<>(petCreationResponseDto, HttpStatus.CREATED);
        }
        catch (RecordAlreadyExists e){
            return new ResponseEntity<>(new ErrorRespDto("Pet already exists"), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            logger.error("Failed to add pet because of {}", e.getMessage());
            return new ResponseEntity<>(new ErrorRespDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
