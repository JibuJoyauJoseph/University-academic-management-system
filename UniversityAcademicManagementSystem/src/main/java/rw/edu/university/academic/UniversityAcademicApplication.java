package rw.edu.university.academic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application entry point. This assignment intentionally contains only the
 * Model and Repository layers; controllers and user-interface code are out of scope.
 */
@SpringBootApplication
public class UniversityAcademicApplication {

    public static void main(String[] args) {
        SpringApplication.run(UniversityAcademicApplication.class, args);
    }
}
