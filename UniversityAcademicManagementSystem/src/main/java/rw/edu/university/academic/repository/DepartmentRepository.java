package rw.edu.university.academic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.edu.university.academic.model.Department;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByCodeIgnoreCase(String code);
}
