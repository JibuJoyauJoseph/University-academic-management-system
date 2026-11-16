-- Reference relational schema for university_academic_db.
-- Hibernate can generate/update these tables from the entity annotations.
-- The department/head-lecturer relationship is added after both base tables
-- exist because Department and Lecturer reference each other.

CREATE TABLE departments (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(120) NOT NULL,
    description VARCHAR(500)
);

CREATE TABLE lecturers (
    id BIGSERIAL PRIMARY KEY,
    staff_number VARCHAR(30) NOT NULL UNIQUE,
    first_name VARCHAR(80) NOT NULL,
    last_name VARCHAR(80) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    phone_number VARCHAR(30),
    department_id BIGINT NOT NULL REFERENCES departments(id)
);

ALTER TABLE departments
    ADD COLUMN head_lecturer_id BIGINT UNIQUE;

ALTER TABLE departments
    ADD CONSTRAINT fk_department_head_lecturer
    FOREIGN KEY (head_lecturer_id) REFERENCES lecturers(id);

CREATE TABLE students (
    id BIGSERIAL PRIMARY KEY,
    student_number VARCHAR(30) NOT NULL UNIQUE,
    first_name VARCHAR(80) NOT NULL,
    last_name VARCHAR(80) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    admission_date DATE NOT NULL,
    department_id BIGINT NOT NULL REFERENCES departments(id)
);

CREATE TABLE student_profiles (
    id BIGSERIAL PRIMARY KEY,
    date_of_birth DATE NOT NULL,
    gender VARCHAR(30) NOT NULL,
    phone_number VARCHAR(30) NOT NULL,
    residential_address VARCHAR(300) NOT NULL,
    student_id BIGINT NOT NULL UNIQUE REFERENCES students(id)
);

CREATE TABLE courses (
    id BIGSERIAL PRIMARY KEY,
    course_code VARCHAR(30) NOT NULL UNIQUE,
    title VARCHAR(160) NOT NULL,
    description VARCHAR(1000),
    credits INTEGER NOT NULL,
    department_id BIGINT NOT NULL REFERENCES departments(id)
);

-- Join table for Lecturer <-> Course. No separate Java entity is required.
CREATE TABLE course_lecturers (
    course_id BIGINT NOT NULL REFERENCES courses(id) ON DELETE CASCADE,
    lecturer_id BIGINT NOT NULL REFERENCES lecturers(id) ON DELETE CASCADE,
    CONSTRAINT pk_course_lecturers PRIMARY KEY (course_id, lecturer_id)
);

-- Join entity for Student <-> Course because registration has its own attributes.
CREATE TABLE course_registrations (
    id BIGSERIAL PRIMARY KEY,
    registration_date DATE NOT NULL,
    academic_year VARCHAR(20) NOT NULL,
    semester VARCHAR(30) NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED')),
    grade NUMERIC(5,2),
    student_id BIGINT NOT NULL REFERENCES students(id),
    course_id BIGINT NOT NULL REFERENCES courses(id),
    CONSTRAINT uk_registration_student_course_term
        UNIQUE (student_id, course_id, academic_year, semester)
);

CREATE INDEX idx_students_department ON students(department_id);
CREATE INDEX idx_lecturers_department ON lecturers(department_id);
CREATE INDEX idx_courses_department ON courses(department_id);
CREATE INDEX idx_registrations_student ON course_registrations(student_id);
CREATE INDEX idx_registrations_course ON course_registrations(course_id);
