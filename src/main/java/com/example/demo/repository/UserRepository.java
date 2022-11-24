package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Registeredusers;


public interface UserRepository extends JpaRepository<Registeredusers, String> {

}
