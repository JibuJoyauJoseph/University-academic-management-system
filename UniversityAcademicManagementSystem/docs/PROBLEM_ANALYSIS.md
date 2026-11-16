# Problem Analysis and Database Design

## 1. Entities and attributes

| Entity | Main attributes | Why it is required |
|---|---|---|
| Department | `id`, `code`, `name`, `description`, `headLecturer` | Represents an academic department that organizes students, lecturers and courses and has a head lecturer. |
| Student | `id`, `studentNumber`, `firstName`, `lastName`, `email`, `admissionDate`, `department` | Stores each admitted student and the department to which the student belongs. |
| StudentProfile | `id`, `dateOfBirth`, `gender`, `phoneNumber`, `residentialAddress`, `student` | Stores the personal profile required for each student. |
| Lecturer | `id`, `staffNumber`, `firstName`, `lastName`, `email`, `phoneNumber`, `department`, `courses` | Stores lecturers employed by a department and the courses they teach. |
| Course | `id`, `courseCode`, `title`, `description`, `credits`, `department`, `lecturers` | Represents a course offered by one department and taught by one or more lecturers. |
| CourseRegistration | `id`, `registrationDate`, `academicYear`, `semester`, `status`, `grade`, `student`, `course` | Resolves the Student-Course many-to-many relationship and stores the details required for each registration. |

`RegistrationStatus` is an enum, not a separate entity. Its allowed values are exactly `PENDING`, `APPROVED`, and `REJECTED` as specified in the assignment.

## 2. Relationships and cardinality

| Relationship | Cardinality | Implementation |
|---|---|---|
| Department to Student | One department has many students; each student belongs to one department (1:M). | `Student.department` is `@ManyToOne`; FK `students.department_id`. |
| Department to Lecturer | One department employs many lecturers; each lecturer belongs to one department (1:M). | `Lecturer.department` is `@ManyToOne`; FK `lecturers.department_id`. |
| Department to Course | One department offers many courses; each course belongs to one department (1:M). | `Course.department` is `@ManyToOne`; FK `courses.department_id`. |
| Department to its head Lecturer | One department has one head; a lecturer can head at most one department (1:1). | `Department.headLecturer` is `@OneToOne`; unique FK `departments.head_lecturer_id`. |
| Student to StudentProfile | Each student has one profile and each profile belongs to one student (1:1). | `StudentProfile.student` is `@OneToOne`; unique FK `student_profiles.student_id`. |
| Lecturer to Course | A lecturer can teach many courses and a course can be taught by many lecturers (M:N). | `@ManyToMany` with `course_lecturers`; no separate entity is created. |
| Student to Course | A student can register for many courses and a course can have many students (M:N). | `CourseRegistration` is the join entity because the relationship has its own attributes. |

## 3. Foreign keys

- `students.department_id` references `departments.id`.
- `lecturers.department_id` references `departments.id`.
- `courses.department_id` references `departments.id`.
- `departments.head_lecturer_id` references `lecturers.id` and is unique.
- `student_profiles.student_id` references `students.id` and is unique.
- `course_lecturers.course_id` references `courses.id`.
- `course_lecturers.lecturer_id` references `lecturers.id`.
- `course_registrations.student_id` references `students.id`.
- `course_registrations.course_id` references `courses.id`.

## 4. Join table versus join entity

**Lecturer-Course:** The assignment explicitly says to maintain this relationship without creating a separate entity. Therefore, the implementation uses a JPA `@ManyToMany` mapping and the `course_lecturers` join table. This table contains only the two foreign keys.

**Student-Course:** This relationship records registration date, academic year, semester, status and grade. A plain many-to-many join table would not represent these attributes cleanly. Therefore, `CourseRegistration` is a proper entity with its own primary key and two many-to-one relationships.

## 5. Design notes and assumptions

1. IDs use `Long` with `GenerationType.IDENTITY`.
2. Gender, academic year and semester are stored as text because the assignment does not prescribe permitted values or a naming convention for them.
3. Registration status is an enum because the assignment lists its three permitted values.
4. Grade uses `BigDecimal` and is nullable until a grade is awarded. The assignment does not specify a grading scale, so no numeric range is imposed in Java.
5. A unique constraint prevents duplicate registration of the same student in the same course during the same academic year and semester. The same course may be taken again in a later term.
6. The department/head-lecturer association is mapped as one-to-one. Its FK is temporarily nullable to support initial creation of the mutually dependent Department and Lecturer records; assign a head after creating both records. A stricter business rule can be enforced by a service layer in a later phase.
7. The project implements only the Model and Repository layers, as the assignment's technical scope requires. It does not add controllers, services, authentication or a frontend.
