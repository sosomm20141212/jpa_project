package com.example.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.jpa.model.Member;


@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
//member = repository type 변수
//"select * from member where member_id = 'memberId' and member_pw = 'memberPw'"
    Member findByMemberIdAndMemberPw(String memberId, String memberPw);
    List<Member> findByMemberPwAndMemberId(String memberPw, String memberId); //size() test용 원래 이렇게 하면 안된다
    List<Member> findByMemberId(String memberId);
}
