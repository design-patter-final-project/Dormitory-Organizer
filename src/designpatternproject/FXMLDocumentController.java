package designpatternproject;

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
import designpatternproject.database.StudentDaoImpl;
import designpatternproject.database.model.Duration;
import designpatternproject.database.model.Student;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class FXMLDocumentController implements Initializable {
    
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
                    
           
    ObservableList<Student> student = FXCollections.observableArrayList();
    StudentDaoImpl studentDaoImpl = StudentDaoImpl.getInstance();
    TreeItem<Student> root;
    
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
        
        studentTableView.getSelectionModel().selectedItemProperty()
            .addListener((obs, oldSelection, newSelection) ->  {
                System.out.println(newSelection.getValue().getName());
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
}
