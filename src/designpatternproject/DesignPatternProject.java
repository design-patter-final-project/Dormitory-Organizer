package designpatternproject;

import designpatternproject.mediator.Authenticator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DesignPatternProject extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginIntroView.fxml"));
        Parent root = loader.load();       
        LoginIntroViewController loginController = loader.getController();
        Authenticator.getInstance().registerScene(loginController);
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
