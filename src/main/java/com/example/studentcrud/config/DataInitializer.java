package com.example.studentcrud.config;

import com.example.studentcrud.entity.Student;
import com.example.studentcrud.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner loadSampleStudents(StudentRepository studentRepository) {
        return args -> {
            if (studentRepository.count() > 0) {
                return;
            }

            studentRepository.save(createStudent("S1001", "Ava", "Johnson", "ava.johnson@example.com"));
            studentRepository.save(createStudent("S1002", "Noah", "Smith", "noah.smith@example.com"));
            studentRepository.save(createStudent("S1003", "Mia", "Brown", "mia.brown@example.com"));
        };
    }

    private Student createStudent(String studentId, String firstName, String lastName, String email) {
        Student student = new Student();
        student.setStudentId(studentId);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);
        return student;
    }
}
