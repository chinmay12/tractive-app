package com.code.pettracking.pets.models.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetsTypeStatsDto {
    private String petType;
    private long count;
}
