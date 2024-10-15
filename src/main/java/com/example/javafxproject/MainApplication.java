package com.example.javafxproject;
//interface

/**
 *
 * @author RoryHarrington
 * R00233458
 * rory.harrington@mycit.ie
 */

//import packages
import javafx.application.Application;



import javafx.application.Platform;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Optional;
import javafx.scene.control.TabPane;
import java.sql.SQLException;
public class MainApplication extends Application {// extends application part of the javafx library          view to my program

    public Controller control;//acts as a controller/remote for the project communicates with the model


    protected TextArea area = new TextArea();
    protected TextArea studentDisplayinfomation = new TextArea();

    public static void main(String[] args) {
        launch(args);
    }






    @Override
    public void start(Stage primaryStage) throws FileNotFoundException, SQLException {
        Controller.StudentDatabase dataBase = new Controller.StudentDatabase("jdbc:mysql://localhost:3306/StudentRecordsMTU", "root", "Ody55ey101");
        primaryStage.setTitle("MTU Student management System");
        control = new Controller(this);

        control.loadStudentData();




        Label NameLbl = new Label("Enter Name ");
        TextField NameFld = new TextField();

        //Creating an image
        Image image = new Image(new FileInputStream("target\\MTU_size.png"));//this image may fail due to the location of my workspace in my c drive may differ for yours

        //Setting the image to view 1
        ImageView imageView1 = new ImageView(image);
        //Setting the position of the image
        imageView1.setX(10);
        imageView1.setY(10);



        //setting the fit height and width of the image view
        imageView1.setFitHeight(150);
        imageView1.setFitWidth(150);



        //Setting the preserve ratio of the image view
        imageView1.setPreserveRatio(true);

        // text  for student number
        Text StudentNumberPrompt = new Text("Enter StudentID");

        //text  for DOB
        Text EnterDoB = new Text(" Date of birth");
        // name textfield
        TextField nameFLd = new TextField();

        TextField StudentID = new TextField();
        StudentID.setPromptText("Enter Student ID");

        nameFLd.setPromptText("Enter Name");

        TextField DateOfBirth = new TextField();
        DateOfBirth.setPromptText("Enter Date of birth");
        DatePicker Dob = new DatePicker();

        LocalDate minDate = LocalDate.of(1958, 12, 31);//minimum date
        LocalDate maxDate = LocalDate.of(2006,12,31);// max date to set
        DatePicker datePicker = Dob;//date picker for the DOB
        datePicker.setDayCellFactory(d ->
                new DateCell() {
                    @Override public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isAfter(maxDate) || item.isBefore(minDate));
                    }});
        TextField studentYear = new TextField();
        Text studentYeartxt = new Text("Enter the current year of study ");

        // puts the students in the area within the window
        area.setEditable(false);
        area.setPrefWidth(350);
        area.setPrefHeight(150);

//buttons for functianality for Students
        Button helpScreen = new Button("Help");
        Button addStudentBtn = new Button("Add to list ");
        Button RemoveStudentBtn = new Button("Remove list");
        Button Listbtn = new Button("List");
        Button Loadbtn = new Button("Load");
        Button Savebtn1 = new Button("Save");
        Button Exitbtn = new Button("Exit");
        Button Clearbtn = new Button("Clear");
        Button AddStudentDB = new Button("Add to Database ");
        Button RemoveStdnfromDB = new Button("Remove DataBase");
        Button MemoryLeak = new Button("Memory Leak");
        HBox btnFunc = new HBox(addStudentBtn,AddStudentDB, RemoveStudentBtn, Listbtn,RemoveStdnfromDB);
        HBox btnFunc2 = new HBox(Loadbtn, Savebtn1, Exitbtn, helpScreen,MemoryLeak);



        //--------------layout for students records-------------------//
        GridPane gridPane = new GridPane();
        gridPane.getStylesheets().add(("Style.css"));// style sheet used
        gridPane.setMinSize(400, 400);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        GridPane.setConstraints(addStudentBtn, 0, 3);
        GridPane.setConstraints(RemoveStudentBtn, 1, 3);

        GridPane.setConstraints(Listbtn, 2, 3);
        GridPane.setConstraints(Savebtn1, 0, 6);
        GridPane.setConstraints(Loadbtn, 1, 6);
        GridPane.setConstraints(Exitbtn, 2, 6);
        GridPane.setConstraints(helpScreen, 3, 6);

        gridPane.setVgap(8);
        gridPane.setHgap(10);

        gridPane.add(NameLbl, 0, 0);
        gridPane.add(nameFLd, 1, 0);

        gridPane.add(StudentNumberPrompt, 0, 1);
        gridPane.add(StudentID, 1, 1);
        gridPane.add(EnterDoB, 0, 2);

        gridPane.add(imageView1, 2, 1);
        gridPane.add(Dob, 1, 2);
        gridPane.add(studentYeartxt, 0, 3);
        gridPane.add(studentYear, 1, 3);
        gridPane.add(btnFunc, 0, 4);

        gridPane.add(area, 0, 5, 3, 1);
        gridPane.add(btnFunc2, 2, 6);



        //---------------------Modules layout---------------------//


        ComboBox<String> StudentcomboList = new ComboBox<>(FXCollections.observableArrayList(control.GetstudentID()));
        StudentcomboList.setPromptText("Select a Student to Add Module");



        Label ModuleLbl = new Label("Name of the Module: ");
        Label ModuleGradelbl = new Label("Grade of the Module: ");
        TextField ModuleNameFLd = new TextField();
        TextField ModuleGradeFld = new TextField();


        TextField ModyearFld = new TextField();
        Label ModYearlbl = new Label("Enter semester ");
        TextField modCode = new TextField();
        Label modCodelbl = new Label("Enter module Code ");
        Button addModuleBtn = new Button("Add");
        Button AddtoDBmod = new Button("Add to database");
        Button removeModDB = new Button("Remove Module from Database");
        HBox btnFunc3 = new HBox(Exitbtn,addModuleBtn,Clearbtn,AddtoDBmod,removeModDB);

        Clearbtn.setOnAction(e -> {// clears fields above in the display
            ModuleGradeFld.clear();
            ModuleNameFLd.clear();
        });

        ToggleGroup group = new ToggleGroup();//makes the radio buttons toggleable
        RadioButton GradeButton = new RadioButton("Order by grade");
        RadioButton AlphaButton= new RadioButton("Order Alphabetical");
        GradeButton.setToggleGroup(group);
        AlphaButton.setToggleGroup(group);
        AlphaButton.setSelected(true);//set to be ordered by alphabetical order
        HBox ToggleBut = new HBox(GradeButton,AlphaButton);


        GridPane ModulesPane = new GridPane();
        ModulesPane.getStylesheets().add(("Style.css"));
        ModulesPane.setMinSize(400, 400);
        ModulesPane.setPadding(new Insets(10, 10, 10, 10));
        ModulesPane.setConstraints(addModuleBtn, 0, 3);
        ModulesPane.setConstraints(RemoveStudentBtn, 1, 3);

        ModulesPane.setVgap(8);
        ModulesPane.setHgap(10);
        ModulesPane.add(StudentcomboList, 0,1);
        ModulesPane.add(ModuleLbl, 1, 2);
        ModulesPane.add(ModuleNameFLd, 2, 2);
        ModulesPane.add(ModuleGradelbl, 1, 3);
        ModulesPane.add(ModuleGradeFld, 2, 3);
        ModulesPane.add(ModYearlbl, 1, 4);
        ModulesPane.add(ModyearFld, 2, 4);
        ModulesPane.add(modCodelbl, 1, 5);
        ModulesPane.add(modCode, 2, 5);
        ModulesPane.add(btnFunc3, 2, 6);

//-------------------records Layout------------------//
        GridPane RecordPane = new GridPane();
        RecordPane.getStylesheets().add(("Style.css"));
        RecordPane.setMinSize(400, 400);
        RecordPane.setPadding(new Insets(10, 10, 10, 10));


        RecordPane.setVgap(8);
        RecordPane.setHgap(10);
        ComboBox<String> studentComboList1 = new ComboBox<>(FXCollections.observableArrayList(control.GetstudentID()));
         RecordPane.add(studentComboList1, 0,1);
         RecordPane.add(ToggleBut,0,2);
        area.setEditable(false);
        area.setPrefWidth(350);//attributes for the  area
        area.setPrefHeight(150);
        // puts the students in the area within the window
        RecordPane.add(studentDisplayinfomation, 0, 3);
        Button saveBtn2 = new Button("Save");

        saveBtn2.setOnAction(p -> control.saveStudentInfo());
        RecordPane.add(saveBtn2,0, 4);


        TabPane tabpane = new TabPane();
        Tab tab1 = new Tab("Students");
        tab1.setContent(gridPane);

        Tab tab2 = new Tab("Modules");
        tab2.setContent(ModulesPane);

        Tab tab3 = new Tab("Records");
        tab3.setContent(RecordPane);


        tabpane.getTabs().addAll(tab1,tab2,tab3);

        //stops the user from closing the tabs on accident
        tab1.setClosable(false);
        tab2.setClosable(false);
        tab3.setClosable(false);

        Scene scene1 = new Scene(tabpane);



        primaryStage.setScene(scene1);
        primaryStage.show();

        helpScreen.setOnAction(e -> helpscreen.show());



        AlphaButton.setOnAction(f-> control.OrderData());
        GradeButton.setOnAction(t -> control.Ordergrade());
        addStudentBtn.setOnAction(e -> {//add button , adds the student to the database
            if (!nameFLd.getText().matches("[a-zA-Z\\s']+")) {//checks if its right name
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Error");
                alert1.setHeaderText("error has occurred");
                alert1.setContentText("Name must only contain letters");

                alert1.showAndWait();

            } else {// puts the value in the database
                control.AddStudent(nameFLd.getText(), StudentID.getText(), String.valueOf(Dob.getValue()),Integer.parseInt(studentYear.getText()));
            }
        });


        addModuleBtn.setOnAction(e -> {//add button , adds the student to the database
            if(!ModuleNameFLd.getText().matches("[a-zA-Z\\s']+") || ModuleGradeFld.getText().matches("[a-zA-Z\\s']+")){//regex if its not a name
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Error");
                alert1.setHeaderText("error has occurred");
                alert1.setContentText("Module must only contain letters");

                alert1.showAndWait();
                }
            else {// checks if the statement is true
                control.CreateNewModule(ModuleNameFLd.getText(),modCode.getText(),Integer.parseInt(ModyearFld.getText()), Integer.parseInt(ModuleGradeFld.getText()));
            }
        });
        StudentcomboList.setOnAction(e->control.FindName(StudentcomboList.getValue()));
//sets and displays the info in the dropdown boxes
        studentComboList1.setOnAction(e -> control.SetStudentRecord(studentComboList1.getValue()));

        Exitbtn.setOnAction(e -> {//on exit ,checks the user
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation ");
            alert.setHeaderText("Exit Application.");
            alert.setContentText("Do you want to Exit the Application? clicking ok will save your progress");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                //if the user clicks OK the program terminates
                control.saveStudentInfo();
                Platform.exit();
            } else {

                // ... user chose CANCEL or closed the dialog
            }
        });

        Listbtn.setOnAction(e -> area.setText(control.UpdateStudentList()));
        RemoveStudentBtn.setOnAction(g -> control.RemoveStudent(StudentID.getText()));
        Loadbtn.setOnAction(k -> control.loadStudentData());
        Savebtn1.setOnAction(l -> control.saveStudentInfo());
        AddStudentDB.setOnAction(e -> dataBase.addStudentToDatabase(nameFLd.getText(), StudentID.getText(), String.valueOf(Dob.getValue()), studentYear.getText()));
        removeModDB.setOnAction(e -> dataBase.RemoveModuleDB(modCode.getText()));
        RemoveStdnfromDB.setOnAction(e-> dataBase.RemoveStudentDB(StudentID.getText()));
        AddtoDBmod.setOnAction(e -> {
            try {
                dataBase.insertModule(modCode.getText(),ModuleNameFLd.getText(),Integer.parseInt(ModyearFld.getText()), Float.parseFloat(ModuleGradeFld.getText()));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        MemoryLeak.setOnAction(r-> dataBase.addStudentWithMemoryLeak(nameFLd.getText(), StudentID.getText(), String.valueOf(Dob.getValue()), Integer.parseInt(studentYear.getText())));
    }
}
