package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.exception.UserAlreadyExistException;
import com.example.demo.model.Applicationdata;
import com.example.demo.model.Registeredusers;
import com.example.demo.repository.ApplicationRepository;
import com.example.demo.repository.UserRepository;

@Service
public class CustomeUserDetailsService implements UserDetailsService, CustomerServInterface {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ApplicationRepository appRepo;
	
	
	@SuppressWarnings("deprecation")
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		//getting user by id from Mysql db
		Registeredusers user = userRepository.getById(userName);
		//getting username Mysql db
		String usernamedb = user.getUsername();
		//getting password from Mysql db
		String pwdb=user.getPassword();
		if (userName.equals(usernamedb)) {
			return new User(usernamedb, pwdb, new ArrayList<>());
		}
		
		else {
			throw new UsernameNotFoundException("User not found");
		}
		
	}

	@Override
	public List<Registeredusers> findAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public Registeredusers findAUser(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<Registeredusers> registeredusers2 = userRepository.findById(username);
		if(!registeredusers2.isPresent()) {
			throw new UsernameNotFoundException("No User exists with the details provided");
		}
		
		return userRepository.findById(username).get();
	}
	
	

	public Registeredusers addUser(Registeredusers registeredusers) throws UserAlreadyExistException {
		
		Optional<Registeredusers> registeredusers2 = userRepository.findById(registeredusers.getUsername());
		if(registeredusers2.isPresent()) {
			throw new UserAlreadyExistException("Username Already Taken");
		}
		
		else return userRepository.save(registeredusers);
	}
	
	
	@Override
	public Applicationdata addAppData(Applicationdata applicationdata) throws UserAlreadyExistException {
		// TODO Auto-generated method stub
		return appRepo.save(applicationdata);
	}
	
}
