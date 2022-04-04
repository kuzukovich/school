package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    @LocalServerPort
    private int port;
    private Student student;
    private Student student2;
    private Student student3;
    private long id;
    private Collection<Student> studentList;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void initTestData() {
        student = new Student();
        student.setId(1L);
        student.setName("studentName");
        student.setAge(20);
        student2 = new Student();
        student2.setId(1L);
        student2.setName("studentName2");
        student2.setAge(30);
        student3 = new Student();
        student3.setId(1L);
        student3.setName("studentName3");
        student3.setAge(40);
        studentList = List.of(
                student,
                student2,
                student3
        );
    }


    @Test
    void contextLoads() {
        assertThat(studentController).isNotNull();
    }

    @Test
    void createStudentTest() {
        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student/", student, String.class))
                .isNotNull();
        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student/", student, Student.class))
                .isEqualTo(student);
    }

    @Test
    void getStudensByAgeTest() {
        student.setAge(80);
        id = restTemplate.postForObject("http://localhost:" + port + "/student/", student, Student.class).getId();
        student.setId(id);
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/" + "?age=" + 80, Collection.class))
                .isNotNull();
    }

    @Test
    void getStudentByIdTest() {
        id = restTemplate.postForObject("http://localhost:" + port + "/student/", student, Student.class).getId();
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/" + id, Student.class))
                .isEqualTo(student);
    }

    @Test
    void editStudentTest() {
        student.setAge(80);
        id = restTemplate.postForObject("http://localhost:" + port + "/student/", student, Student.class).getId();
        student.setId(id);
        restTemplate.put("http://localhost:" + port + "/student/", student);
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/" + id, Student.class))
                .isEqualTo(student);
    }


    @Test
    void deleteStudentTest() {
        id = restTemplate.postForObject("http://localhost:" + port + "/student/", student, Student.class).getId();
        student.setId(id);
        restTemplate.delete("http://localhost:" + port + "/student/" + id);
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/" + id, Student.class))
                .isNotIn(student);
    }

    @Test
    void findByAgeBetween() {
        id = restTemplate.postForObject("http://localhost:" + port + "/student/", student, Student.class).getId();
        id = restTemplate.postForObject("http://localhost:" + port + "/student/", student2, Student.class).getId();
        id = restTemplate.postForObject("http://localhost:" + port + "/student/", student2, Student.class).getId();
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/" + "?ageMin=" + 5 + "&ageMax=" + 60, ArrayList.class).size())
                .isGreaterThan(3);
    }

}