package com.example.myAnimalShelter;

import com.example.myAnimalShelter.entities.Roles;
import com.example.myAnimalShelter.entities.Shelter;
import com.example.myAnimalShelter.repositories.ShelterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomShelterDetailsService implements UserDetailsService {
    private ShelterRepository shelterRepository;
    @Autowired
    public CustomShelterDetailsService(ShelterRepository shelterRepository){
        this.shelterRepository=shelterRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Shelter shelterEntity = this.shelterRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("UserName not found!!!"));

        return new User(shelterEntity.getUsername(), shelterEntity.getPassword(), mapRolesToAuthorities(shelterEntity.getRoles()));
    }
    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Roles> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
