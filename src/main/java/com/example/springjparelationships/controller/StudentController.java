package com.example.springjparelationships.controller;

import com.example.springjparelationships.entity.Address;
import com.example.springjparelationships.entity.Group;
import com.example.springjparelationships.entity.Student;
import com.example.springjparelationships.payload.StudentDto;
import com.example.springjparelationships.respository.AddressRepository;
import com.example.springjparelationships.respository.GroupRepository;
import com.example.springjparelationships.respository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/student")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    GroupRepository groupRepository;

    //READ -> MINISTRY
    @GetMapping
    public Page<Student> studentList(@RequestParam int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> studentPage = studentRepository.findAll(pageable);
        return studentPage;
    }

    //READ -> by university id
    @GetMapping(value = "/byUniversityId/{universityId}")
    public Page<Student> getStudentsByUniversityId(@PathVariable Integer universityId, @RequestParam int page){

        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> allByGroup_faculty_universityId =
                studentRepository.findAllByGroup_Faculty_UniversityId(universityId,pageable);
        return allByGroup_faculty_universityId;
    }

    //READ -> by faculty id
    @GetMapping(value = "/byFacultyId/{facultyId}")
    public List<Student> getStudentsByFacultyId(@PathVariable Integer facultyId){
        List<Student> allByGroup_facultyId = studentRepository.findAllByGroup_FacultyId(facultyId);
        return allByGroup_facultyId;
    }

    //READ -> by group id
    @GetMapping(value = "/byGroupId/{groupId}")
    public List<Student> getStudentsByGroupId(@PathVariable Integer groupId){
        List<Student> allByGroupId = studentRepository.findAllByGroupId(groupId);
        return allByGroupId;
    }

    //CREATE STUDENT
    @PostMapping
    public String addStudent(@RequestBody StudentDto studentDto){
        Student student = new Student();
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());

        Address address = new Address();
        address.setCity(studentDto.getCity());
        address.setDistrict(studentDto.getDistrict());
        address.setStreet(studentDto.getStreet());
        Address savedAddres = addressRepository.save(address);
        student.setAddress(savedAddres);

        Optional<Group> optionalGroup = groupRepository.findById(studentDto.getGroupId());
        if (!optionalGroup.isPresent()){
            return "Group not found";
        }
        student.setGroup(optionalGroup.get());

        studentRepository.save(student);
        return "Student added";
    }

    //DELETE STUDENT
    @DeleteMapping(value = "/delete/{id}")
    public String deleteStudent(@PathVariable Integer id){
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()){
            Student student = optionalStudent.get();
            studentRepository.delete(student);
            return "Student deleted!";
        }
        return "Student not found!";
    }

    //UPDATE STUDENT
    @PutMapping(value = "/edit/{id}")
    public String editStudent(@PathVariable Integer id, @RequestBody StudentDto studentDto){
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (!optionalStudent.isPresent()){
            return "Student not found";
        }
        Student student = optionalStudent.get();
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());

        Address address = student.getAddress();
        address.setCity(studentDto.getCity());
        address.setDistrict(studentDto.getDistrict());
        address.setStreet(studentDto.getStreet());
        Address savedAddress = addressRepository.save(address);

        student.setAddress(savedAddress);
        Group group = student.getGroup();
        group.setId(studentDto.getGroupId());

        Group savedGroup = groupRepository.save(group);
        student.setGroup(savedGroup);

        studentRepository.save(student);
        return "Student edited!";
    }
}
