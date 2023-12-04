package com.example.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.jpa.repository.DemoRepository;
import com.example.jpa.model.Demo;
import java.util.*;

@RestController
public class DBController {
    @Autowired
    DemoRepository demoRepository;
// findAll() : 전부 찾아준다
    @GetMapping("/")
    public List<Demo> demoSelect() {         
        return demoRepository.findAll();      
    }

//user 이용 list로 값 받기
    // @GetMapping("/user")
    // public List<Demo> demoSelect1() {              
    //     return demoRepository.findByUser("AAA");
    // }
    
//user 이용 객체 그대로 값 받기
    // @GetMapping("/user")
    // public Demo demoSelect1() {                       
    //     return demoRepository.findByUser("AAA");
    // }

//user 이용 getUser() : String
    // @GetMapping("/user")
    // public String demoSelect1() {                       
    //     String user = demoRepository.findByUser("AAA").getUser();  
    //     return user;
    // }

//seq 이용 list로 값 받기
    // @GetMapping("/user2")
    // public List<Demo> demoSelect2() {
    //     return demoRepository.findBySeq(2);
    // }

//insert 메소드 : lombok은 get set을 자동으로 생성시켜준다
    // @GetMapping("/insert")
    // public String demoInsert() {
    //     Demo data = new Demo();
    //     data.setSeq(4);
    //     data.setUser("강다솜");
    //     demoRepository.save(data);           //seq,user 새 값을 DB에 집어 넣는다
    //     return data.getUser().toString() + " 저장 되었습니다";
    // }

//update 메소드 
    @GetMapping("/insert")
    public Demo demoInsert() {
        Demo data = new Demo();
        data.setSeq(3);
        data.setUser("강다솜");
        demoRepository.save(data);                //save를 조심해야 한다
        return data;
    }

//delete 메소드
    @GetMapping("/delete")
    public String demoDelete() {
        Demo data = new Demo();
        data.setSeq(3);
        demoRepository.delete(data);
        return data.toString();
    }


}
