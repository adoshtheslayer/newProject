package com.example.springjparelationships.controller;

import com.example.springjparelationships.entity.Subject;
import com.example.springjparelationships.respository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/subject")
public class SubjectController {

    @Autowired
    SubjectRepository subjectRepository;

    //READ ONCE
    @RequestMapping(value = "/subject/{id}", method = RequestMethod.GET)
    public Subject getSubject(@PathVariable Integer id) {
        Optional<Subject> subject = subjectRepository.findById(id);
        if (subject.isPresent()) {
            return subject.get();
        }
        return null;
    }

    //READ ALL
  //  @RequestMapping(value = "/subject", method = RequestMethod.GET)
    @GetMapping
    public List<Subject> getSubjects() {
        return subjectRepository.findAll();
    }

    //DELETE
    @RequestMapping(value = "/subject/{id}", method = RequestMethod.DELETE)
    public String deleteSubject(@PathVariable Integer id) {
        Optional<Subject> subject = subjectRepository.findById(id);
        if (subject.isPresent()) {
            subjectRepository.delete(subject.get());
            return "Subject deleted";
        }
        return "Subject not fount";
    }

    //CREATE
    @RequestMapping(method = RequestMethod.POST)
    public String addSubject(@RequestBody Subject subject) {
        boolean existsByName = subjectRepository.existsByName(subject.getName());
        if (existsByName){
            return "This subject already exist";
        }
        subjectRepository.save(subject);
        return "Subject added";
    }

    //UPDATE
    @RequestMapping(value = "/subject/{id}", method = RequestMethod.PUT)
    public String editSubject(@PathVariable Integer id, @RequestBody Subject subject) {

        Optional<Subject> subject1 = subjectRepository.findById(id);
        if (subject1.isPresent()) {
            if (subject.getName() != null) {
                subject1.get().setName(subject.getName());
                subjectRepository.save(subject1.get());
                return "Subject edited";
            }
        }
        return "Subject not found";
    }
}
