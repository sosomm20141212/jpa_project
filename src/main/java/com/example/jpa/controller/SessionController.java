package com.example.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.ResponseBody;

import com.example.jpa.model.Member;
import com.example.jpa.repository.MemberRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class SessionController {
    @Autowired
    MemberRepository memberRepository;      //메소드가 아니라 객체

    @GetMapping("/login")
    public String login() {
        return "html/login";
    }

    // @PostMapping("/login")
    // public String loginPost(Member member, HttpSession session) {
    //     session.setAttribute("user", member);         //나에게 필요한 속성이 들어간다
    //     return "redirect:/main";                           //mapping 주소
    // }
    @PostMapping("/login")
    public String loginPost(
        @RequestParam("memberId") String memberId,
        @RequestParam("memberPw") String memberPw,
        HttpSession session
    ) {
        Member member;        //형태만 가지고 오므로 new 선언 안해줘도 된다
        member = memberRepository.findByMemberIdAndMemberPw(memberId, memberPw);
        int count = memberRepository.findByMemberPwAndMemberId(memberPw, memberId).size();
        if (count<1) {
            return "/html/loginfail";
        }
        session.setAttribute("member", member);    //member에 memberName 값 담기
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String main() {
        return "html/main";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/main";
    }

    @GetMapping("/register")
    public String register(HttpSession session) {
        Member member = new Member();
        member.setMemberId("admin");
        session.setAttribute("member", member);
        return "html/register";
    }

    @PostMapping("/register")
    // @ResponseBody
    public String registerPost(
        @RequestParam("memberId") String memberId,
        @RequestParam("memberPw") String memberPw,
        @RequestParam("memberName") String memberName,
        HttpSession session
    ) {
        int count = memberRepository.findByMemberId(memberId).size();
            
        Member member = new Member();
        member.setMemberId(memberId);
        member.setMemberName(memberName);
        member.setMemberPw(memberPw);
//"insert into member(member_id, member_name, member_pw) values(memberId, memberName, memberPw)"
        
        session.setAttribute("member", member);  // 이 String 내에 session이 담겨져 있다

        if(count > 0) {
            return "html/register";
        }
        memberRepository.save(member);
        return "redirect:/main";
    }
}
