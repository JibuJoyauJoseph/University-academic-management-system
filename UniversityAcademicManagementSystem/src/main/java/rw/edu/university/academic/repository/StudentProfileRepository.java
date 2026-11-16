package rw.edu.university.academic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.edu.university.academic.model.StudentProfile;
import java.util.Optional;

public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {
    Optional<StudentProfile> findByStudent_Id(Long studentId);
}
