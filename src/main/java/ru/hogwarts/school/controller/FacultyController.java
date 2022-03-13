package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.services.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping({"{id}"}) // GET http:localhost:8080/faculty/1
    public ResponseEntity getFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.getFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping(params = "color")// GET http:localhost:8080/student?color=green
    public ResponseEntity<Collection<Faculty>> getFacultyByColor(@RequestParam(required = false) String color) {
        Collection<Faculty> facultyByColor = facultyService.getFacultyByColor(color);
        if (facultyByColor.size() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(facultyByColor);
    }

    @GetMapping(params = {"color", "name"})// GET http:localhost:8080/faculty?
    public ResponseEntity<Collection<Faculty>> getFaculty(@RequestParam(required = false) String color, @RequestParam(required = false) String name) {
        if ((color != null && !color.isBlank()) || name != null && !name.isBlank()) {
            return ResponseEntity.ok(facultyService.findByColorIgnoreCaseOrNameIgnoreCase(color, name));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping// POST http:localhost:8080/faculty
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @PutMapping// PUT http:localhost:8080/faculty
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyService.editFaculty(faculty);
        if (foundFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @DeleteMapping({"{id}"}) // DELETE http:localhost:8080/faculty/1
    public ResponseEntity deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }
}
