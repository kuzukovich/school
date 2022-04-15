package ru.hogwarts.school.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;


@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;
    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method createFaculty");
        return facultyRepository.save(faculty);
    }

    public Faculty getFaculty(long id) {
        logger.info("Was invoked method getFaculty:идентификатор {}", id);
        return facultyRepository.findById(id).get();
    }

    public Collection<Faculty> getFacultyByColor(String color) {
        logger.info("Was invoked method getFacultyByColor:цвет {}", color);
        return facultyRepository.findByColor(color);
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.info("Was invoked method editFaculty");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        logger.info("Was invoked method deleteFaculty: идентификатор {}", id);
        facultyRepository.deleteById(id);
    }

    public List<Faculty> findByColorIgnoreCaseOrNameIgnoreCase(String color, String name) {
        logger.info("Was invoked method findByColorIgnoreCaseOrNameIgnoreCase: цвет  {}, имя {}", color, name);
        return facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(color, name);
    }

    public String getLongestFacultyName() {
        logger.info("Was invoked method getLongestFacultyName");
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElse(null);
    }
}
