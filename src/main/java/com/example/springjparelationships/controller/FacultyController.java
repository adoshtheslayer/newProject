package com.example.springjparelationships.controller;

import com.example.springjparelationships.entity.Faculty;
import com.example.springjparelationships.entity.University;
import com.example.springjparelationships.payload.FacultyDto;
import com.example.springjparelationships.respository.FacultyRepository;
import com.example.springjparelationships.respository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/faculty")
public class FacultyController {

    @Autowired
    FacultyRepository facultyRepository;
    @Autowired
    UniversityRepository universityRepository;

    //  @RequestMapping(method = RequestMethod.POST)
    @PostMapping
    public String add(@RequestBody FacultyDto facultyDto) {

        boolean exists = facultyRepository.existsByNameAndUniversityId(facultyDto.getName(), facultyDto.getUniversityId());
        if (exists) {
            return "This university such faculty exist";
        }

        Faculty faculty = new Faculty();
        faculty.setName(facultyDto.getName());

        Optional<University> optionalUniversity = universityRepository.findById(facultyDto.getUniversityId());
        if (!optionalUniversity.isPresent()) {
            return "University not found";
        }
        University university = optionalUniversity.get();
        faculty.setUniversity(university);
        facultyRepository.save(faculty);
        return "Faculty added";
    }

    @GetMapping(value = "/byUniversityId/{id}")
    public List<Faculty> getFacultiesByUniversityId(@PathVariable Integer id) {
        List<Faculty> byUniversityId = facultyRepository.findAllByUniversityId(id);
        return byUniversityId;
    }


    @PutMapping(value = "/{id}")
    public String editFaculty(@PathVariable Integer id, @RequestBody FacultyDto facultyDto) {

        Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
        if (optionalFaculty.isPresent()) {
            Faculty faculty = optionalFaculty.get();
            faculty.setName(facultyDto.getName());
            Optional<University> optionalUniversity = universityRepository.findById(facultyDto.getUniversityId());
            if (!optionalFaculty.isPresent()) {
                return "University not found";
            }
            University university = optionalUniversity.get();
            faculty.setUniversity(university);
            return "Faculty edited";
        }
        return "Faculty not found";
    }

    @DeleteMapping(value = "/{id}")
    public String deleteFaculty(@PathVariable Integer id) {
        try {
            facultyRepository.findById(id);
            return "Faculty deleted";
        } catch (Exception e) {
            return "Faculty not found";
        }
    }
}
