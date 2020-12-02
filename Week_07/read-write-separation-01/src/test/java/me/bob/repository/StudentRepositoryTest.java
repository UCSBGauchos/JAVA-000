package me.bob.repository;

import me.bob.entity.StudentEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    private Long idTemp = new Random().nextLong();

    private String nameTemp = UUID.randomUUID().toString().substring(1, 10);

    @Test
    void testAll() throws SQLException {
        studentRepository.add(StudentEntity.builder().id(idTemp).name(nameTemp).build());
        String name = studentRepository.locate(idTemp);
        assertEquals(name, nameTemp);
        System.out.println("name = " + name);
        System.out.println("nameTemp = " + nameTemp);
    }

    @Test
    void add() throws SQLException {
        studentRepository.add(StudentEntity.builder().id(idTemp).name(nameTemp).build());
    }

    @Test
    void locate() {
        String name = studentRepository.locate(idTemp);
        System.out.println("name = " + name);
    }

}