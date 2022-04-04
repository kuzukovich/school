package ru.hogwarts.school.controller;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import net.minidev.json.JSONObject;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.services.FacultyService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static ru.hogwarts.school.constants.Constants.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = FacultyController.class)
class FacultyControllerTest {
    private Faculty faculty;
    private Collection<Faculty> facultyList;
    private JSONObject facultyObject;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyService facultyService;

    @InjectMocks
    private FacultyController facultyController;

    @BeforeEach
    void initDataForTest() throws JSONException {
        faculty = new Faculty();
        faculty.setId(FACULTY_ID);
        faculty.setName(FACULTY_NAME);
        faculty.setColor(FACULTY_COLOR);
        Faculty faculty1 = new Faculty();
        faculty1.setId(FACULTY_ID);
        faculty1.setName(FACULTY_NAME);
        faculty1.setColor(FACULTY_COLOR);
        Faculty faculty2 = new Faculty();
        faculty2.setId(FACULTY_ID);
        faculty2.setName(FACULTY_NAME);
        faculty2.setColor(FACULTY_COLOR);
        facultyList = List.of(
                faculty,
                faculty1,
                faculty2
        );
        facultyObject = new JSONObject();
        facultyObject.put("name", FACULTY_NAME);
        facultyObject.put("color", FACULTY_COLOR);

    }

    @Test
    void getFacultyByIdTest() throws Exception {
        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(faculty));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + FACULTY_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(FACULTY_ID))
                .andExpect(jsonPath("$.name").value(FACULTY_NAME))
                .andExpect(jsonPath("$.color").value(FACULTY_COLOR));
    }

    @Test
    void getFacultyTest() throws Exception {
        when(facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(anyString(), anyString())).thenReturn((List<Faculty>) facultyList);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/?color=" + FACULTY_COLOR + "&name=" + FACULTY_NAME)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]").exists())
                .andExpect(jsonPath("$.[*].id").isNotEmpty())
                .andExpect(jsonPath("$.[*].name").isNotEmpty())
                .andExpect(jsonPath("$.[*].color").isNotEmpty());
    }

    @Test
    void getFacultyByColorTest() throws Exception {
        when(facultyRepository.findByColor(FACULTY_COLOR)).thenReturn((List<Faculty>) facultyList);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/?color=" + FACULTY_COLOR)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*]").exists())
                .andExpect(jsonPath("$.[*].id").isNotEmpty());
    }

    @Test
    void createFacultyTest() throws Exception {
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(FACULTY_ID))
                .andExpect(jsonPath("$.name").value(FACULTY_NAME))
                .andExpect(jsonPath("$.color").value(FACULTY_COLOR));
    }

    @Test
    void editFacultyTest() throws Exception {
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(FACULTY_ID))
                .andExpect(jsonPath("$.name").value(FACULTY_NAME))
                .andExpect(jsonPath("$.color").value(FACULTY_COLOR));
    }

    @Test
    void deleteFacultyTest() throws Exception {
        doNothing().when(facultyRepository).deleteById(anyLong());
        mockMvc.perform(MockMvcRequestBuilders.delete("/faculty/" + FACULTY_ID)).andExpect(status().isOk());
    }

}