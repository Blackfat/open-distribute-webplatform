package com.blackfat.common.spring;

/**
 * @author blackfat
 * @create 2019-03-30-上午7:33
 */
public class StudentA {

    private StudentB studentB;

    public void setStudentB(StudentB studentB){
        this.studentB = studentB;
    }

    public StudentA(){

    }

    public StudentA(StudentB studentB){
        this.studentB = studentB;
    }


}
