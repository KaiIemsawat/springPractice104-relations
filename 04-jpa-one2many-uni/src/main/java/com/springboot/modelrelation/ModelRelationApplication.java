package com.springboot.modelrelation;

import com.springboot.modelrelation.dao.AppDAO;
import com.springboot.modelrelation.entity.Course;
import com.springboot.modelrelation.entity.Instructor;
import com.springboot.modelrelation.entity.InstructorDetails;
import com.springboot.modelrelation.entity.Review;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class ModelRelationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModelRelationApplication.class, args);
	}

//	For command line usage
	@Bean
	public CommandLineRunner commandLineRunner(AppDAO appDAO) {
		return runner -> {
//			createCourseAndReviews(appDAO);

			retrieveCourseAndReviews(appDAO);
		};
	}

	private void retrieveCourseAndReviews(AppDAO appDAO) {
//		Get the course and reviews
		int theId = 10;
		Course tempCourse = appDAO.findCourseAndReviewsByCourseId(theId);

//		Print the course
		System.out.println(tempCourse);

//		Print the associate reviews
		System.out.println(tempCourse.getReviews());

		System.out.println("Completed");
	}

	private void createCourseAndReviews(AppDAO appDAO) {
//		Create a course
		Course tempCourse = new Course("How to eat all the snacks");

//		Add reviews
		tempCourse.addReview(new Review("Now, I can eat all of the snack in the house"));
		tempCourse.addReview(new Review("Hooman gib me a lot of snacks now"));
		tempCourse.addReview(new Review("This is the best course for us, the doggos!!"));

//		Save course (and reviews)
		System.out.println("Saving the course");
		System.out.println(tempCourse);
		System.out.println(tempCourse.getReviews());

		appDAO.saveCourse(tempCourse);
	}

	private void deleteCourseById(AppDAO appDAO) {
		int theId = 10;
		System.out.println("Deleting course id : " + theId);

		appDAO.deleteCourseById(theId);
		System.out.println("Course " + theId + " is deleted..!");
	}

	private void updateCourseById(AppDAO appDAO) {
		int theId = 10;

//		find the course
		System.out.println("Finding course id : " + theId);
		Course tempCourse = appDAO.findCourseById(theId);

//		Update the course
		tempCourse.setTitle("Updated Course Title");;
		appDAO.updateCourse(tempCourse);
		System.out.println("Course updated... !");
	}

	private void updateInstructor(AppDAO appDAO) {
		int theId = 1;

//		find the instructor
		System.out.println("Finding instructor with id : " + theId);;
		Instructor tempInstructor = appDAO.findInstructorById(theId);

//		Update data for the instructor
		System.out.println("Updating instructor id : " + theId);
		tempInstructor.setLast_name("Tester");
		appDAO.update(tempInstructor);

		System.out.println("UPDATED..!");
	}

	private void findInstructorWithCoursesJoinFetch(AppDAO appDAO) {
		int theId = 1;
		System.out.println("Finding instructor id : " + theId);
		Instructor tempInstructor = appDAO.findInstructorByIdJoinFetch(theId);

		System.out.println("Temp instructor : " + tempInstructor);
		System.out.println("The associated courses : " + tempInstructor.getCourses());

		System.out.println("Completed..!");

	}

	private void findCoursesAssociateToInstructorId(AppDAO appDAO) {
		int theId = 1;
		System.out.println("Finding instructor id : " + theId);

		Instructor tempInstructor = appDAO.findInstructorById(theId);
		System.out.println("Temp instructor : " + tempInstructor);

//		find courses for instructor
		System.out.println("Finding courses for instructor id : " + theId);
		List<Course> courses =appDAO.findCoursesByInstructorId(theId);

//		Associate the objects
		tempInstructor.setCourses(courses);
//		Verify results
		System.out.println("The courses : " + tempInstructor.getCourses());
		System.out.println("Completed..!");
	}

	private void findInstructorAndCourses(AppDAO appDAO) {
		int theId = 1;
		System.out.println("Finding instructor id : " + theId);

		Instructor tempInstructor = appDAO.findInstructorById(theId);
		System.out.println("Temp instructor : " + tempInstructor);
		System.out.println("The associate courses : " + tempInstructor.getCourses());
		System.out.println("Completed..!");
	}

	private void createInstructorWithCourses(AppDAO appDAO) {
//		create the instructor
		Instructor tempInstructor = new Instructor(
				"Zukkii", "Iem", "zukkii@email.com"
		);

//		Create the instructor detail
		InstructorDetails tempInstructorDetail = new InstructorDetails(
				"www.zukkii.com", "Ball catching"
		);

//		Associate the objects
		tempInstructor.setInstructorDetails(tempInstructorDetail);

//		Create courses
		Course tempCourse1 = new Course("Random course 3");
		Course tempCourse2 = new Course("Random course 4");

//		Add courses to instructor
		tempInstructor.add(tempCourse1);
		tempInstructor.add(tempCourse2);

//		Save the instructor
//		Note : this will also save the course since CascadeType.PERSIST was declared in entity
		System.out.println("Saving instructor: " + tempInstructor);
		System.out.println("The coursed: " + tempInstructor.getCourses());
		appDAO.save(tempInstructor);

		System.out.println("Created");
	}


	private void deleteInstDetailsById(AppDAO appDAO) {
		int theId = 5;
		System.out.println("Deleting instructor details id : " + theId);

		appDAO.deleteInstructorDetailsById(theId);
		System.out.println("Deleted");
	}

	private void findInstructorDetailsById(AppDAO appDAO) {
//		Get instructor detail object
		int theId = 3;
		InstructorDetails tempInstDetails = appDAO.findInstructorDetailsById(theId);

//		print the instructor details
		System.out.println("The details " + tempInstDetails);

//		print the associated instructor
		System.out.println("The associated instructor " + tempInstDetails.getInstructor());
		System.out.println("Task Completed");
	}

	private void deleteInstructorById(AppDAO appDAO) { // with associate courses
		int theId = 1;
		System.out.println("Deleting instructor id : " + theId);

		appDAO.deleteInstructorById(theId);
		System.out.println("Deleted");
	}

	private void findInstructorById(AppDAO appDAO) {
		int theId = 2;
		System.out.println("Finding instructor id : " + theId);

		Instructor tempInstructor = appDAO.findInstructorById(theId);
		System.out.println("tempinstructor : " + tempInstructor);
		System.out.println("the associated instructorDetail only : " + tempInstructor.getInstructorDetails());
	}

	private void createInstructor(AppDAO appDAO) {

////		create the instructor
//		Instructor tempInstructor = new Instructor(
//				"Zukkii", "Iem", "zukkii@email.com"
//		);
//
////		Create the instructor detail
//		InstructorDetails tempInstructorDetail = new InstructorDetails(
//				"www.zukkii.com/youtube", "fight with red ball"
//		);


//		create the instructor
		Instructor tempInstructor = new Instructor(
				"Stokii", "Hampton", "biscuit@email.com"
		);

//		Create the instructor detail
		InstructorDetails tempInstructorDetail = new InstructorDetails(
				"www.stokii.com/youtube", "greeting people"
		);

//		Associate the objects
		tempInstructor.setInstructorDetails(tempInstructorDetail);

//		Save the instructor
//			- This will also save the details object in the process
//			- because of CascadeType.ALL
		System.out.println("Saving instructor: " + tempInstructor);
		appDAO.save(tempInstructor);

		System.out.println("Instructor SAVED !!!");
	};

}
