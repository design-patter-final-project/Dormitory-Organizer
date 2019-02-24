package designpatternproject;

import designpatternproject.adapters.DurationAdapter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import designpatternproject.database.DurationDaoImpl;
import designpatternproject.database.StudentDaoImpl;
import designpatternproject.database.model.Duration;
import designpatternproject.database.model.Student;
import designpatternproject.iterator.DurationRepository;
import designpatternproject.iterator.Iiterator;
import designpatternproject.mediator.Component;
import designpatternproject.mediator.Mediator;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class AdminControlViewController implements Initializable, Component {
    
    @FXML
    private AnchorPane winAnchorPane;

    @FXML
    private JFXTabPane winTabPane;

    @FXML
    private JFXTreeTableView<Student> studentTableView;

    @FXML
    private TreeTableColumn<Student, String> idColumn;

    @FXML
    private TreeTableColumn<Student, String> nameColumn;

    @FXML
    private TreeTableColumn<Student, String> departmentColumn;

    @FXML
    private TreeTableColumn<Student, String> endColumn;
    
    @FXML
    private JFXTextField idTextField;

    @FXML
    private JFXTextField nameTextField;

    @FXML
    private JFXPasswordField passwordTextField;

    @FXML
    private JFXComboBox<String> departmentComboBox;

    @FXML
    private JFXDatePicker fromDatePicker;

    @FXML
    private JFXDatePicker toDatePicker;

    @FXML
    private JFXButton addButton;
    
    @FXML
    private Label idLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label departmentLable;
    
    @FXML
    private VBox dormStatusVBox;
                    
    // Mediator for comunication between Student and Login windows
    private Mediator mediator;
           
    ObservableList<Student> student = FXCollections.observableArrayList();
    StudentDaoImpl studentDaoImpl = StudentDaoImpl.getInstance();
    DurationDaoImpl durationDaoImpl = DurationDaoImpl.getInstance();
    TreeItem<Student> root;
    
    // Student selected to edit
    private Student selectedStudent;
    
    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }
    
    @Override
    public String getName() {
        return "AdminController";
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        List<Student> students = studentDaoImpl.getAll();
        System.out.println(students.size());
        
        students.forEach(s -> {
            student.add(s);
        });
        
        idColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Student, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Student, String> param) {
                return param.getValue().getValue().getRowId();
            }
        });

        nameColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Student, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Student, String> param) {
                return param.getValue().getValue().getRowName();
            }
        });

        departmentColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Student, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Student, String> param) {
                return param.getValue().getValue().getRowDepartment();
            }
        });

        endColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Student, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Student, String> param) {
                return param.getValue().getValue().getRowDepartment();
            }
        });
        
        endColumn.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        endColumn.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<Student, String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<Student, String> event) {
                
            }
        
        });
        
        root = new RecursiveTreeItem<Student>(student, RecursiveTreeObject::getChildren);
        studentTableView.getColumns().setAll(idColumn, nameColumn, departmentColumn, endColumn);
        studentTableView.setRoot(root);
        studentTableView.setShowRoot(false);
        studentTableView.setEditable(true);
        
        //
        // Student selected from student list
        //
        studentTableView.getSelectionModel().selectedItemProperty()
            .addListener((obs, oldSelection, newSelection) ->  {    
                selectedStudent = newSelection.getValue();
                updateStudentView();
                winTabPane.getSelectionModel().select(2); 
        });
        
        departmentComboBox.getItems().add("Bio-Medical");
        departmentComboBox.getItems().add("Checmical");
        departmentComboBox.getItems().add("Electrical");
        departmentComboBox.getItems().add("Mechanical");
        departmentComboBox.getItems().add("Software");
        departmentComboBox.getItems().add("It");
        
        addButton.setOnAction(e -> add());
        
    }
    
    public void add() {
        
        String id = idTextField.getText();
        String name = nameTextField.getText();
        String password = passwordTextField.getText();
        String department = departmentComboBox.getValue();
        int year = fromDatePicker.getValue().getYear();
        int duration_diff = toDatePicker.getValue().getYear() - fromDatePicker.getValue().getYear() + 1;
           
        Student student = new Student(id, name, password, department);
        
        ObservableList<Duration> durations = DurationAdapter.getDurations(duration_diff, id, year);
        
        student.setDuration(durations);
        
        int rowAffected = studentDaoImpl.addStudent(student);
    
        JFXSnackbar bar = new JFXSnackbar(winAnchorPane);
       
        if (rowAffected > 0){ // successful
            Label l = new Label("Successfully added a student");
            bar.enqueue(new SnackbarEvent(l));
            updateStudentList();
            emptyForm();
            winTabPane.getSelectionModel().select(0);
        } else {
            Label l = new Label("Error: Unable to add student!");
            bar.enqueue(new SnackbarEvent(l));
        }
    }
    
    public void updateStudentList() {
        root.getChildren().clear();
        List<Student> students = studentDaoImpl.getAll();
        students.forEach(s -> {
            student.add(s);
        });
        root.getChildren().add(new RecursiveTreeItem<Student>(student, RecursiveTreeObject::getChildren));
    }

    public void emptyForm() {
        idTextField.clear();
        nameTextField.clear();
        passwordTextField.clear();
        departmentComboBox.getItems().clear();
        fromDatePicker.getEditor().clear();
        toDatePicker.getEditor().clear();
    }
    
    private HBox createDormStatusHBox(Duration duration) {
        
        HBox hBox = new HBox();
        Label yearLabel = new Label(Integer.toString(duration.getYear()));
        yearLabel.setPrefWidth(54);
        yearLabel.setPadding(new Insets(10, 0, 10, 0));
        Button button = null;
        String statusDesc;
        
        switch(duration.getStatus()){
            case 0:
                statusDesc = "Not Assigned";
                button = new Button("Activate");
                break;
            case 1:
                statusDesc = "Not Confirmed";
                button = new Button("Deactivate");
                break;
            case 2:
                statusDesc = "Confirmed";
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
        
        JFXTextField dormTextField = new JFXTextField();
        dormTextField.setPromptText("Dorm Number");
        dormTextField.setPrefHeight(26.0);
        dormTextField.setPrefWidth(100);  
        dormTextField.setPadding(new Insets(5, 0, 0, 0));
        HBox.setMargin(dormTextField, new Insets(0, 30, 0, 0));
        
        if(duration.getStatus() == 0) {
            hBox.getChildren().add(dormTextField);
        } else {
            Label dormLabel = new Label(duration.getDorm());
            dormLabel.setPadding(new Insets(10, 0, 10, 0));
            dormLabel.setPrefWidth(130);
            hBox.getChildren().add(dormLabel);
        }
        
        if(button != null) {
            button.setPadding(new Insets(4, 5, 4, 5));
            button.setStyle("-fx-background-color: #3ad; -fx-text-fill: #FFFFFF;");
            
            String buttonText = button.getText();
            button.setOnAction((even) -> {
                
                JFXSnackbar bar = new JFXSnackbar(winAnchorPane);
                
                if(buttonText.equals("Activate")){ // Activate year duration
                    
                    duration.setStatus(1);
                    duration.setDorm(dormTextField.getText());
                   
                    if(durationDaoImpl.updateDuration(duration) > 0){ // updated sucessfully
                        updateStudentView();
                        Label l = new Label("Dorm is assigned successfully!");
                        bar.enqueue(new SnackbarEvent(l));
                    } else { // faild to update
                        Label l = new Label("Error: assigning dorm!");
                        bar.enqueue(new SnackbarEvent(l));
                    }
                    
                } else { // Stop activation
                    
                    duration.setStatus(0);
                    duration.setDorm(null);
                    if(durationDaoImpl.updateDuration(duration) > 0){ // updated sucessfully
                        updateStudentView();
                        Label l = new Label("Successfully remove activation!");
                        bar.enqueue(new SnackbarEvent(l));
                    } else { // faild to update
                        Label l = new Label("Error: removing activation!");
                        bar.enqueue(new SnackbarEvent(l));
                    }
                }
                
            });
            
            hBox.getChildren().add(button);
        }
        return hBox;
    }
    
    public void updateStudentView() {
        idLabel.setText(selectedStudent.getId());
        nameLabel.setText(selectedStudent.getName());
        passwordLabel.setText(selectedStudent.getPassword());
        departmentLable.setText(selectedStudent.getDepartment());
        updateDurationView(selectedStudent.getDuration());
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
