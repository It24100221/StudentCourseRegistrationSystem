# 🎓 Student Course Registration System

A comprehensive Spring Boot application for university course registration and management with file-based data persistence.

## 📌 Project Summary

The Student Course Registration System is a full-stack Spring Boot web application that allows:

- Students to register and enroll in university courses
- Course browsing and registration with queue management
- Lecture scheduling and management
- Student feedback submission for courses
- Support request submission (Academic and Technical)
- User authentication and management
- RESTful API for all system operations

## 🧠 Features

- ✅ User authentication and management
- ✅ Course catalog and management
- ✅ Student enrollment with queue system
- ✅ Lecture scheduling
- ✅ Student feedback collection
- ✅ Support request handling
- ✅ File-based data persistence
- ✅ RESTful API endpoints
- ✅ Input validation and error handling

## 🧰 Tech Stack

| Layer | Technology |
|-------|------------|
| Language | Java |
| Backend | Spring Boot |
| Build Tool | Maven |
| Database | Plain text files (.txt) |
| API Design | RESTful Web Services |
| Validation | Jakarta Validation |
| JSON Processing | Jackson |

## 📂 File-Based Storage

All data is persisted in text files instead of a database:

- users.txt - User account information
- students.txt - Student information
- courses.txt - Course catalog
- enrollments.txt - Course enrollment records
- lectures.txt - Lecture schedule information
- feedback.txt - Student feedback on courses
- support.txt - Support requests from students

## 🚀 Running the Application

```
./mvnw.cmd spring-boot:run
```
## 👥 Group Members

-A.P.D.O.C.Wijesingha
-S.N.M.Nanayakkara
-E.M.G.O.Bandara
-E.M.N.A.Ekanayake
-K.H.M.S.S.Karunatathna

## 📱 Core Entities

### User
- Email, full name, password, and registration time
- Basic authentication information

### Student
- Student ID, full name, and email
- Core student information

### Course
- ID, title, instructor, price, duration, description, and learning points
- Comprehensive course information

### Enrollment
- ID, course ID, student information, payment method, and timestamp
- Tracks student enrollment in courses

### EnrollmentQueue
- Queue implementation for managing course enrollments
- Sorts enrollments by timestamp (first-come, first-served)

### Lecture
- ID, course, lecturer, day, time, duration, location, and description
- Detailed lecture scheduling information

### Feedback
- ID, student name, course name, rating, comments, and timestamps
- Student feedback on courses

### Support
- ID, student name, type (Academic or Technical), and message
- Student support requests

## 🔄 Enrollment Process

When a student enrolls in a course:

1. Student selects a course from the catalog
2. Student submits enrollment request with payment method
3. System adds enrollment to the queue (if space is available)
4. Enrollment is processed in timestamp order (first-come, first-served)
5. Student receives confirmation of enrollment

## 📝 Feedback System

Students can provide feedback on courses they've taken:

1. Student submits a rating (numeric) and comments
2. Feedback is stored with timestamps
3. Feedback can be viewed by administrators and instructors

## 🆘 Support System

Two types of support requests are available:

1. Academic Support - For course-related questions
2. Technical Support - For system-related issues

## 🔒 Security Note

User passwords are stored in plain text in the files. In a production environment, proper encryption and security measures should be implemented.
