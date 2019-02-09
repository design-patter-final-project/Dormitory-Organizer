package designpatternproject.database;

import designpatternproject.database.model.Duration;
import designpatternproject.database.model.Student;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DurationDaoImpl implements DurationDao {
    
    private static DurationDaoImpl durationDaoImpl;
    
    private DurationDaoImpl() {
        
    }
    
    public static DurationDaoImpl getInstance() {
        if(durationDaoImpl == null){
            durationDaoImpl = new DurationDaoImpl();
        }
        return durationDaoImpl;
    }
    
    @Override
    public ObservableList<Duration> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ObservableList<Duration> getDurationByReference(String referenceKey) {
        try {
            String query = "SELECT * FROM durations WHERE student_id = '" + referenceKey + "'";
            ObservableList<Duration> durations = FXCollections.observableArrayList();
            
            String url = "jdbc:mysql://localhost:3306/jdbctry";
            String username = "jdbc";
            String password = "jdbcpassword";
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, username, password);
            Statement st = conn.createStatement();
            
            ResultSet rsDuration = st.executeQuery(query);
            while(rsDuration.next()){
                Duration duration = new Duration(rsDuration.getInt("id"), rsDuration.getString("student_id"), rsDuration.getInt("year"), rsDuration.getInt("status"));
                durations.add(duration);
            }
            return durations;
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }

    @Override
    public Duration getDurationById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int addDuration(Duration duration) {
        try {
            String query = "INSERT INTO durations (student_id, year, status) VALUES (?,?,?)";
            
            String url = "jdbc:mysql://localhost:3306/jdbctry";
            String username = "jdbc";
            String password = "jdbcpassword";
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, username, password);
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, duration.getReferenceKey());
            st.setInt(2, duration.getYear());
            st.setInt(3, duration.getStatus());
            
            int count = st.executeUpdate();
            
            return count;
            
        } catch (Exception ex) {
            System.out.println(ex.getStackTrace());
            return -1;
        }
    }
    
}
