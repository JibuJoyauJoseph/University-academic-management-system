package rw.edu.university.academic.model;

import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_code", nullable = false, unique = true, length = 30)
    private String courseCode;

    @Column(nullable = false, length = 160)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private Integer credits;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    /* Owning side of the lecturer-course many-to-many relationship. */
    @ManyToMany
    @JoinTable(
        name = "course_lecturers",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "lecturer_id"),
        uniqueConstraints = @UniqueConstraint(
            name = "uk_course_lecturer", columnNames = {"course_id", "lecturer_id"}
        )
    )
    private Set<Lecturer> lecturers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "course")
    private Set<CourseRegistration> registrations = new LinkedHashSet<>();

    protected Course() {
        // Required by JPA.
    }

    public Course(String courseCode, String title, String description,
                  Integer credits, Department department) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.credits = credits;
        this.department = department;
    }

    public void addLecturer(Lecturer lecturer) {
        lecturers.add(lecturer);
        if (!lecturer.getCourses().contains(this)) {
            lecturer.getCourses().add(this);
        }
    }

    public void addRegistration(CourseRegistration registration) {
        registrations.add(registration);
        registration.setCourse(this);
    }

    public Long getId() { return id; }
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }
    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
    public Set<Lecturer> getLecturers() { return lecturers; }
    public Set<CourseRegistration> getRegistrations() { return registrations; }
}
