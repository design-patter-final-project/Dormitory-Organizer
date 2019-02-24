/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package designpatternproject;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import designpatternproject.database.StudentDaoImpl;
import designpatternproject.database.model.Student;
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

/**
 * FXML Controller class
 *
 * @author nbody
 */
public class LoginIntroViewController implements Initializable {

    @FXML
    private JFXTextField idTextField;

    @FXML
    private JFXPasswordField passwordTextField;

    @FXML
    private JFXButton loginButton;
    
    StudentDaoImpl studentDaoImpl = StudentDaoImpl.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loginButton.setOnAction(e -> {
            
            String id = idTextField.getText().toString().trim();
            String password = passwordTextField.getText().toString().trim();
            
            if(id.equals("Admin") &&  password.equals("adminpassword")) { // user is admin
                try {
                    
                    Stage loginStage = (Stage) ((Node)e.getSource()).getScene().getWindow();
                    loginStage.close();
                    
                    Parent root = FXMLLoader.load(getClass().getResource("AdminControlView.fxml"));        
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
            
            System.out.println(student.getId());
        });
    }    
    
}
