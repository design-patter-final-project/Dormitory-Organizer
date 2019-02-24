package designpatternproject;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import designpatternproject.database.StudentDaoImpl;
import designpatternproject.database.model.Student;
import designpatternproject.mediator.Component;
import designpatternproject.mediator.Authenticator;
import designpatternproject.mediator.Mediator;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoginIntroViewController implements Initializable, Component {

    @FXML
    private JFXTextField idTextField;

    @FXML
    private JFXPasswordField passwordTextField;

    @FXML
    private JFXButton loginButton;
    
    // Mediator for communication among admin and student views
    Mediator mediator;
    
    StudentDaoImpl studentDaoImpl = StudentDaoImpl.getInstance();
    Authenticator auth = Authenticator.getInstance();
    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }
    
    @Override
    public String getName() {
        return "LoginController";
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loginButton.setOnAction(e -> {
            
            String id = idTextField.getText().toString().trim();
            String password = passwordTextField.getText().toString().trim();
            
            if(id.equals("Admin") &&  password.equals("adminpassword")) { // user is admin
                try {
                    
                    Stage loginStage = (Stage) ((Node)e.getSource()).getScene().getWindow();
                    loginStage.close();
                    
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminControlView.fxml"));
                    Parent root = loader.load();       
                    AdminControlViewController adminController = (AdminControlViewController) loader.getController();
                    auth.registerScene(adminController);
                    
                    Scene scene = new Scene(root);
                    
                    Stage stage = new Stage();
                    stage.setTitle("Login");
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(LoginIntroViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            Student student = studentDaoImpl.GetUser(id, password);
            
            if(student != null) { // matching student has found for id & username
                try {
                    System.out.println(student.getId());
                    auth.logInStudent(student);
                    Stage loginStage = (Stage) ((Node)e.getSource()).getScene().getWindow();
                    loginStage.close();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentView.fxml"));
                    Parent root = loader.load();       
                    StudentViewController studentController = (StudentViewController) loader.getController();
                    auth.registerScene(studentController);
                    
                    Scene scene = new Scene(root);
                    
                    Stage stage = new Stage();
                    stage.setTitle("Student");
                    stage.setScene(scene);
                    stage.show();
                    
                } catch (IOException ex) {
                    Logger.getLogger(LoginIntroViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }    
    
}
