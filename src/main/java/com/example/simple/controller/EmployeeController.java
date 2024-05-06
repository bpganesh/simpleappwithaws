package com.example.simple.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.simple.model.Employee;
import com.example.simple.repository.Employeeinterface;

@RestController
public class EmployeeController {
	@Autowired
	Employeeinterface tutorialRepository;
	@GetMapping("/tutorials")
	  public ResponseEntity<List<Employee>> getAllTutorials(@RequestParam(required = false) String title) {
	    try {
	      List<Employee> tutorials = new ArrayList<Employee>();

	      if (title == null)
	        tutorialRepository.findAll().forEach(tutorials::add);
	      else
	        tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);

	      if (tutorials.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }

	      return new ResponseEntity<>(tutorials, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }

	  @GetMapping("/tutorials/{id}")
	  public ResponseEntity<Employee> getTutorialById(@PathVariable("id") long id) {
	    Optional<Employee> tutorialData = tutorialRepository.findById(id);

	    if (tutorialData.isPresent()) {
	      return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }

	  @PostMapping("/tutorials")
	  public ResponseEntity<Employee> createTutorial(@RequestBody Employee tutorial) {
	    try {
	      Employee _tutorial = tutorialRepository
	          .save(new Employee(tutorial.getTitle(), tutorial.getDescription(), false));
	      return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }

	  @PutMapping("/tutorials/{id}")
	  public ResponseEntity<Employee> updateTutorial(@PathVariable("id") long id, @RequestBody Employee tutorial) {
	    Optional<Employee> tutorialData = tutorialRepository.findById(id);

	    if (tutorialData.isPresent()) {
	      Employee _tutorial = tutorialData.get();
	      _tutorial.setTitle(tutorial.getTitle());
	      _tutorial.setDescription(tutorial.getDescription());
	      _tutorial.setPublished(tutorial.isPublished());
	      return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }

	  @DeleteMapping("/tutorials/{id}")
	  public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
	    try {
	      tutorialRepository.deleteById(id);
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }

//	  @DeleteMapping("/tutorials")
//	  public ResponseEntity<HttpStatus> deleteAllTutorials() {
//	    try {
//	      tutorialRepository.deleteAll();
//	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//	    } catch (Exception e) {
//	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//
//	  }

	  @GetMapping("/tutorials/published")
	  public ResponseEntity<List<Employee>> findByPublished() {
	    try {
	      List<Employee> tutorials = tutorialRepository.findByPublished(true);

	      if (tutorials.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }
	      return new ResponseEntity<>(tutorials, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }

	

}
