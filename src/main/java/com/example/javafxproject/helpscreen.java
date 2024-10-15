package com.example.javafxproject;

/**
 * The helpscreen class displays a window with information and instructions for using the application.
 * It contains a method for showing the window.
 * Author: Rory Harrington (R00233458)
 * Email: rory.harrington@mycit.ie
 */

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class helpscreen {
    public helpscreen() {
    }

    public static void show() {// method for showing the help window
        Stage window = new Stage();
        VBox vbox = new VBox(30.0);
        vbox.getStylesheets().add("Style.css");
        window.setTitle("Help/Info");

        // Create a close button that closes the window when clicked
        Button close = new Button("Close");
        close.setOnAction((e) -> {
            window.close();
        });

        // Create a label with information and instructions for using the application
        Label ShowUseriNFO = new Label("You must type in a valid Name (no numbers or special characters )\n " +
                "You can choose a date of birth minimum 2006 , max date is 1958\n " +
                "To Delete a student from the list , Enter the student number and click Remove, then click list to see your changes\n " +
                "To ADD a user Enter in the required details , then click Add , then click list , the program will display the newly added user \n " +
                "To Load file click update\n " +
                "To Save simply click save\n " +
                "To EXIT click exit, the program saves on exit");

        // Set padding for the VBox
        vbox.setPadding(new Insets(30.0, 30.0, 30.0, 30.0));

        // Add the close button and information label to the VBox
        vbox.getChildren().addAll(new Node[]{close, ShowUseriNFO});

        // Create a new scene with the VBox as its root and set its size
        Scene scene = new Scene(vbox, 900.0, 400.0);

        // Set the scene for the window and show it
        window.setScene(scene);
        window.show();
    }
}