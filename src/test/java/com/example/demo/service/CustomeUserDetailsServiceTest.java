package com.example.demo.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.exception.UserAlreadyExistException;
import com.example.demo.model.Applicationdata;
import com.example.demo.model.Registeredusers;
import com.example.demo.repository.ApplicationRepository;
import com.example.demo.repository.UserRepository;



@ExtendWith(MockitoExtension.class)
class CustomeUserDetailsServiceTest {

	@Mock
    private UserRepository userRepository;	
	
	@Mock
	private ApplicationRepository applicationRepository;
	
	 @InjectMocks
	    private CustomeUserDetailsService customeUserDetailsService;
	    private Registeredusers registeredusers, registeredusers1;
	    private Applicationdata applicationdata;
	    private List<Registeredusers> userList;
	    private Optional optional;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		registeredusers = new Registeredusers("yadav", "Anant", "anantyadav@gmail.com", "8285069191", "Veeru@123");
		registeredusers1 = new Registeredusers("yadav1", "Anant1", "anantyadav1@gmail.com", "8285069192", "Veeru@13");
        optional = Optional.of(registeredusers);
        applicationdata = new Applicationdata("yadav", "Anant", "anantyadav@gmail.com", "8285069191", "Veeru@123",
        		"data", "data1", "data2", "data3", "data4", "data5", "data5", "data5");
	}

	@AfterEach
	void tearDown() throws Exception {	
		registeredusers=null;
	}
	
	@Test
	void testFindAllUsers() {
		userRepository.save(registeredusers);
        //stubbing the mock to return specific data
        when(userRepository.findAll()).thenReturn(userList);
        List<Registeredusers> userList1 = customeUserDetailsService.findAllUsers();
        assertEquals(userList, userList1);
        verify(userRepository, times(1)).save(registeredusers);
        verify(userRepository, times(1)).findAll();	
	}

	
	@Test
	void testFindAUser() {
		 when(userRepository.findById((String) any())).thenReturn(Optional.of(registeredusers));
		 Registeredusers retrieveduser = customeUserDetailsService.findAUser(registeredusers.getUsername());
	        verify(userRepository, times(2)).findById((String) any());	
	       
	}


	@Test
	void testAddUser() throws UserAlreadyExistException {
		when(userRepository.save(any())).thenReturn(registeredusers);
        assertEquals(registeredusers, customeUserDetailsService.addUser(registeredusers));
        verify(userRepository, times(1)).save(any());
		
	}

	@Test
	void testAddAppData() throws UserAlreadyExistException {
		when(applicationRepository.save(any())).thenReturn(applicationdata);
        assertEquals(applicationdata, customeUserDetailsService.addAppData(applicationdata));
        verify(applicationRepository, times(1)).save(any());
		
	}

}
