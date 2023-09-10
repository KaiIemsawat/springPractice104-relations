package com.springboot.modelrelation;

import com.springboot.modelrelation.dao.AppDAO;
import com.springboot.modelrelation.entity.Instructor;
import com.springboot.modelrelation.entity.InstructorDetails;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ModelRelationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModelRelationApplication.class, args);
	}

//	For command line usage
	@Bean
	public CommandLineRunner commandLineRunner(AppDAO appDAO) {
		return runner -> {
			createInstructor(appDAO);
		};
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
				"Titann", "Iem", "titann@email.com"
		);

//		Create the instructor detail
		InstructorDetails tempInstructorDetail = new InstructorDetails(
				"www.titann.com/youtube", "eat anything"
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
