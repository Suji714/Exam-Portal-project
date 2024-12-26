package com.coforge.training.examportal;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.coforge.training.examportal.controller.UserController;
import com.coforge.training.examportal.model.AnswerRequest;
import com.coforge.training.examportal.model.User;
import com.coforge.training.examportal.model.UserScore;
import com.coforge.training.examportal.repository.UserRepository;
import com.coforge.training.examportal.service.UserScoreService;
import com.coforge.training.examportal.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
* Author      : Satyam.3.Singh
* Date        : 3:48:03 pm
* Time        : 3:48:03 pm
* Project     : user-service
*/

class UserServiceTest1 { 


    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;
    
    @Mock
    private UserScoreService userScoreService;


    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() throws Exception {
	        MockitoAnnotations.openMocks(this);
	        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	        objectMapper = new ObjectMapper();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testRegister() throws Exception {
		User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setMobile("1234567890");

        doNothing().when(userService).saveUser(user);

        mockMvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("test@example.com"));

        verify(userService, times(1)).saveUser(user);
	}
	
	@Test
    void testRegister_Exception() throws Exception {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setMobile("1234567890");

        doThrow(new Exception("Email already exists")).when(userService).saveUser(user);

        mockMvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Email already exists"));

        verify(userService, times(1)).saveUser(user);
    }

	@Test
	void testAuthenticateUser() throws Exception {
		User loginRequest = new User();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");
        user.setId(1L);

        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(user);
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(true);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login successful!");
        response.put("userId", user.getId());

        mockMvc.perform(post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login successful!"))
                .andExpect(jsonPath("$.userId").value(1L));

        verify(userRepository, times(1)).findByEmail(loginRequest.getEmail());
        verify(passwordEncoder, times(1)).matches(loginRequest.getPassword(), user.getPassword());
	}

	 @Test
	    void testAuthenticateUser_Failure() throws Exception {
	        User loginRequest = new User();
	        loginRequest.setEmail("test@example.com");
	        loginRequest.setPassword("password");

	        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(null);

	        mockMvc.perform(post("/api/user/login")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(loginRequest)))
	                .andExpect(status().isUnauthorized())
	                .andExpect(jsonPath("$.message").value("Invalid credentials!"));

	        verify(userRepository, times(1)).findByEmail(loginRequest.getEmail());
	    }
	 
	@Test
	void testSubmitExam() throws Exception {
		Long userId = 1L;
        String topic = "Java";
        List<AnswerRequest> answers = Arrays.asList(
                new AnswerRequest(1L, "A"),
                new AnswerRequest(2L, "B")
        );

        when(userService.calculateAndStoreScore(userId, topic, answers)).thenReturn(2);

        mockMvc.perform(post("/api/user/submit-exam/{topic}", topic)
                .param("userId", userId.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(answers)))
                .andExpect(status().isOk())
                .andExpect(content().string("Your score for Java is: 2"));

        verify(userService, times(1)).calculateAndStoreScore(userId, topic, answers);
    }

	@Test
	void testGetUserReports() throws Exception {
		 List<Object[]> mockReports = new ArrayList<>();
	        mockReports.add(new Object[]{1L, "John", "Doe", 85});
	        mockReports.add(new Object[]{2L, "Jane", "Smith", 90});

	        when(userService.getUserReports(null, null)).thenReturn(mockReports);

	        mockMvc.perform(get("/api/user/reports"))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.size()").value(2))
	                .andExpect(jsonPath("$[0][1]").value("John"))
	                .andExpect(jsonPath("$[1][1]").value("Jane"));

	        verify(userService, times(1)).getUserReports(null, null);
	    }


	@Test
	void testGetUserReport() throws Exception {
		 Long userId = 1L;
	        List<UserScore> scores = Arrays.asList(
	                new UserScore(1L, userId, "Java", 5),
	                new UserScore(2L, userId, "C++", 3)
	        );

	        when(userService.getUserScores(userId)).thenReturn(scores);

	        mockMvc.perform(get("/api/user/report/{userId}", userId))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.size()").value(2))
	                .andExpect(jsonPath("$[0].topic").value("Java"))
	                .andExpect(jsonPath("$[1].topic").value("C++"));

	        verify(userService, times(1)).getUserScores(userId);
	    }
	
	 @Test
	    void testDownloadUserScorePdf() throws Exception {
	        Long userId = 1L;

	        // Prepare the PDF content
	        ByteArrayInputStream bis = new ByteArrayInputStream("Sample PDF Content".getBytes());
	        
	        // Mock the service call
	        when(userScoreService.generateUserScorePdf(userId)).thenReturn(bis);

	        MvcResult result = mockMvc.perform(get("/api/user/score/{userId}/download", userId))
	                .andExpect(status().isOk())
	                .andExpect(content().contentType(MediaType.APPLICATION_PDF))
	                .andReturn();

	        // Verify the content-disposition header
	        String contentDisposition = result.getResponse().getHeader(HttpHeaders.CONTENT_DISPOSITION);
	        assertTrue(contentDisposition.contains("attachment; filename=UserScore.pdf"));

	        // Verify the service call
	        verify(userScoreService, times(1)).generateUserScorePdf(userId);
	    }

	}

