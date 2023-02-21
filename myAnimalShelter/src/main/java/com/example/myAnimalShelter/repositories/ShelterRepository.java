package com.example.myAnimalShelter.repositories;

import org.springframework.data.repository.CrudRepository;
import com.example.myAnimalShelter.entities.Shelter;
import java.util.ArrayList;
import java.util.Optional;

public interface ShelterRepository extends CrudRepository<Shelter, Integer>{
    ArrayList<Shelter> findByName(String name);
    Optional<Shelter> findByUsername(String username);
    Boolean existsByUsername(String username);
}
