package rw.edu.university.academic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.edu.university.academic.model.CourseRegistration;
import rw.edu.university.academic.model.enums.RegistrationStatus;
import java.util.List;

public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Long> {
    List<CourseRegistration> findByStudent_Id(Long studentId);
    List<CourseRegistration> findByCourse_Id(Long courseId);
    List<CourseRegistration> findByAcademicYearAndSemester(String academicYear, String semester);
    List<CourseRegistration> findByStatus(RegistrationStatus status);
}
