package ru.hogwarts.school.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;


@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        logger.info("Was invoked method createStudent");
        return studentRepository.save(student);
    }

    public Student getStudent(long id) {
        logger.info("Was invoked method getStudent: идентификатор {}", id);
        return studentRepository.findById(id).get();
    }

    public Collection<Student> getStudensByAge(int age) {
        logger.info("Was invoked method getStudensByAge: возраст {}", age);
        return studentRepository.findByAge(age);
    }

    public Student editStudent(Student student) {
        logger.info("Was invoked method editStudent");
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        logger.info("Was invoked method deleteStudent: идентификатор {}", id);
        studentRepository.deleteById(id);
    }

    public List<Student> findByAgeBetween(int minAge, int maxAge) {
        logger.info("Was invoked method findByAgeBetween: минимальный возраст {},максимальный возраст {}", minAge, maxAge);
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public int getStudentCount() {
        logger.info("Was invoked method getStudentCount");
        return studentRepository.getStudentCount();
    }

    public int getStudentAverageAge() {
        logger.info("Was invoked method getStudentAverageAge");
        return studentRepository.getStudentAverageAge();
    }

    public List<Student> getLastStudents(int count) {
        logger.info("Was invoked method getLastStudents: количество {}", count);
        return studentRepository.getLastStudents(count);
    }


}
