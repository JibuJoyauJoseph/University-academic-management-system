# Entity Relationship Diagram (ERD)

The following Mermaid ER diagram can be previewed in VS Code with a Mermaid extension or in a Markdown viewer that supports Mermaid.

```mermaid
erDiagram
    DEPARTMENT ||--o{ STUDENT : contains
    DEPARTMENT ||--o{ LECTURER : employs
    DEPARTMENT ||--o{ COURSE : offers
    DEPARTMENT ||--o| LECTURER : headed_by
    STUDENT ||--|| STUDENT_PROFILE : has
    STUDENT ||--o{ COURSE_REGISTRATION : makes
    COURSE ||--o{ COURSE_REGISTRATION : receives
    COURSE }o--o{ LECTURER : taught_by

    DEPARTMENT {
        bigint id PK
        varchar code UK
        varchar name
        varchar description
        bigint head_lecturer_id FK,UK
    }
    STUDENT {
        bigint id PK
        varchar student_number UK
        varchar first_name
        varchar last_name
        varchar email UK
        date admission_date
        bigint department_id FK
    }
    STUDENT_PROFILE {
        bigint id PK
        date date_of_birth
        varchar gender
        varchar phone_number
        varchar residential_address
        bigint student_id FK,UK
    }
    LECTURER {
        bigint id PK
        varchar staff_number UK
        varchar first_name
        varchar last_name
        varchar email UK
        varchar phone_number
        bigint department_id FK
    }
    COURSE {
        bigint id PK
        varchar course_code UK
        varchar title
        varchar description
        int credits
        bigint department_id FK
    }
    COURSE_REGISTRATION {
        bigint id PK
        date registration_date
        varchar academic_year
        varchar semester
        varchar status
        decimal grade
        bigint student_id FK
        bigint course_id FK
    }
```

**Physical join table:** `course_lecturers(course_id, lecturer_id)` implements the Lecturer-Course many-to-many association. It is not a Java entity, as requested.

**Join entity:** `course_registrations` is a Java entity and a database table because the Student-Course association carries registration data.
