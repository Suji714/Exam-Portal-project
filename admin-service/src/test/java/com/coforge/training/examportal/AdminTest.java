package com.coforge.training.examportal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.coforge.training.examportal.controller.AdminController;
import com.coforge.training.examportal.service.AdminService;

import java.util.ArrayList;
import java.util.List;

class AdminTest {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        // Cleanup resources if needed
    }

    @Test
    void testAddQuestions() {
        MultipartFile file = new MockMultipartFile("file", "questions.json", "text/plain", "Sample content".getBytes());

        doNothing().when(adminService).addQuestions(any(MultipartFile.class));

        ResponseEntity<String> response = adminController.addQuestions(file);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Questions uploaded successfully!", response.getBody());

        verify(adminService, times(1)).addQuestions(any(MultipartFile.class));
    }

    @Test
    void testAddQuestionsError() {
        MultipartFile file = new MockMultipartFile("file", "questions.json", "text/plain", "Sample content".getBytes());

        doThrow(new RuntimeException("File upload error")).when(adminService).addQuestions(any(MultipartFile.class));

        ResponseEntity<String> response = adminController.addQuestions(file);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error: File upload error", response.getBody());

        verify(adminService, times(1)).addQuestions(any(MultipartFile.class));
    }

    @Test
    void testRemoveQuestions() {
        String topic = "Math";

        doNothing().when(adminService).removeQuestionsByTopic(topic);

        ResponseEntity<String> response = adminController.removeQuestions(topic);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Questions for topic '" + topic + "' removed successfully!", response.getBody());

        verify(adminService, times(1)).removeQuestionsByTopic(topic);
    }

    @Test
    void testViewReports() {
        List<Object[]> mockReports = new ArrayList<>();
        mockReports.add(new Object[]{1L, "John", "Doe", 85});
        mockReports.add(new Object[]{2L, "Jane", "Smith", 90});

        when(adminService.getUserReports(null, null)).thenReturn((ResponseEntity<List<Object[]>>) mockReports);

        List<Object[]> reports = (List<Object[]>) adminController.viewReports(null, null);

        assertEquals(2, reports.size());
        assertEquals("John", reports.get(0)[1]);
        assertEquals("Jane", reports.get(1)[1]);

        verify(adminService, times(1)).getUserReports(null, null);
    }
}
