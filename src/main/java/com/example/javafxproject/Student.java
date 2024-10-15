package com.example.javafxproject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author RoryHarrington
 * R00233458
 * rory.harrington@mycit.ie
 */

public class Student implements Serializable {// a standard java class to work in tandem with the array acts as my Model for students
    protected String name;
    protected String studentID;
    protected String DateOfBirth;

    protected int semester;
    ArrayList<Modules> ModuleList = new ArrayList<Modules>();


    public Student(String sName, String sID, String dob,int semes) {
        this.name = sName;
        this.studentID = sID;
        this.DateOfBirth = dob;
        this.semester = semes;
    }

    protected String GetStudentID() {
        return this.studentID;
    }


    public String StudentArrayList() {
        String studentString = this.name + " " + this.studentID + " " + this.DateOfBirth;
        return studentString;
    }

    public String StudentArrayFile() {
        String studentString = this.name + "," + this.studentID + "," + this.DateOfBirth;
        return studentString;
    }
    public void AddModule(Modules module){
        ModuleList.add(module);

    }
    public String GetStudentRecord() {
        String StudentRecord = "Name\t" + name + "\nDate Of Birth\t" + DateOfBirth + "\nStudent Id\t" + studentID;
        for (int i = 0; i < ModuleList.size(); i++) {
            StudentRecord += "\nModule:\t" + ModuleList.get(i).GetModuleName() + "\n";
            StudentRecord += "Grade:\t" + ModuleList.get(i).GetModuleGrade();


        }
        return StudentRecord;
    }

}

