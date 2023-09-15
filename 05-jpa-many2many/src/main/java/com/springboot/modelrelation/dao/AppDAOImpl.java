package com.springboot.modelrelation.dao;

import com.springboot.modelrelation.entity.Course;
import com.springboot.modelrelation.entity.Instructor;
import com.springboot.modelrelation.entity.InstructorDetails;
import com.springboot.modelrelation.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AppDAOImpl implements AppDAO{

//    define field for entity manager
    private EntityManager entityManager;

//    inject entity manager using constructor injection
    @Autowired
    public AppDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Instructor theInstructor) {
        entityManager.persist(theInstructor);
    }

    @Override
    public Instructor findInstructorById(int theId) {
        return entityManager.find(Instructor.class, theId);
        // Will also retrieve the instructor details object
        // because of default behavior of @OneToOne fetch type is eager
    }

    @Override
    @Transactional
    public void deleteInstructorById(int theId) {
//        Retrieve the instructor
        Instructor tempInstructor = entityManager.find(Instructor.class, theId);

//        Get the courses
        List<Course> courses = tempInstructor.getCourses();

//        Break the association between the instructor and the courses
        for (Course tempCourse : courses) {
            tempCourse.setInstructor(null); // <-- remove associated instructor from courses
        }

//        Delete the instructor
        entityManager.remove(tempInstructor);
    }

    @Override
    public InstructorDetails findInstructorDetailsById(int theId) {
        return entityManager.find(InstructorDetails.class, theId);
    }

    @Override
    @Transactional
    public void deleteInstructorDetailsById(int theId) {
//        InstructorDetails tempInstDetails = entityManager.find(InstructorDetails.class, theId);
//        entityManager.remove(tempInstDetails);

        InstructorDetails tempInstDetails = entityManager.find(InstructorDetails.class, theId);
        if (tempInstDetails != null) {

//            - get the instructor that associate with the instructorDetails
//            - then set the instructorDetails of that instructor to null
            tempInstDetails.getInstructor().setInstructorDetails(null);

            entityManager.remove(tempInstDetails);
            System.out.println("Instructor details deleted successfully.");
        } else {
            System.out.println("Instructor details not found for ID: " + theId);
        }
    }

    @Override
    public List<Course> findCoursesByInstructorId(int theId) {
        TypedQuery<Course> query = entityManager.createQuery(
                "from Course where instructor.id = :data", Course.class
//                                                         :data <-- name parameter has ':'
        );
        query.setParameter("data", theId); // "data" <-- actual value no ':'

//        execute query
        List<Course> courses = query.getResultList();
        return courses;
    }

    @Override
    public Instructor findInstructorByIdJoinFetch(int theId) {
        TypedQuery<Instructor> query = entityManager.createQuery(
//                                            'i' is alias.
                    "select i from Instructor i JOIN FETCH i.courses JOIN FETCH i.instructorDetails " +
//                                                          Need ' ' a whitespace before the quotation since multiple strings will be combined
                    "where i.id = :data", Instructor.class);
        query.setParameter("data", theId);

//        Execute query
        Instructor instructor = query.getSingleResult();

        return instructor;
    }

    @Override
    @Transactional
    public void update(Instructor tempInstructor) {
        entityManager.merge(tempInstructor); // entityManager.merge(thisParameter); <-- .merge will update the parameter in ()
    }

    @Override
    public Course findCourseById(int theId) {
        return entityManager.find(Course.class, theId);
    }

    @Override
    @Transactional
    public void updateCourse(Course tempCourse) {
        entityManager.merge((tempCourse));
    }

    @Override
    @Transactional
    public void deleteCourseById(int theId) {
//        Retrieve the course
        Course themCourse = entityManager.find(Course.class, theId);

//        Delete the course
        entityManager.remove(themCourse);
    }

    @Override
    @Transactional
    public void saveCourse(Course theCourse) {
        entityManager.persist(theCourse); // Will save both course and reviews since 'CascadeType.All' was declared
    }

    @Override
    public Course findCourseAndReviewsByCourseId(int theId) {
//        Create query
        TypedQuery<Course> query = entityManager.createQuery(
                "SELECT c FROM Course c JOIN FETCH c.reviews WHERE c.id = :data", Course.class
        );
        query.setParameter("data", theId);

//        Execute query
        Course course = query.getSingleResult();
        return course;
    }

    public Course findCourseAndStudentsByCourseId(int theId) {
        TypedQuery<Course> query = entityManager.createQuery(
                "SELECT c FROM Course c JOIN FETCH c.students WHERE c.id = :data", Course.class
        );
        query.setParameter("data", theId);

        try {
            Course course = query.getSingleResult();
            return course;
        } catch (NoResultException e) {
            // Course not found, return null or handle the exception as needed
            System.out.println("EXCEPTION ----> " + e);
            return null;
        }
    }

    @Override
    public Student findStudentAndCoursesByStudentId(int id) {
        TypedQuery<Student> query = entityManager.createQuery(
                "SELECT s FROM Student s JOIN FETCH s.courses WHERE s.id = :data", Student.class
        );
        query.setParameter("data", id);

        try {
            Student student = query.getSingleResult();
            return student;
        }
        catch (NoResultException e) {
            System.out.println("EXCEPTION ----> " + e);
            return null;
        }
    }

    @Override
    @Transactional
    public void updateStudentCourse(Student tempStudent) {
        entityManager.merge(tempStudent);
    }

    @Override
    @Transactional
    public void deleteStudentById(int studentId) {
//        Retrieve the student
        Student tempStudent = entityManager.find(Student.class, studentId);

//        Delete the student
        entityManager.remove(tempStudent);
    }
}
