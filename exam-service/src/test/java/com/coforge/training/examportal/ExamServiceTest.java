package com.coforge.training.examportal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import com.coforge.training.examportal.model.ExamQuestion;
import com.coforge.training.examportal.repository.ExamQuestionRepository;
import com.coforge.training.examportal.service.ExamService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class ExamServiceTest {

    @Mock
    private ExamQuestionRepository examQuestionRepository;

    @Mock
    private MultipartFile file;

    @InjectMocks
    private ExamService examService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testSaveQuestionsFromJson() throws Exception {
        ExamQuestion question1 = new ExamQuestion();
        question1.setTopic("Java");
        question1.setQuestion("What is Java?");
        question1.setCorrectAnswer("Java is a programming language.");

        List<ExamQuestion> questions = Arrays.asList(question1);

        byte[] jsonBytes = objectMapper.writeValueAsBytes(questions);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(jsonBytes);

        when(file.getInputStream()).thenReturn(inputStream);
        when(examQuestionRepository.findMaxQuestionIdByTopic("Java")).thenReturn(0L);

        examService.saveQuestionsFromJson(file);
        
        question1.setQuestionId(1L);

        verify(examQuestionRepository, times(1)).save(question1);
    }

    @Test
    public void testGetQuestionsByTopic() {
        String topic = "Java";
        ExamQuestion question1 = new ExamQuestion();
        question1.setTopic(topic);
        question1.setQuestion("What is Java?");
        question1.setCorrectAnswer("Java is a programming language.");

        List<ExamQuestion> questions = Arrays.asList(question1);

        when(examQuestionRepository.findByTopic(topic)).thenReturn(questions);

        List<ExamQuestion> result = examService.getQuestionsByTopic(topic);

        assertEquals(questions, result);
    }

    @Test
    public void testRemoveQuestionsByTopic() {
        String topic = "Java";
        ExamQuestion question1 = new ExamQuestion();
        question1.setTopic(topic);
        question1.setQuestion("What is Java?");
        question1.setCorrectAnswer("Java is a programming language.");

        List<ExamQuestion> questions = Arrays.asList(question1);

        when(examQuestionRepository.findByTopic(topic)).thenReturn(questions);

        examService.removeQuestionsByTopic(topic);

        verify(examQuestionRepository, times(1)).deleteAll(questions);
    }

    @Test
    public void testRemoveQuestionsByTopic_NoQuestionsFound() {
        String topic = "Java"; 

        when(examQuestionRepository.findByTopic(topic)).thenReturn(Arrays.asList());

        assertThrows(RuntimeException.class, () -> {
            examService.removeQuestionsByTopic(topic);
        });
    }
}
