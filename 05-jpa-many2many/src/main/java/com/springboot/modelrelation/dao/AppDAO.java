package com.springboot.modelrelation.dao;

import com.springboot.modelrelation.entity.Course;
import com.springboot.modelrelation.entity.Instructor;
import com.springboot.modelrelation.entity.InstructorDetails;
import com.springboot.modelrelation.entity.Student;

import java.util.List;

public interface AppDAO {
    void save(Instructor theInstructor);

    Instructor findInstructorById(int theId);

    void deleteInstructorById(int theId);

    InstructorDetails findInstructorDetailsById(int theId);

    void deleteInstructorDetailsById(int theId);

    List<Course> findCoursesByInstructorId(int theId);

    Instructor findInstructorByIdJoinFetch(int theId);

    void update (Instructor tempInstructor);

    Course findCourseById(int theId);

    void updateCourse(Course tempCourse);

    void deleteCourseById(int theId);

    void saveCourse(Course theCourse);

    Course findCourseAndReviewsByCourseId(int theId);

    Course findCourseAndStudentsByCourseId(int theId);

    Student findStudentAndCoursesByStudentId(int id);

    void updateStudentCourse(Student tempStudent);

    void deleteStudentById(int studentId);
}
