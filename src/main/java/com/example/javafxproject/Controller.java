package com.example.javafxproject;



/**
 *
 * @author RoryHarrington
 * R00233458
 * rory.harrington@mycit.ie
 */



import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.*;
import java.util.*;
import java.io.Serializable;
import java.sql.*;
public class Controller implements Serializable { //controller

    private  MainApplication View;
    public Controller(MainApplication main){
        View =main;
    }
    static Student[] studentList = new Student[0];



    private Student selectedStudent;
    private Student ViewStudentRecord;




    // Define the filename where the student records will be saved
    String filename = "Student_Records.txt";


    public static class Database {
        /**
         * @param Connection is where we are getting the connection to the database
         * @param Database is a class that is getting the Driver-manger
         * @param url is where you will get the url to the database
         * @param user is getting the username
         * @param password is where we are getting the password for the database
         */
        private Connection connection;

        public Database(String url, String user, String password) throws SQLException {
            connection = DriverManager.getConnection(url, user, password);
        }
    }

    // Method to save the student information to a file
    public void saveStudentInfo() {
        try {
            // Create a FileOutputStream and ObjectOutputStream to write the data to a file
            FileOutputStream fileOutputStream = new FileOutputStream(filename);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            // Create a new ArrayList to store the students from the studentList array
            ArrayList<Student> saveStudentList = new ArrayList<>();

            // Loop through the studentList array and add each student to the saveStudentList ArrayList
            for (int i = 0; i < studentList.length; i++) {
                saveStudentList.add(studentList[i]);
            }

            // Write the saveStudentList ArrayList to the file using the ObjectOutputStream
            objectOutputStream.writeObject(saveStudentList);

            // Close the ObjectOutputStream and FileOutputStream
            objectOutputStream.close();
            fileOutputStream.close();
            System.out.println("Written to file.");
        } catch(IOException ex) {
            // If an IOException is caught, print an error message
            System.out.println("IOException is caught");
        }
    }

public void loadStudentData() {
    try (FileInputStream file = new FileInputStream(filename);
         ObjectInputStream in = new ObjectInputStream(file)) {
        List<Student> loadStudents = (List<Student>) in.readObject();
        studentList = loadStudents.toArray(new Student[loadStudents.size()]);
        View.area.setText(UpdateStudentList());
    } catch (IOException | ClassNotFoundException ex) {
        // Handle the exception appropriately (e.g., display an error message or log it)
        ex.printStackTrace();
    }
}

    public String UpdateStudentList() {// updates the studentList
        String res = "";
        for (int i = 0; i < studentList.length; i++) {
            if (i != studentList.length - 1) {
                res += studentList[i].StudentArrayList() + "\n";
            } else res += studentList[i].StudentArrayList();
        }
        return res;
    }


    public Student[] addStudent(Student[] oldArray, Student newStudent) {
        Student[] newArray = new Student[oldArray.length + 1];
        for (int i = 0; i < oldArray.length; i++) {
            newArray[i] = oldArray[i];
        }
        newArray[oldArray.length] = newStudent;
        return newArray;
    }


    public Student[] RemoveStudent(Student[] oldArray, int position) {//removes the student from the array
        Student[] newArray = new Student[oldArray.length - 1];
        for (int i = 0; i < newArray.length; i++) {//takes the new array
            if (i < position) {
                newArray[i] = oldArray[i];
            } else {
                newArray[i] = oldArray[i + 1];
            }
        }
        return newArray;
    }

    public void RemoveStudent(String ID) {
        if (ID.length() > 0) {
            for (int i = 0; i < studentList.length; i++) {
                if (Objects.equals(ID, studentList[i].GetStudentID())) {
                    studentList = RemoveStudent(studentList, i);
                }
            }
        } else System.out.println("The student ID must be provided");
    }

    public void AddStudent(String name, String ID, String DOB,int semesterYear) {//adds a student via method
        if (name.length() > 0 && ID.length() > 0 && DOB.length() > 0) {
            if (ListID(ID) == null) {// if its empty ,creates a student
                Student student = new Student(name, ID, DOB,semesterYear);
                studentList = addStudent(studentList, student);

            }

        } else System.out.println("All Fields need to be entered");
    }

        // Here is the main point to the Student Database
        public static class StudentDatabase {
            /**
             * @param StudentDatabase is gettign the url the username and the password and it will also make sure that the conncection to the database is closed too
             */
            private Connection connection;

            String url = "jdbc:mysql://localhost:3306/StudentRecordsMTU";// url link
            String username = "root"; //username
            String password = "Ody55ey101";// password

            public StudentDatabase(String url, String user, String password) throws SQLException {
                connection = DriverManager.getConnection(url, user, password);
            }



            /**

             Adds a new student record to the database with the specified name, ID, date of birth, and semester.
             @param name The name of the student to be added.
             @param id The ID of the student to be added.
             @param dob The date of birth of the student to be added.
            **/
             public void addStudentToDatabase(String name, String id, String dob, String semester) {
                if (name.length() > 0 && id.length() > 0 && dob.length() > 0) {
                    if (ListID(id) == null) {
                        try {
                            PreparedStatement statement = connection.prepareStatement(
                                    "INSERT INTO student (studentName, dateofbirth, studentId, year) VALUES (?, ?, ?, ?)"
                            );
                            statement.setString(1, name);
                            statement.setString(2, dob);
                            statement.setString(3, id);
                            statement.setString(4, semester);
                            statement.executeUpdate();
                            statement.close();
                        } catch (SQLException ex) {
                            System.out.println("SQLException caught: " + ex.getMessage());
                        }
                    }
                } else {
                    System.out.println("Please enter all required fields.");
                }
            }

            /**
             * @param ID Just getting the valid id from the user to remove them from the database
             */
            public void RemoveStudentDB(String ID) {
                if (ID.length() > 0) {
                    String url = "jdbc:mysql://localhost:3306/StudentRecordsMTU";
                    String username = "root";
                    String password = "Ody55ey101";
                    try (Connection connection = DriverManager.getConnection(url, username, password)) {
                        String sql = "DELETE FROM student WHERE studentId  = ?";
                        PreparedStatement stmt = connection.prepareStatement(sql);
                        stmt.setString(1, ID);
                        stmt.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("The student ID must be provided");
                }
            }

            /**

             Inserts a new module into the database.

             @param moduleCode the code of the new module

             @param moduleName the name of the new module

             @param semester the semester the module is being offered

             @param grade the grade achieved in the module

             @throws SQLException if there is an error with the database connection
             */


            public void insertModule(String moduleCode, String moduleName, int semester, float grade) throws SQLException {
                String query = "INSERT INTO Module (courseCode, moduleName, year, grade) VALUES (?, ?, ?, ?)";

                try (PreparedStatement stmt = connection.prepareStatement(query)) {
                    stmt.setString(1, moduleCode);
                    stmt.setString(2, moduleName);
                    stmt.setInt(3, semester);
                    stmt.setFloat(4, grade);
                    stmt.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("An error occurred while inserting module: " + e.getMessage());
                }
            }
            /**

             Removes a module from the database.
             @param ModuleCode the code of the module to be removed
             */
            public void RemoveModuleDB(String ModuleCode) {
                if (ModuleCode.length() > 0) {

                    try (Connection connection1 = DriverManager.getConnection(url, username, password)) {
                        String sqlQuery = "DELETE FROM Module WHERE courseCode = ?";
                        PreparedStatement statement = connection1.prepareStatement(sqlQuery);
                        statement.setString(1, ModuleCode);
                        int numRowsDeleted = statement.executeUpdate();
                        if (numRowsDeleted > 0) {
                            System.out.println("Module record with code " + ModuleCode + " deleted successfully.");
                        } else {
                            System.out.println("No module record with code " + ModuleCode + " found.");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("The module code must be provided");
                }
            }
            /**
             * Adds a new student to the database with the specified information.
             * If the name "LeakyStudent" is entered, the method will simulate a memory leak by creating
             * new Student objects in an infinite loop until an OutOfMemoryError is thrown.
             *
             * @param name     the name of the student
             * @param id       the ID of the student
             * @param dob      the date of birth of the student
             * @param semester the semester the student is currently in
             * @return
             */

            public void addStudentWithMemoryLeak(String name, String id, String dob, int semester) {
                if (name.length() > 0 && id.length() > 0 && dob.length() > 0) {
                    if (ListID(id) == null) {
                        try {
                            PreparedStatement statement = connection.prepareStatement(
                                    "INSERT INTO student (studentName, dateofbirth, studentId, year) VALUES (?, ?, ?, ?)"
                            );
                            statement.setString(2, dob);
                            statement.setString(3, id);
                            statement.setString(4, String.valueOf(semester));
                            if (name.equals("LeakyStudent")) {
                                for (int i = 0; i < 50; i++) { // enter 50 records for leaky student
                                    statement.setString(1, "LeakyStudent" + i);
                                    statement.executeUpdate();
                                }
                            } else {
                                statement.setString(1, name);
                                statement.executeUpdate();
                            }
                            statement.close();
                        } catch (SQLException ex) {
                            System.out.println("SQLException caught: " + ex.getMessage());
                        }
                    }
                } else {
                    System.out.println("Please enter all required fields.");
                }
            }


        }




    public void SetViewStudentRecord(Student ViewStudentRecord) {
        this.ViewStudentRecord = ViewStudentRecord;//makes it so its possible to view in the
    }

    public void SetselectedStudentName(Student selectedStudent) {
        this.selectedStudent = selectedStudent;

    }


    public void CreateNewModule(String moduleName,String code, int year,int grade) {
        if (selectedStudent != null) {// if
            // create a new module object with the given name and grade
            Modules module = new Modules(moduleName, code, year,  grade);
            selectedStudent.AddModule(module);
        } else {
            System.out.println("No student selected");
        }
    }

    public static Student ListID(String ID) {//lists the students info
        for (int i = 0; i < studentList.length; i++) {
            if (Objects.equals(ID, studentList[i].GetStudentID())) {
                return studentList[i];
            }
        }
        return null;
    }

    public ArrayList<String> GetstudentID() {//gets the sudent ID in the combobox
        ArrayList<String> Student_Name_List = new ArrayList<>();//might change to name we'll see
        for (int i = 0; i < studentList.length; i++) {
            Student_Name_List.add(studentList[i].GetStudentID());
        }
        return Student_Name_List;
    }

    public void SetStudentRecord(String studentID) {// puts the info of the student into the txt area
        for (int i = 0; i < studentList.length; i++) {
            if (studentID == studentList[i].GetStudentID()) {
                // If a matching student is found, sets the view student record and updates the text area
                SetViewStudentRecord(studentList[i]);

                View.studentDisplayinfomation.setText(ViewStudentRecord.GetStudentRecord());
            }

        }


    }


    public void FindName(String studentNum) {
        for (int i = 0; i < studentList.length; i++) {
            if (studentNum == studentList[i].GetStudentID()) {
                SetselectedStudentName(studentList[i]);

            }

        }


    }




    public void OrderData() {//a Method that orders my subjects in alphabetical order
        // creating Arraylist of Modules and an ArrayList
        ArrayList<Modules> moduleArrayList = new ArrayList<>();
        ArrayList<String> moduleListStudents = new ArrayList<>();


        for (Modules module : ViewStudentRecord.ModuleList) {
            moduleListStudents.add(module.GetModuleName());
        }
        // Add the module names to the moduleListStudents ArrayList
        Collections.sort(moduleListStudents, String::compareToIgnoreCase);

        // Sort the moduleListStudents ArrayList in alphabetical order
        for (String moduleName : moduleListStudents) {
            for (Modules module : ViewStudentRecord.ModuleList) {
                if (moduleName.equals(module.GetModuleName())) {
                    moduleArrayList.add(module);
                    break;
                }
            }
        }

        ViewStudentRecord.ModuleList = moduleArrayList;
        SetStudentRecord(ViewStudentRecord.GetStudentID());
    }


    public void Ordergrade() {
        ArrayList<Modules> gradeList = new ArrayList<>();// Create a new list to store the modules with grades matching in gradenumberList
        ArrayList<Integer> gradesOrdered = new ArrayList<>();

        for (Modules module : ViewStudentRecord.ModuleList) {
            gradesOrdered.add(module.GetModuleGrade());
        }

        Collections.sort(gradesOrdered, (Integer grade1, Integer grade2) -> grade1.compareTo(grade2));




// Loop through each grade in gradenumberList
        for (Integer grade : gradesOrdered) {

            // Loop through each module in ViewStudentRecord.ModuleList
            for (Modules module : ViewStudentRecord.ModuleList) {

                // If the module's grade matches the current grade being looped through
                if (grade.compareTo(module.GetModuleGrade()) == 0) {

                    // Add the module to the gradeList and exit the inner loop
                    gradeList.add(module);
                    break;
                }
            }
        }

// At this point, gradeList contains all modules with grades that match those in gradenumberList

        ViewStudentRecord.ModuleList = gradeList;
        SetStudentRecord(ViewStudentRecord.GetStudentID());
    }



}
