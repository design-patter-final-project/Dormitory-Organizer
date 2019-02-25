package designpatternproject.factory;

import designpatternproject.StudentViewController;
import designpatternproject.mediator.Authenticator;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StudentWinGenerator implements WinGenerator {

    @Override
    public void generate() {
        try {
            String path = "/home/nbody/NetBeansProjects/DormitoryOrganizer/src/designpatternproject/fxml/StudentView.fxml";
            FXMLLoader loader = new FXMLLoader(Paths.get(path).toUri().toURL());
            Parent root = loader.load();
            StudentViewController studentController = (StudentViewController) loader.getController();
            Authenticator.getInstance().registerScene(studentController);
            
            Scene scene = new Scene(root);
            
            Stage stage = new Stage();
            stage.setTitle("Student");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(StudentWinGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
                    
    }

}
