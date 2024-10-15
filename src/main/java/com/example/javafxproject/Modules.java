package com.example.javafxproject;


import java.io.Serializable;

public class Modules implements Serializable {//  a model for the project


    private String moduleName,moduleCode;// name of the module
    private int Year,//grade for the module
    moduleGrade;




    // Constructor for Modules class
    public Modules(String modName,String code,int year ,int modGrade ){
       this.moduleGrade = modGrade;
       this.moduleName = modName;
       this.Year = year;
       this.moduleCode=code;


    }
    public String GetModuleName() {
        return moduleName;
    }

    //getter methods here
    public int GetModuleGrade() {
        return moduleGrade;
    }

    public int GetYear(){return Year;}

    public String  GetModuleCode(){return  moduleCode;}



}

