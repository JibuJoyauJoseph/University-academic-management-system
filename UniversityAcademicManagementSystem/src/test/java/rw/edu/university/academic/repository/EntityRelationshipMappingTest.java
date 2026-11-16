package rw.edu.university.academic.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import rw.edu.university.academic.model.*;
import rw.edu.university.academic.model.enums.RegistrationStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Exercises the core relationships using an in-memory H2 database in
 * PostgreSQL compatibility mode. PostgreSQL itself is still the target DB.
 */
@DataJpaTest(properties = {
    "spring.datasource.url=jdbc:h2:mem:university_test;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DATABASE_TO_LOWER=TRUE",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password="
})
class EntityRelationshipMappingTest {

    @Autowired private DepartmentRepository departmentRepository;
    @Autowired private LecturerRepository lecturerRepository;
    @Autowired private CourseRepository courseRepository;
    @Autowired private StudentRepository studentRepository;
    @Autowired private StudentProfileRepository studentProfileRepository;
    @Autowired private CourseRegistrationRepository registrationRepository;

    @Test
    void persistsOneToOneOneToManyManyToManyAndRegistrationRelationships() {
        Department department = departmentRepository.saveAndFlush(
            new Department("IT", "Information Technology", "Computing and information systems")
        );

        Lecturer lecturer = lecturerRepository.saveAndFlush(
            new Lecturer("LEC-001", "Alex", "Mugisha", "alex@example.edu",
                         "+250700000001", department)
        );
        department.setHeadLecturer(lecturer);
        departmentRepository.saveAndFlush(department);

        Course course = new Course("WEB201", "Web Technology", "Web development fundamentals", 3, department);
        course.addLecturer(lecturer);
        courseRepository.saveAndFlush(course);

        Student student = new Student(
            "STU-001", "Jordan", "Uwase", "jordan@example.edu",
            LocalDate.of(2026, 9, 1), department
        );
        StudentProfile profile = new StudentProfile(
            LocalDate.of(2005, 5, 12), "Female", "+250700000002", "Kigali, Rwanda"
        );
        student.setProfile(profile);
        studentRepository.saveAndFlush(student);

        CourseRegistration registration = new CourseRegistration(
            student, course, LocalDate.of(2026, 9, 5), "2026-2027", "Semester 1",
            RegistrationStatus.PENDING, null
        );
        student.addRegistration(registration);
        course.addRegistration(registration);
        registrationRepository.saveAndFlush(registration);

        assertThat(departmentRepository.findById(department.getId()).orElseThrow().getHeadLecturer().getId())
            .isEqualTo(lecturer.getId());
        assertThat(studentProfileRepository.findByStudent_Id(student.getId())).isPresent();
        assertThat(courseRepository.findDistinctByLecturers_Id(lecturer.getId()))
            .extracting(Course::getCourseCode).contains("WEB201");
        assertThat(registrationRepository.findByStudent_Id(student.getId())).hasSize(1);
        assertThat(registrationRepository.findByStatus(RegistrationStatus.PENDING)).hasSize(1);
    }
}
