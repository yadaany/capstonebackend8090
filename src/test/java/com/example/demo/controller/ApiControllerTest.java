package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.example.demo.model.Registeredusers;
import com.example.demo.service.CustomeUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;


@ExtendWith(MockitoExtension.class)
class ApiControllerTest {

	   private MockMvc mockMvc;
	    @Mock
	    CustomeUserDetailsService customeUserDetailsService;
	    @InjectMocks
	    private ApiController apiController;

	    private Registeredusers registeredusers;
//	    private List<Blog> blogList;
	    private String message;
	
	@BeforeEach
	void setUp() throws Exception {
		registeredusers= new Registeredusers();
		mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
		registeredusers.setUsername("Veeru123");
		registeredusers.setName("Anant");
		registeredusers.setEmailid("anant@gmail.com");
		registeredusers.setMobile("82659874135");
		registeredusers.setPassword("Veeru@123");
		 message= "Hello Anant";
	}

	@AfterEach
	void tearDown() throws Exception {
		registeredusers=null;
	}


	@Test
	void testAddUser() throws Exception {
	        when(customeUserDetailsService.addUser(any())).thenReturn(registeredusers);
	        mockMvc.perform(post("/addUser")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(asJsonString(registeredusers)))
	                .andExpect(status().isCreated())
	                .andDo(MockMvcResultHandlers.print());
	        verify(customeUserDetailsService).addUser(any());
	   }
	
	
	 public static String asJsonString(final Object obj) {
	        try {
	            return new ObjectMapper().writeValueAsString(obj);
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	    }

}
