package com.example.springjparelationships.respository;

import com.example.springjparelationships.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

    List<Group> findAllByFaculty_UniversityId(Integer faculty_university_id);

    @Query(value = "select gr from groups gr where gr.faculty.university.id=:universityId")
    List<Group> getGroupsByUniversityId(Integer universityId);


    @Query(value = "select * from groups g join faculty f on f.id = g.faculty_id" +
            " join university u on f.university_id = u.id\n" +
            "where u.id=:universityId", nativeQuery = true)
    List<Group> getList(Integer universityId);
}
