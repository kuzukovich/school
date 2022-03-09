package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.services.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("student")
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping({"{id}"}) // GET http:localhost:8080/student/1
    public ResponseEntity getStudent(@PathVariable Long id) {
        Student student = studentService.getStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping // GET http:localhost:8080/student?age=18
    public ResponseEntity<Collection<Student>> getStudensByAge(@RequestParam("age") int age) {
        Collection<Student> studensByAge = studentService.getStudensByAge(age);
        if (studensByAge.size() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(studensByAge);
    }

    @PostMapping// POST http:localhost:8080/student
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping// PUT http:localhost:8080/student
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundStudent = studentService.editStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping({"{id}"}) // DELETE http:localhost:8080/student/1
    public Student deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }

}
