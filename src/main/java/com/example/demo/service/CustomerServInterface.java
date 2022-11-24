package com.example.demo.service;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.demo.exception.UserAlreadyExistException;
import com.example.demo.model.Applicationdata;
import com.example.demo.model.Registeredusers;

public interface CustomerServInterface {

	public Registeredusers addUser(Registeredusers registeredusers) throws UserAlreadyExistException;
	
	public Applicationdata addAppData(Applicationdata applicationdata) throws UserAlreadyExistException;
	
	public List<Registeredusers> findAllUsers();	
	
	public Registeredusers findAUser(String username) throws UsernameNotFoundException;
}
