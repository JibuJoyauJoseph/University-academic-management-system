# Relational Database Schema

Database name: `university_academic_db` (PostgreSQL).

| Table | Primary key | Foreign keys | Important constraints |
|---|---|---|---|
| `departments` | `id` | `head_lecturer_id` → `lecturers.id` | `code` unique; `head_lecturer_id` unique |
| `students` | `id` | `department_id` → `departments.id` | `student_number` and `email` unique |
| `student_profiles` | `id` | `student_id` → `students.id` | `student_id` unique: one profile per student |
| `lecturers` | `id` | `department_id` → `departments.id` | `staff_number` and `email` unique |
| `courses` | `id` | `department_id` → `departments.id` | `course_code` unique |
| `course_lecturers` | (`course_id`, `lecturer_id`) | `course_id` → `courses.id`; `lecturer_id` → `lecturers.id` | Composite primary key prevents duplicate course-lecturer links |
| `course_registrations` | `id` | `student_id` → `students.id`; `course_id` → `courses.id` | Unique (`student_id`, `course_id`, `academic_year`, `semester`) |

The executable reference DDL is in [`../database/schema.sql`](../database/schema.sql). Hibernate can also create/update the tables from the entity annotations when the application starts with `spring.jpa.hibernate.ddl-auto=update`.
