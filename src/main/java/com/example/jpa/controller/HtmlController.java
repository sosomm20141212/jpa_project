package com.example.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import com.example.jpa.model.Demo;
import com.example.jpa.repository.DemoRepository;

@Controller          //주소를 mapping
public class HtmlController {
    @Autowired
    DemoRepository demoRepository;
    @GetMapping("/home")
    public String home(Model model) {
        List<String> list = new ArrayList<>();
        list.add("list1");
        list.add("list2");
        list.add("list3");
        model.addAttribute("listObject", list);

        Map<String, Object> map = new HashMap<>();
        map.put("key1", "map1");
        map.put("key2", "map2");
        map.put("key3", "map3");
        model.addAttribute("mapObject", map);

        Demo demo = new Demo();
        demo.setUser("강다솜");
        model.addAttribute("demoObject", demo.getUser());

        List<Demo> demo1 = demoRepository.findAll();
        model.addAttribute("demoObject1", demo1);

        return "html/home";   //결과값이 listObject이다. String 타입이 아니다.
    }

    @GetMapping("/user")
    public String user(Model model) {
        Map<String, Object> user = new HashMap<>();
        user.put("userId", "z1");
        user.put("userName", "CCC");
        user.put("userAge", 25);
        model.addAttribute("user", user);  //html과 값 교환
        return "html/user";
    }

    @GetMapping("/user/list")
    public String userList(Model model) {
        List<Demo> data = demoRepository.findAll();
        model.addAttribute("userList", data);
        return "html/userlist";
    }

    @GetMapping("/mode")
    public String mode(Model model) {
        Demo mode = demoRepository.findByUser("BBB");
        model.addAttribute("mode", mode);
        return "html/mode";
    }

    @GetMapping("/pagination")
    public String pagination(
        Model model,
        @RequestParam(defaultValue = "1") int page
    ) {
        int startPage = (page-1)/10 * 10 + 1;      //page 공식
        int endPage = startPage + 9;
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("page", page);
        return "html/pagination";
    }

    @GetMapping("/linkurl")
    public String linkurl(
        Model model,
        @RequestParam(defaultValue = "1") int page
    ) {
        int startPage = (page-1)/10 * 10 + 1;      //page 공식
        int endPage = startPage + 9;
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("page", page);
        return "html/linkurl";
    }
}
