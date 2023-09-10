package com.springboot.modelrelation.dao;

import com.springboot.modelrelation.entity.Instructor;

public interface AppDAO {
    void save(Instructor theInstructor);

    Instructor findInstructorById(int theId);
}
