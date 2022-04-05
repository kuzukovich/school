package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(int age);

    List<Student> findByAgeBetween(int minAge, int maxAge);

    @Query(value = "select count(*) from student", nativeQuery = true)
    int getStudentCount();

    @Query(value = "select avg(age) from student", nativeQuery = true)
    int getStudentAverageAge();

    @Query(value = "select * from student order by id  DESC limit ?1", nativeQuery = true)
    List<Student> getLastStudents(int count);

}
