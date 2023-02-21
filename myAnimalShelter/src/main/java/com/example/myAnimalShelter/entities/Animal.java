package com.example.myAnimalShelter.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;


@Entity
@Table(name="ANIMAL")
public class Animal {
    public enum typeofanimal{DOG,CAT,OTHER};
    public enum genderofanimal{MALE, FEMALE};

    public enum cities{TORONTO, MONTREAL, VANCOUVER, CALGARY, OTTAWA, EDMONTON, WINNIPEG, QUEBEC_CITY, HALIFAX, LONDON, WINDSOR, WATERLOO};
    @Id
    @GeneratedValue
    Integer id;
    @Column(name = "AGE")
    Integer age;
    @Column(name="GENDER")
    genderofanimal gender;
    @Column(name="SPECIES")
    String species;
    @Column(name="SHELTER")
    String shelter;
    @Column(name="TYPE")
    typeofanimal type;
    @Column(name="CITY")
    cities city;

    public cities getCity() {
        return city;
    }
    public void setCity(cities city) {
        this.city = city;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id){
        this.id=id;
    }
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public genderofanimal getGender() {
        return gender;
    }

    public void setGender(genderofanimal gender) {
        this.gender = gender;
    }

    public String getShelter() {
        return shelter;
    }

    public void setShelter(String shelter) {
        this.shelter = shelter;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public typeofanimal getType() {
        return type;
    }

    public void setType(typeofanimal type) {
        this.type = type;
    }
}
