package rw.edu.university.academic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.edu.university.academic.model.Student;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByStudentNumber(String studentNumber);
    List<Student> findByDepartment_Id(Long departmentId);
}
