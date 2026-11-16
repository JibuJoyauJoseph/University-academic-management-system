package rw.edu.university.academic.model;

import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String code;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(length = 500)
    private String description;

    /*
     * Unique means a lecturer can head at most one department.
     * Nullable during initial setup because Department and Lecturer refer to
     * each other; assign the head lecturer after both records exist.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "head_lecturer_id", unique = true)
    private Lecturer headLecturer;

    @OneToMany(mappedBy = "department")
    private Set<Student> students = new LinkedHashSet<>();

    @OneToMany(mappedBy = "department")
    private Set<Lecturer> lecturers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "department")
    private Set<Course> courses = new LinkedHashSet<>();

    protected Department() {
        // Required by JPA.
    }

    public Department(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public Long getId() { return id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Lecturer getHeadLecturer() { return headLecturer; }
    public void setHeadLecturer(Lecturer headLecturer) { this.headLecturer = headLecturer; }
    public Set<Student> getStudents() { return students; }
    public Set<Lecturer> getLecturers() { return lecturers; }
    public Set<Course> getCourses() { return courses; }
}
