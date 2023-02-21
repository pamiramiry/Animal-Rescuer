package com.example.myAnimalShelter.entities;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import com.example.myAnimalShelter.entities.Animal.cities;
@Entity
@Table(name="SHELTER")
public class Shelter {

    @Id
    @GeneratedValue
    Integer id;
    @Column(name="ADDRESS")
    String address;
    @Column(name="USERNAME")
    String username;
    @Column(name="PASSWORD")
    String password;
    @Column(name="NAME")
    String name;
    @Column(name="CITY")
    cities city;
    //@Column(name="COUNTRY")
    //String country;
    @Column(name="HOURS")
    String hours;
    @Column(name="NUMBEROFANIMALS")
    Integer numberofanimals;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "shelter_roles", joinColumns = @JoinColumn(name="shelter_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="roles_id", referencedColumnName = "id"))
    private List<Roles> roles = new ArrayList<>();
    public Integer getId(){
        return this.id;
    }
    public void setId(Integer id){
        this.id=id;
    }
    public String getAddress(){
        return this.address;
    }
    public void setAddress(String address){
        this.address=address;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name=name;
    }
    public cities getCity(){
        return this.city;
    }
    public void setCity(cities city){
        this.city=city;
    }
    //public String getCountry(){
    //    return this.country;
    //}
    //public void setCountry(String country){
    //    this.country=country;
    //}
    public String getHours(){
        return this.hours;
    }
    public void setHours(String hours){
        this.hours=hours;
    }
    public int getNumberofanimals(){
        return this.numberofanimals;
    }
    public void setNumberofanimals(int numberofanimals){
        this.numberofanimals=numberofanimals;
    }
    public String getUsername(){
        return this.username;
    }
    public void setUsername(String username){
        this.username=username;
    }
    public String getPassword(){
        return this.password;
    }
    public void setPassword(String password){
        this.password=password;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }
}
