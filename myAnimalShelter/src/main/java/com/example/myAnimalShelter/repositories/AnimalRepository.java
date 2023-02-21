package com.example.myAnimalShelter.repositories;

import org.springframework.data.repository.CrudRepository;
import com.example.myAnimalShelter.entities.Animal;
import java.util.ArrayList;

public interface AnimalRepository extends CrudRepository<Animal,Integer>{

    ArrayList<Animal> findAllByShelter(String username);
    ArrayList<Animal> findAllByCity(Animal.cities city);
    void deleteById(Integer id);
}
