package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.UserAlreadyExistException;
import com.example.demo.model.Applicationdata;
import com.example.demo.model.JwtRequest;
import com.example.demo.model.JwtResponse;
import com.example.demo.model.Registeredusers;
import com.example.demo.service.CustomeUserDetailsService;
import com.example.demo.util.JwtUtil;


//////Car loan service==for login register and customer basic details///////Using port 8090////

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ApiController {
	
	@Autowired
	CustomeUserDetailsService customeUserDetailsService;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	AuthenticationManager authenticationManager;


	
	@PostMapping("/token")
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception{
		
		System.out.println(jwtRequest);
		
		try {
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
			
		} catch (BadCredentialsException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception("Invalid Credentials");
		}
		
		UserDetails userDetails = this.customeUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
		
		String token = this.jwtUtil.generateToken(userDetails);
		System.out.println(token);
		
		return ResponseEntity.ok(new JwtResponse(token));
	}
	


@PostMapping("/addUser")
ResponseEntity<Registeredusers> addUser(@RequestBody Registeredusers registeredusers) throws UserAlreadyExistException{	
	return new ResponseEntity<Registeredusers>(customeUserDetailsService.addUser(registeredusers) , HttpStatus.CREATED);
	}
	
	
	@GetMapping("/allusers")
	public ResponseEntity<List<Registeredusers>> findAllusers(){
		List<Registeredusers> allusers=customeUserDetailsService.findAllUsers();
		return new ResponseEntity<List<Registeredusers>>(allusers, HttpStatus.OK);
	}
	
	
	@GetMapping("/allusers/{username}")
	public ResponseEntity<Registeredusers> findAuser(@PathVariable String username) throws UsernameNotFoundException{
	Registeredusers user=customeUserDetailsService.findAUser(username);
		return new ResponseEntity<Registeredusers>(user, HttpStatus.OK);
	}
	
////Same method available in CARCAPSTONE service 
	
//	@PostMapping("/appData")
//	ResponseEntity<Applicationdata> appUser(@RequestBody Applicationdata applicationdata) throws UserAlreadyExistException{
//		return new ResponseEntity<Applicationdata>(customeUserDetailsService.addAppData(applicationdata) , HttpStatus.CREATED);
//	}
//	
}
