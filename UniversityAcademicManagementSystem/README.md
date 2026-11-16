# University Course Registration and Academic Management System

**Course:** Web Technology and Internet — Individual Assignment  
**Stack:** Java 17+, Spring Boot, Spring Data JPA / Hibernate, PostgreSQL, Maven  
**Scope:** Model and Repository layers only

This project implements the analysis and database mapping described in the supplied assignment PDF. It includes six JPA entities, one enum, one Spring Data repository per entity, PostgreSQL configuration, a reference SQL schema, an ER diagram, design notes and a repository mapping test.

## 1. Project structure

```text
src/main/java/rw/edu/university/academic/
├── UniversityAcademicApplication.java
├── model/
│   ├── Department.java
│   ├── Student.java
│   ├── StudentProfile.java
│   ├── Lecturer.java
│   ├── Course.java
│   └── CourseRegistration.java
│   └── enums/RegistrationStatus.java
└── repository/
    ├── DepartmentRepository.java
    ├── StudentRepository.java
    ├── StudentProfileRepository.java
    ├── LecturerRepository.java
    ├── CourseRepository.java
    └── CourseRegistrationRepository.java

docs/                 Problem analysis, ERD, relational schema
 database/schema.sql   Reference PostgreSQL DDL
```

## 2. Entities and relationships

- **Department** has many students, lecturers and courses, and has one head lecturer.
- **Student** belongs to one department and has one personal profile.
- **StudentProfile** stores date of birth, gender, phone number and residential address.
- **Lecturer** belongs to one department and can teach many courses.
- **Course** belongs to one department and can be taught by many lecturers.
- **CourseRegistration** connects a student to a course and records registration date, academic year, semester, status and grade.

The Lecturer-Course relationship uses `@ManyToMany` and the `course_lecturers` join table, with no separate Java entity. Student-Course registration uses `CourseRegistration` as a join entity because the relationship has its own attributes.

Read [`docs/PROBLEM_ANALYSIS.md`](docs/PROBLEM_ANALYSIS.md), [`docs/ERD.md`](docs/ERD.md) and [`docs/RELATIONAL_SCHEMA.md`](docs/RELATIONAL_SCHEMA.md) for the complete analysis.

## 3. Requirements

- JDK 17 or later (the project was authored for Java 17; JDK 21 also works).
- Apache Maven 3.9 or later.
- PostgreSQL installed and running.
- Visual Studio Code or another Java IDE.

## 4. Create the PostgreSQL database

In pgAdmin Query Tool or `psql`, run:

```sql
CREATE DATABASE university_academic_db;
```

The default connection settings are:

| Setting | Default |
|---|---|
| URL | `jdbc:postgresql://localhost:5432/university_academic_db` |
| Username | `postgres` |
| Password | `postgres` |

Change the default password to the one configured on your own PostgreSQL installation. You can either edit `src/main/resources/application.properties` or set environment variables before starting the application.

### Windows PowerShell example

```powershell
$env:DB_URL = "jdbc:postgresql://localhost:5432/university_academic_db"
$env:DB_USERNAME = "postgres"
$env:DB_PASSWORD = "your_actual_postgres_password"
```

Do not commit real database passwords to GitHub.

## 5. Run the project

Open a terminal in the folder containing `pom.xml` and run:

```powershell
mvn clean test
mvn spring-boot:run
```

The test uses an in-memory H2 database in PostgreSQL compatibility mode to check the entity mappings and repository queries. The application itself is configured to use PostgreSQL.

On first application startup, Hibernate creates or updates the tables based on the JPA mappings. The configured default is `spring.jpa.hibernate.ddl-auto=update`. For an already-created schema, you can change it to `validate` after checking the database structure.

## 6. Verify generated tables

Refresh the `Schemas > public > Tables` node for `university_academic_db` in pgAdmin. The expected tables are:

1. `departments`
2. `students`
3. `student_profiles`
4. `lecturers`
5. `courses`
6. `course_lecturers`
7. `course_registrations`

The `course_lecturers` table is the many-to-many join table. The `course_registrations` table is the join entity table and contains the additional registration attributes.

The Department-Head Lecturer relationship is circular at the database level because lecturers belong to departments while departments reference their head lecturer. For that reason, `head_lecturer_id` is nullable during initial creation. Create the department, create one of its lecturers, and then assign that lecturer as the department head. The one-to-one mapping and unique constraint ensure one lecturer cannot head multiple departments.

## 7. Capture assignment screenshots

The assignment requests screenshots showing the generated PostgreSQL tables and relationship mappings. Capture your own pgAdmin screenshots after running against PostgreSQL and save them in [`screenshots/`](screenshots/). The folder contains a checklist. H2 test results are not a substitute for PostgreSQL screenshots.

## 8. GitHub submission

From the project root:

```powershell
git init
git add .
git commit -m "Implement university academic model and repositories"
git branch -M main
git remote add origin https://github.com/YOUR-USERNAME/university-academic-management-system.git
git push -u origin main
```

Create the repository on GitHub first and replace the URL with your own repository URL. Make sure the repository is accessible to your lecturer before the deadline in the assignment brief.

## 9. Scope boundary

This deliverable intentionally does **not** add REST controllers, service classes, authentication, a frontend or CRUD screens. The PDF's technical implementation section limits this stage to the Model and Repository layers. Those application layers can be added in a later project phase if required.
