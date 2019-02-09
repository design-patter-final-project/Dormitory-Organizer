package designpatternproject.database.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.util.List;
import javafx.beans.property.*;
import javafx.collections.ObservableList;

public class Student extends RecursiveTreeObject<Student> {
    
    private StringProperty id;
    private StringProperty name;
    private StringProperty password;
    private StringProperty department;
    private ListProperty<Duration> duration; 
    
    public Student() {
        
    }
    
    public Student(String id, String name, String password, String department) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.password = new SimpleStringProperty(password);
        this.department = new SimpleStringProperty(department);
    }

    public String getId() {
        return id.get();
    }
    
    public StringProperty getRowId() {
        return id;
    }

    public void setId(String id) {
        this.id = new SimpleStringProperty(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty getRowName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }
    
    public String getPassword() {
        return password.get();
    }
    
    public StringProperty getRowPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = new SimpleStringProperty(password);
    }

    public String getDepartment() {
        return department.get();
    }
    
    public StringProperty getRowDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = new SimpleStringProperty(department);
    }

    public ObservableList<Duration> getDuration() {
        return duration;
    }

    public void setDuration(ObservableList<Duration> duration) {
        this.duration = new SimpleListProperty(duration);
    }
    
    
}
