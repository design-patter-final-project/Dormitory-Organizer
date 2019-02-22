package designpatternproject.database;

import designpatternproject.database.model.Duration;
import designpatternproject.database.model.Student;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StudentDaoImpl implements StudentDao {
    
    private static StudentDaoImpl studentDaoImpl;
    private DurationDaoImpl durationDoaImpl = DurationDaoImpl.getInstance();
    
    private StudentDaoImpl() {
      
    }
    
    public static StudentDaoImpl getInstance() {
        if(studentDaoImpl == null){
            studentDaoImpl = new StudentDaoImpl();
        }
        return studentDaoImpl;
    }

    @Override
    public ObservableList<Student> getAll() {
        try {
            String query = "SELECT * FROM students";
            ObservableList<Student> students = FXCollections.observableArrayList();
            ObservableList<Duration> durations = FXCollections.observableArrayList();
            
            String url = "jdbc:mysql://localhost:3306/jdbctry";
            String username = "jdbc";
            String password = "jdbcpassword";
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, username, password);
            Statement st = conn.createStatement();
            
            ResultSet rs = st.executeQuery(query);
            while(rs.next()) {
                Student student = new Student(rs.getString("id"), rs.getString("name"), rs.getString("password"), rs.getString("department"));
                
                durations = durationDoaImpl.getDurationByReference(rs.getString("id"));
                
                student.setDuration(durations);
                students.add(student);
            }
            return students;
        } catch (Exception ex) {
            System.out.println(ex.getStackTrace());
            return null;
        }
       
    }

    @Override
    public Student getStudentById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int addStudent(Student student) {
        try {
            String query = "INSERT INTO students VALUES (?,?,?,?)";
            
            String url = "jdbc:mysql://localhost:3306/jdbctry";
            String username = "jdbc";
            String password = "jdbcpassword";
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, username, password);
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, student.getId());
            st.setString(2, student.getName());
            st.setString(3, student.getPassword());
            st.setString(4, student.getDepartment());
            
            int count = st.executeUpdate();
            
            if(count > 0){ // student added, add duration
                student.getDuration().forEach(duration -> {
                    durationDoaImpl.addDuration(duration);
                });
            }
            return count;
        } catch (Exception ex) {
            System.out.println(ex);
            return -1;
        }
    }

    @Override
    public void updateStudent(Student studnet) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteStudent(Student student) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
