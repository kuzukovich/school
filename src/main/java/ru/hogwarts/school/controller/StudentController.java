package ru.hogwarts.school.controller;

import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.services.StudentService;


import java.util.Collection;
import java.util.List;

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

    @GetMapping(params = "age")// GET http:localhost:8080/student?age=18
    public ResponseEntity<Collection<Student>> getStudensByAge(@RequestParam(required = false) int age) {
        Collection<Student> studensByAge = studentService.getStudensByAge(age);
        if (studensByAge.size() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(studensByAge);
    }

    @GetMapping(params = {"minAge", "maxAge"})// GET http:localhost:8080/student?age=18
    public ResponseEntity<Collection<Student>> getStudensByAgeBetween(@RequestParam(required = false) int minAge, @RequestParam(required = false) int maxAge) {
        Collection<Student> studensByAgeBetween = studentService.findByAgeBetween(minAge, maxAge);
        if (studensByAgeBetween.size() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(studensByAgeBetween);
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
    public ResponseEntity deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/count")
    public int getStudentCount() {
        return studentService.getStudentCount();
    }

    @GetMapping("/averageAge")
    public int getStudentAverageAge() {
        return studentService.getStudentAverageAge();
    }

    @GetMapping("/last/{count}")
    public List<Student> getLastStudents(@PathVariable int count) {
        return studentService.getLastStudents(count);
    }

    @GetMapping("/startWithA")
    public List<String> getStudentNameWithA() {
        return studentService.getStudentNameWithA();
    }

    @GetMapping("/parallel-thread")
    public ResponseEntity<Void> getStudentsFlow() {
        studentService.getStudentsFlow();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/sync-thread")
    public ResponseEntity<Void> getStudentsFlowSynchronized() {
        studentService.getStudentsFlowSynchronized();
        return ResponseEntity.ok().build();
    }
}
