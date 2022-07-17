package com.example.springjparelationships.respository;

import com.example.springjparelationships.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    Page<Student> findAllByGroup_Faculty_UniversityId(Integer group_faculty_university_id, Pageable pageable);
    List<Student> findAllByGroup_FacultyId(Integer group_faculty_id);
    List<Student> findAllByGroupId(Integer group_id);
}
