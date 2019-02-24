package designpatternproject;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import designpatternproject.database.DurationDaoImpl;
import designpatternproject.database.model.Duration;
import designpatternproject.database.model.Student;
import designpatternproject.iterator.DurationRepository;
import designpatternproject.iterator.Iiterator;
import designpatternproject.mediator.Authenticator;
import designpatternproject.mediator.Component;
import designpatternproject.mediator.Mediator;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StudentViewController implements Initializable, Component {

    @FXML
    private JFXTabPane winTabPane;

    @FXML
    private Label idLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label departmentLable;
    
    @FXML
    private VBox dormStatusVBox;
    
    private Mediator mediator;
    
    Authenticator auth = Authenticator.getInstance();
    DurationDaoImpl durationDaoImpl = DurationDaoImpl.getInstance();
    
    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }
    
    @Override
    public String getName() {
        return "StudentController";
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        updateStudentView();
    } 
    
    private HBox createDormStatusHBox(Duration duration) {
        
        HBox hBox = new HBox();
        Label yearLabel = new Label(Integer.toString(duration.getYear()));
        yearLabel.setPrefWidth(54);
        yearLabel.setPadding(new Insets(10, 0, 10, 0));
        Button button = null;
        String statusDesc;
        
        Label dormLabel = new Label(duration.getDorm());
        dormLabel.setPadding(new Insets(10, 0, 10, 0));
        dormLabel.setPrefWidth(130);
        
        switch(duration.getStatus()){
            case 0:
                statusDesc = "Not Assigned";
                dormLabel.setText("Not Assigned");
                break;
            case 1:
                statusDesc = "To Be Confirmed";
                button = new Button("Confirm");
                break;
            case 2:
                statusDesc = "Assigned";
                break;
            default:
                statusDesc = "Status default";
                break;      
        }
        
        Label statusLabel = new Label(statusDesc);
        statusLabel.setPadding(new Insets(10, 0, 10, 0));
        statusLabel.setPrefWidth(130);
        
        hBox.getChildren().add(yearLabel);
        hBox.getChildren().add(statusLabel);
        hBox.getChildren().add(dormLabel);
        
        if(button != null) {
            button.setPadding(new Insets(4, 5, 4, 5));
            button.setStyle("-fx-background-color: #3ad; -fx-text-fill: #FFFFFF;");
            
            String buttonText = button.getText();
            button.setOnAction((even) -> {
                
                duration.setStatus(2);

                durationDaoImpl.updateDuration(duration);
                updateStudentView();
            });
            
            hBox.getChildren().add(button);
        }
        return hBox;
    }
    
    public void updateStudentView() {
        Student loggedStudent = auth.getLoggedStudent();
        idLabel.setText(loggedStudent.getId());
        nameLabel.setText(loggedStudent.getName());
        departmentLable.setText(loggedStudent.getDepartment());
        updateDurationView(loggedStudent.getDuration());
    }
    
    public void updateDurationView(ObservableList<Duration> duration) {
        DurationRepository dRepo = new DurationRepository(duration);
        dormStatusVBox.getChildren().remove(1, dormStatusVBox.getChildren().size());
        
        for(Iiterator iterator = dRepo.getIterator(); iterator.hasNext();){
            Duration iDuration = (Duration) iterator.next();
            dormStatusVBox.getChildren().add(createDormStatusHBox(iDuration));
        }
    }
    
}
