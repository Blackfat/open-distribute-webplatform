package com.blackfat.common.spring;

/**
 * @author blackfat
 * @create 2019-03-30-上午7:36
 */
public class StudentB {

    private StudentA studentA;

    public void setStudentA(StudentA studentA){
        this.studentA = studentA;
    }

    public  StudentB(){

    }

    public StudentB(StudentA studentA){
        this.studentA = studentA;
    }
}
