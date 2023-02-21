package com.example.myAnimalShelter;

import com.example.myAnimalShelter.entities.Animal;
import com.example.myAnimalShelter.entities.Shelter;
import com.example.myAnimalShelter.repositories.AnimalRepository;
import com.example.myAnimalShelter.repositories.RoleRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.myAnimalShelter.repositories.ShelterRepository;
import org.springframework.ui.Model;

import com.example.myAnimalShelter.entities.Animal.cities;

import java.util.ArrayList;

@SpringBootApplication
//@RestController
@Controller
public class MyAnimalShelterApplication {

	private final AnimalRepository animalRepository;
	private final ShelterRepository shelterRepository;
	private AuthenticationManager authenticationManager;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	////////////////////////
	public MyAnimalShelterApplication(ShelterRepository shelterRepository, AuthenticationManager authenticationManager, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AnimalRepository animalRepository){
		this.shelterRepository=shelterRepository;
		this.authenticationManager=authenticationManager;
		this.roleRepository=roleRepository;
		this.passwordEncoder=passwordEncoder;
		this.animalRepository=animalRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(MyAnimalShelterApplication.class, args);
	}

	//Returns the Home Page where User can Login, Register etc.
	@GetMapping("/")
	public String hello(){
		return "index";
	}


	// This takes in a Shelter and adds it to the database if Username has not been created before
	// If User is created the client is notified
	@PostMapping("auth/register")
	//ResponseEntity<String>
	public String register(@ModelAttribute("shelter") Shelter shelter, Model model){
		Boolean created=Boolean.TRUE;
		if(this.shelterRepository.existsByUsername(shelter.getUsername())){
			//return new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
			created = Boolean.FALSE;
			return "index";
		}
		shelter.setPassword(this.passwordEncoder.encode(shelter.getPassword()));
		this.shelterRepository.save(shelter);

		model.addAttribute("created", created);
		//return new ResponseEntity<>("User was created successfully", HttpStatus.OK);
		return "index";
	}

	//Deals with registrations
	//Returns Html registration form
	@GetMapping("auth/register")
	public String registerForm(Model model){
		Shelter shelter = new Shelter();
		model.addAttribute("shelter", shelter);
		return "bstest";
	}

	// Client after logging decides to add animal
	// Returns the animal registration form
	@GetMapping("auth/login/user/addanimal")
	public String testanimal(Model model){
		Animal animal = new Animal();
		model.addAttribute("animal", animal);
		return "animalRegistration";
	}

	//Receives the results from the form and creates the animal object
	// Gets the User that is currently logged in and uses its details to fill in the rest of the animal object
	//@ResponseBody
	@PostMapping("auth/login/user/addanimal")
	public String testpostanimal(@ModelAttribute("animal") Animal animal, Model model){
		String shelterUserName = SecurityContextHolder.getContext().getAuthentication().getName();
		Shelter shelter = this.shelterRepository.findByUsername(shelterUserName).orElseThrow(()-> new UsernameNotFoundException("Error Something went wrong"));
		animal.setShelter(shelter.getUsername());
		animal.setCity(shelter.getCity());
		//animal.setCity(shelter.getCity());
		this.animalRepository.save(animal);
		model.addAttribute("created", Boolean.TRUE);
		//return animal.getGender()== Animal.genderofanimal.MALE ? "Male"+animal.getShelter(): "Female"+animal.getShelter();
		return "shelterHomePage";
	}


	@ResponseBody
	@PostMapping("auth/login")
	public ResponseEntity<String> loginPortal(@RequestBody Shelter shelter){
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(shelter.getUsername(), shelter.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponseEntity<>("User is logged in", HttpStatus.OK);
		//return ResponseEntity.status(HttpStatus.OK).location(URI.create("http://localhost:8080/auth/login/user")).build();
	}

	//@ResponseBody
	//This is the Shelters home page
	// You will be able to view all the animals that this shelter has
	// Also can add and delete animals
	@GetMapping("auth/login/user")
	public String shelterHomePage(Model model){
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("name", "Welcome "+a.getName());
		ArrayList<Animal> list= this.animalRepository.findAllByShelter(a.getName());
		model.addAttribute("list", list);
		Animal animaltest = new Animal();
		model.addAttribute("animal", animaltest);
		//model.addAttribute("list", this.animalRepository.findAll());
		return "shelterHomePage";
	}
	// Shows all animals that the shelter has
	// Client chooses which one to delete
	@GetMapping("auth/login/user/deleteanimal")
	public String deleteAnimal(Model model){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("name", "Welcome "+authentication.getName());
		ArrayList<Animal> list= this.animalRepository.findAllByShelter(authentication.getName());
		model.addAttribute("list", list);
		Animal animal123 = new Animal();
		model.addAttribute("animal",animal123);
		return "delete";
	}
	//Delete the chosen animal
	// Send a Notification when Animal has been deleted
	@PostMapping("auth/login/user/deleteanimal")
	public String deleteanimalagain(@ModelAttribute("animal") Animal animal, Model model){
		Integer id =animal.getId();
		//this.animalRepository.deleteById(animal.getId());
		String success = "Animal with Id "+ id +" has been removed";
		model.addAttribute("message", success);
		model.addAttribute("animal", animal);
		model.addAttribute("deleted", Boolean.TRUE);

		//'Id: '+ ${animal.id} + ' Age: ' + ${animal.age} + ' Gender: ' + ${animal.gender} + ' Species: ' +${animal.species}
		this.animalRepository.deleteById(id);

		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		ArrayList<Animal> list= this.animalRepository.findAllByShelter(a.getName());
		model.addAttribute("list", list);
		return "shelterHomePage";
	}

	//Shows all the animals on the app
	//Gives an option to filter by city
	@GetMapping("/search")
	public String searchAnimals(Model model){
		Iterable<Animal> list = this.animalRepository.findAll();
		model.addAttribute("list", list);
		Animal animaltest = new Animal();
		model.addAttribute("animal", animaltest);
		//model.addAttribute("list", this.animalRepository.findAll());
		return "search";
	}

	// Filter out all the Animals based on the city sent by the client
	@PostMapping("search")
	public String searchAnimals(@ModelAttribute("city") cities c, Model model){
		ArrayList<Animal> list = this.animalRepository.findAllByCity(c);
		model.addAttribute("list", list);
		Animal animaltest = new Animal();
		model.addAttribute("animal", animaltest);
		//model.addAttribute("list", this.animalRepository.findAll());
		return "search";
	}
	/////////////////////////////////////////////////////////////////////////////
	//////////////These methods below arent used just for testing and checking

	//This wads just for testing backend functionality
	@PostMapping("/signup")
	public String signUp(@RequestBody Shelter shelter){
		if(shelterExist(shelter.getName()))
			return "Shelter already exist cannot make account!!";
		return this.shelterRepository.save(shelter).getName();
		//return newshelter;
	}
	//This wads just for testing backend functionality
	@ResponseBody
	@GetMapping("/allshelters")
	public Iterable<Shelter> getAllShelters(){
		return this.shelterRepository.findAll();
	}

	//This was just for testing backend functionality
	@ResponseBody
	@GetMapping("/login")
	public String login(){
		return "login system in works";
	}
	//This wads just for testing backend functionality
	public Boolean shelterExist(String name) {
		ArrayList<Shelter> totalnames= this.shelterRepository.findByName(name);
		return totalnames.size()>0? true:false;
	}
	// This is to make sure the alert is working
	@GetMapping("test/popups")
	public String lazy(Model model){
		Boolean created = Boolean.TRUE;
		model.addAttribute("created", created);
		return "index";
	}
}
