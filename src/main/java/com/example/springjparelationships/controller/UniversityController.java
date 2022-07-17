package com.example.springjparelationships.controller;

import com.example.springjparelationships.entity.Address;
import com.example.springjparelationships.entity.University;
import com.example.springjparelationships.payload.UniversityDto;
import com.example.springjparelationships.respository.AddressRepository;
import com.example.springjparelationships.respository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UniversityController {

    @Autowired
    UniversityRepository universityRepository;
    @Autowired
    AddressRepository addressRepository;


    //READ ALL
    @RequestMapping(value = "/university", method = RequestMethod.GET)
    public List<University> getUniversities() {
        return universityRepository.findAll();
    }

    //READ ONCE
    @RequestMapping(value = "/university/{id}", method = RequestMethod.GET)
    public University getUniversity(@PathVariable Integer id) {
        Optional<University> university = universityRepository.findById(id);
        if (university.isPresent()) {
            return university.get();
        }
        return null;
    }


    //CREATE
    @RequestMapping(value = "/university", method = RequestMethod.POST)
    public String addUniversity(@RequestBody UniversityDto universityDto) {

        //YANGI ADDRESS OCHIB OLDIK
        Address address = new Address();
        address.setCity(universityDto.getCity());
        address.setDistrict(universityDto.getDistrict());
        address.setStreet(universityDto.getStreet());

        //YASAB OLGAN ADDRESS OBJECTNI DBGA SAQLADIK VA U BIZGA SAQLANGAN ADDRESSNI BERDI
        Address savedAddress = addressRepository.save(address);

        //YANGI UNIVERSITY YASAB OLDIK
        University university = new University();
        university.setName(universityDto.getName());
        university.setAddress(savedAddress);
        universityRepository.save(university);

        return "University added";
    }

    //DELETE
    @RequestMapping(value = "/university/{id}", method = RequestMethod.DELETE)
    public String deleteUniversity(@PathVariable Integer id) {

        Optional<University> university = universityRepository.findById(id);
        if (university.isPresent()) {
            universityRepository.delete(university.get());
            return "Unversity deleted";
        }
        return "University not found";
    }

    //UPDATE
    @RequestMapping(value = "/unversity/{id}", method = RequestMethod.PUT)
    public String editUniversity(@PathVariable Integer id, @RequestBody UniversityDto universityDto) {

        Optional<University> university = universityRepository.findById(id);
        if (university.isPresent()) {
            Integer addressId = university.get().getAddress().getId();
            Optional<Address> address = addressRepository.findById(addressId);
            if (address.isPresent()) {
                if (universityDto.getCity() != null) {
                    address.get().setCity(universityDto.getCity());
                }
                if (universityDto.getDistrict() != null) {
                    address.get().setDistrict(universityDto.getDistrict());
                }
                if (universityDto.getStreet() != null) {
                    address.get().setStreet(universityDto.getStreet());
                }
                Address savedAddress = addressRepository.save(address.get());

                if (universityDto.getName() != null) {
                    university.get().setName(universityDto.getName());
                }
                university.get().setAddress(savedAddress);
                universityRepository.save(university.get());
                return "University updated";
            }
        }
        return "Unievrsity not found";
    }
}
