package com.example.simple.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.simple.model.Employee;

public interface Employeeinterface extends JpaRepository<Employee, Long>{
	List<Employee> findByPublished(boolean published);
	  List<Employee> findByTitleContaining(String title);
}
