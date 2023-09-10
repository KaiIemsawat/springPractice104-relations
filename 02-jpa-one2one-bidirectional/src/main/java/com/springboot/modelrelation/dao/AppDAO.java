package com.springboot.modelrelation.dao;

import com.springboot.modelrelation.entity.Instructor;
import com.springboot.modelrelation.entity.InstructorDetails;

public interface AppDAO {
    void save(Instructor theInstructor);

    Instructor findInstructorById(int theId);

    void deleteInstructorById(int theId);

    InstructorDetails findInstructorDetailsById(int theId);

    void deleteInstructorDetailsById(int theId);
}
