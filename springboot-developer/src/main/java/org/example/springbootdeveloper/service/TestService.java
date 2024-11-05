package org.example.springbootdeveloper.service;

import org.example.springbootdeveloper.entity.Member;
import org.example.springbootdeveloper.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// 비지니스 계층 코드
@Service // 컴포넌트가 들어있고 스프링 컨테이너에 들어간다
public class TestService {

    @Autowired
    MemberRepository memberRepository; // 빈 주입

    public List<Member> getAllMembers() {
        return memberRepository.findAll(); // 멤버 목록 가져오기

    }
}
