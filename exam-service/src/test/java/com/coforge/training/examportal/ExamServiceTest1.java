package com.coforge.training.examportal;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.coforge.training.examportal.controller.ExamController;
import com.coforge.training.examportal.model.ExamQuestion;
import com.coforge.training.examportal.service.ExamService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ExamController.class)
public class ExamServiceTest1 {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExamService examService;

    @InjectMocks
    private ExamController examController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(examController).build();
    }

    @Test
    public void testUploadQuestions() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "questions.json", "application/json", "{\"questions\":[]}".getBytes());

        doNothing().when(examService).saveQuestionsFromJson(file);

        mockMvc.perform(multipart("/api/exam/add-questions")
                .file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(content().string("Questions uploaded successfully!"));
    }

    @Test
    public void testGetQuestionsByTopic() throws Exception {
    	String topic = "Java";
        ExamQuestion question1 = new ExamQuestion();
        question1.setQuestion("What is Java?");
        question1.setCorrectAnswer("Java is a programming language.");

        ExamQuestion question2 = new ExamQuestion();
        question2.setQuestion("What is JVM?");
        question2.setCorrectAnswer("JVM stands for Java Virtual Machine.");

        List<ExamQuestion> questions = Arrays.asList(question1, question2);

        when(examService.getQuestionsByTopic(topic)).thenReturn(questions);

        mockMvc.perform(get("/api/exam/questions/{topic}", topic)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].question").value("What is Java?"))
                .andExpect(jsonPath("$[0].correctAnswer").value("Java is a programming language."))
                .andExpect(jsonPath("$[1].question").value("What is JVM?"))
                .andExpect(jsonPath("$[1].correctAnswer").value("JVM stands for Java Virtual Machine."));
    }

    @Test
    public void testRemoveQuestionsByTopic() throws Exception {
        String topic = "Java";

        doNothing().when(examService).removeQuestionsByTopic(topic);

        mockMvc.perform(delete("/api/exam/remove-questions/{topic}", topic)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Questions for topic '" + topic + "' removed successfully!"));
    }
}
