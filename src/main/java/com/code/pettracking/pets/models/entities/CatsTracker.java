package com.code.pettracking.pets.models.entities;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CatsTracker {
    private int catId;
    private boolean lostTracker;
}
