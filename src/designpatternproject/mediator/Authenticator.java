package designpatternproject.mediator;

import designpatternproject.AdminControlViewController;
import designpatternproject.LoginIntroViewController;
import designpatternproject.StudentViewController;
import designpatternproject.database.model.Student;
import javafx.scene.Scene;

public class Authenticator implements Mediator {
    
    private static Authenticator logger;
    
    private AdminControlViewController adminController;
    private LoginIntroViewController loginController;  
    private StudentViewController studentController;
    private Scene studentScene;
    private Student loggedStudent;
    
    private Authenticator() {}
    
    public static Authenticator getInstance() {
        if(logger == null){
            logger = new Authenticator();
        }
        return logger;
    } 
    
    @Override
    public void registerScene(Component sceneController) {
      sceneController.setMediator(this);
      switch(sceneController.getName()){
          case "AdminController":
              adminController = (AdminControlViewController) sceneController;
              break;
          case "LoginController":
              loginController = (LoginIntroViewController) sceneController;
              break;
          case "StudentController":
              studentController = (StudentViewController) sceneController;
              break;
      }
    }

    @Override
    public void logInStudent(Student student) {
        loggedStudent = student;
    }
    
    @Override
    public void logOutStudent() {
        loggedStudent = null;
    }
    
    @Override
    public Student getLoggedStudent() {
        return loggedStudent;
    }

}
