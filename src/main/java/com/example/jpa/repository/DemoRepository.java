package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.jpa.model.Demo;
// import java.util.List;


@Repository
public interface DemoRepository extends JpaRepository<Demo, Long> {

    // List<Demo> findByUser(String user);         //user 이용 list로 값 받기
    Demo findByUser(String user);                  //user 이용 객체 그대로 값 받기
    // List<Demo> findBySeq(long seq);                //seq 이용 list로 값 받기
}
