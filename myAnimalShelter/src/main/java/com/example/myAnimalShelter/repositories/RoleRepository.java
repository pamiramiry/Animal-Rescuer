package com.example.myAnimalShelter.repositories;

import com.example.myAnimalShelter.entities.Roles;
import com.example.myAnimalShelter.entities.Shelter;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Roles, Integer> {
    Optional<Roles> findByName(String name);
}
