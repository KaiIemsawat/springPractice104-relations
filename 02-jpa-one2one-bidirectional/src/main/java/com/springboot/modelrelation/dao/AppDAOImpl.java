package com.springboot.modelrelation.dao;

import com.springboot.modelrelation.entity.Instructor;
import com.springboot.modelrelation.entity.InstructorDetails;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
            entityManager.remove(tempInstDetails);
            System.out.println("Instructor details deleted successfully.");
        } else {
            System.out.println("Instructor details not found for ID: " + theId);
        }
    }
}
