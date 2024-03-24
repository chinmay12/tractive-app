package com.code.pettracking.pets.models.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetsTrackerStatsDto {
    private String trackerType;
    private long count;
}
