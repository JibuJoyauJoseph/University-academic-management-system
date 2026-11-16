package rw.edu.university.academic.model;

import jakarta.persistence.*;
import rw.edu.university.academic.model.enums.RegistrationStatus;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
    name = "course_registrations",
    uniqueConstraints = @UniqueConstraint(
        name = "uk_registration_student_course_term",
        columnNames = {"student_id", "course_id", "academic_year", "semester"}
    )
)
public class CourseRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @Column(name = "academic_year", nullable = false, length = 20)
    private String academicYear;

    /* String intentionally allows the university to choose its semester labels. */
    @Column(nullable = false, length = 30)
    private String semester;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RegistrationStatus status;

    /* Grade is left nullable until it has been awarded. The assignment gives no grading scale. */
    @Column(precision = 5, scale = 2)
    private BigDecimal grade;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    protected CourseRegistration() {
        // Required by JPA.
    }

    public CourseRegistration(Student student, Course course, LocalDate registrationDate,
                              String academicYear, String semester,
                              RegistrationStatus status, BigDecimal grade) {
        this.student = student;
        this.course = course;
        this.registrationDate = registrationDate;
        this.academicYear = academicYear;
        this.semester = semester;
        this.status = status;
        this.grade = grade;
    }

    public Long getId() { return id; }
    public LocalDate getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }
    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }
    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }
    public RegistrationStatus getStatus() { return status; }
    public void setStatus(RegistrationStatus status) { this.status = status; }
    public BigDecimal getGrade() { return grade; }
    public void setGrade(BigDecimal grade) { this.grade = grade; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
}
