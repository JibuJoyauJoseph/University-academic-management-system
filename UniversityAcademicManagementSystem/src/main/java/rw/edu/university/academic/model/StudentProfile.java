package rw.edu.university.academic.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "student_profiles")
public class StudentProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    /* Kept as text because the assignment does not prescribe gender categories. */
    @Column(nullable = false, length = 30)
    private String gender;

    @Column(name = "phone_number", nullable = false, length = 30)
    private String phoneNumber;

    @Column(name = "residential_address", nullable = false, length = 300)
    private String residentialAddress;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false, unique = true)
    private Student student;

    protected StudentProfile() {
        // Required by JPA.
    }

    public StudentProfile(LocalDate dateOfBirth, String gender,
                          String phoneNumber, String residentialAddress) {
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.residentialAddress = residentialAddress;
    }

    public Long getId() { return id; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getResidentialAddress() { return residentialAddress; }
    public void setResidentialAddress(String residentialAddress) { this.residentialAddress = residentialAddress; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
}
