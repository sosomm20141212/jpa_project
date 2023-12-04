package com.example.jpa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import jakarta.persistence.GenerationType;

@Data        //getter-setter 대신 lombok 이용
@Entity      //가지고 올 클래스가 table
public class Demo {
    @Id       //column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long seq;      //column, 객체를 남에게 안보이기 위해 private 선언
    private String user;
}
