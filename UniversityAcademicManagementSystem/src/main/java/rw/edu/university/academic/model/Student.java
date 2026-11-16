package rw.edu.university.academic.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_number", nullable = false, unique = true, length = 30)
    private String studentNumber;

    @Column(name = "first_name", nullable = false, length = 80)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 80)
    private String lastName;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "admission_date", nullable = false)
    private LocalDate admissionDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private StudentProfile profile;

    @OneToMany(mappedBy = "student")
    private Set<CourseRegistration> registrations = new LinkedHashSet<>();

    protected Student() {
        // Required by JPA.
    }

    public Student(String studentNumber, String firstName, String lastName,
                   String email, LocalDate admissionDate, Department department) {
        this.studentNumber = studentNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.admissionDate = admissionDate;
        this.department = department;
    }

    public void setProfile(StudentProfile profile) {
        this.profile = profile;
        if (profile != null && profile.getStudent() != this) {
            profile.setStudent(this);
        }
    }

    public void addRegistration(CourseRegistration registration) {
        registrations.add(registration);
        registration.setStudent(this);
    }

    public Long getId() { return id; }
    public String getStudentNumber() { return studentNumber; }
    public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDate getAdmissionDate() { return admissionDate; }
    public void setAdmissionDate(LocalDate admissionDate) { this.admissionDate = admissionDate; }
    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
    public StudentProfile getProfile() { return profile; }
    public Set<CourseRegistration> getRegistrations() { return registrations; }
}
