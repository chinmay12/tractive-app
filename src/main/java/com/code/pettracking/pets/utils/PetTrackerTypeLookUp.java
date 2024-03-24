package com.code.pettracking.pets.utils;

import com.code.pettracking.pets.models.TrackerType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PetTrackerTypeLookUp {
    static Map<String, Set<String>>  catTrackerTypeMap = Map.of("Cat", new HashSet<>(Arrays.asList(TrackerType.BIG.toString(), TrackerType.MEDIUM.toString(), TrackerType.SMALL.toString())));
    static Map<String, Set<String>> dogTrackerTypeMap = Map.of("Dog", new HashSet<>(Arrays.asList(TrackerType.BIG.toString(), TrackerType.SMALL.toString())));

    public static boolean validPetTrackerTypeAssociation(String petType, String trackerType){
        if(petType.equalsIgnoreCase("Cat")){
            return catTrackerTypeMap.get(petType).contains(trackerType.toString());
        }
        else if(petType.equalsIgnoreCase("Dog")){
            return dogTrackerTypeMap.get(petType).contains(trackerType.toString());
        }
        return false;
    }
}
