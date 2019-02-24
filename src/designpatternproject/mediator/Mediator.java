package designpatternproject.mediator;

import designpatternproject.database.model.Student;
import javafx.scene.Scene;

public interface Mediator {
    void registerScene(Component component);
    void logInStudent(Student student);
    void logOutStudent();
    Student getLoggedStudent();
}
