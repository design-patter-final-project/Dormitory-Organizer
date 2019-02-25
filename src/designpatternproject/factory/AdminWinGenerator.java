package designpatternproject.factory;

import designpatternproject.AdminControlViewController;
import designpatternproject.DesignPatternProject;
import designpatternproject.mediator.Authenticator;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminWinGenerator implements WinGenerator {

    @Override
    public void generate() {
        try {
            String path = "/home/nbody/NetBeansProjects/DormitoryOrganizer/src/designpatternproject/fxml/AdminControlView.fxml";
            FXMLLoader loader = new FXMLLoader(Paths.get(path).toUri().toURL());
            Parent root = loader.load();
            AdminControlViewController adminController = (AdminControlViewController) loader.getController();
            Authenticator.getInstance().registerScene(adminController);
            
            Scene scene = new Scene(root);
            
            Stage stage = new Stage();
            stage.setTitle("Admin");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AdminWinGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
