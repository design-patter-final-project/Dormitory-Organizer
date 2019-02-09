package designpatternproject.database;

import designpatternproject.database.model.Student;
import java.util.List;

public interface StudentDao {
    
    List<Student> getAll();
    Student getStudentById(String id);
    int addStudent(Student student);
    void updateStudent(Student studnet);
    void deleteStudent(Student student);
}
