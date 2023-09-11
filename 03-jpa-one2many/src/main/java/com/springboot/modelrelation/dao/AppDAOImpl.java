package com.springboot.modelrelation.dao;

import com.springboot.modelrelation.entity.Course;
import com.springboot.modelrelation.entity.Instructor;
import com.springboot.modelrelation.entity.InstructorDetails;
import jakarta.persistence.EntityManager;
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
                    "select i from Instructor i JOIN FETCH i.courses " +
//                                                          Need ' ' a whitespace before the quotation since multiple strings will be combined
                    "where i.id = :data", Instructor.class);
        query.setParameter("data", theId);

//        Execute query
        Instructor instructor = query.getSingleResult();

        return instructor;
    }
}
