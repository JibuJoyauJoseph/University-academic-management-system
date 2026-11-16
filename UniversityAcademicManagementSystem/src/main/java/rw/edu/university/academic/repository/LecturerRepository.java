package rw.edu.university.academic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.edu.university.academic.model.Lecturer;
import java.util.List;
import java.util.Optional;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
    Optional<Lecturer> findByStaffNumber(String staffNumber);
    List<Lecturer> findByDepartment_Id(Long departmentId);
}
