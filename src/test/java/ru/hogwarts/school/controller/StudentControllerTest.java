package ru.hogwarts.school.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.assertj.core.api.Assertions.*;

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
        Student student_18 = new Student("studentName", 18);
        Student student_25 = new Student("studentName", 25);
        Student student_28 = new Student("studentName", 28);
        Student student_32 = new Student("studentName", 32);
        restTemplate.postForEntity("http://localhost:" + port + "/student/", student_18, Student.class);
        restTemplate.postForEntity("http://localhost:" + port + "/student/", student_25, Student.class);
        restTemplate.postForEntity("http://localhost:" + port + "/student/", student_28, Student.class);
        restTemplate.postForEntity("http://localhost:" + port + "/student/", student_32, Student.class);
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("minAge", "20");
        queryParams.add("maxAge", "30");
        foundByCriteria(queryParams, student_25, student_28);
    }

    private UriComponentsBuilder getUriBuilder() {
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path("/hogwarts/students");
    }

    private void foundByCriteria(MultiValueMap<String, String> queryParams, Student... students) {
        URI uri = getUriBuilder().queryParams(queryParams).build().toUri();
    }

}