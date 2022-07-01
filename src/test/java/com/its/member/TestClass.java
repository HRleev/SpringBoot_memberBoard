package com.its.member;

import com.its.member.dto.MemberDTO;
import com.its.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
@SpringBootTest
public class TestClass {
    @Autowired
    private MemberService memberService;

    public MemberDTO newMember(int i){
        MemberDTO member=new MemberDTO("테스트용 이메일"+i,"테스트용 비밀번호"+i,"테스트용 이름"+i,99+i,"테스트용 전화번호"+i);
        return member;
    }


    @Test

    @DisplayName("회원가입테스트")
    @Transactional
    @Rollback(value = true)
    public void saveTest(){
        Long saveId=memberService.save(newMember(1));
        MemberDTO memberDTO=memberService.findById(saveId);
        assertThat(newMember(1).getMemberEmail()).isEqualTo(memberDTO.getMemberEmail());

    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("로그인테스트")
    public void loginTest(){
        final String memberEmail="로그인용 이메일";
        final String memberPassword="로그인용 비밀번호";
        String memberName="로그인용 이름";
        int memberAge=99;
        String memberMobile="로그인용 전화번호";
        MemberDTO memberDTO=new MemberDTO(memberEmail,memberPassword,memberName,memberAge,memberMobile);
        Long saveId=memberService.save(memberDTO);
        //로그인 객체 생성 후 로그인
        MemberDTO loginMemberDTO = new MemberDTO();
        loginMemberDTO.setMemberEmail(memberEmail);
        loginMemberDTO.setMemberPassword(memberPassword);
        MemberDTO loginResult =memberService.login(loginMemberDTO);
        //로그인 결과가 not null 이면 테스트 통과
        assertThat(loginResult).isNotNull();
    }
    @Test
    @DisplayName("회원데이터 저장")
    public void memberSave(){
        IntStream.rangeClosed(1,20).forEach(i->{
            memberService.save(newMember(i));
        });
    }
    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("회원삭제테스트")
    public void memberDeleteTest(){
        /**
         * 신규회원등록
         * 삭제처리
         * 해당회원으로 조회시 null이면 통과
         */
        Long savedId = memberService.save(newMember(999));
        memberService.delete(savedId);
        assertThat(memberService.findById(savedId)).isNull();

    }
    @Test
    @Transactional
    @Rollback
    @DisplayName("업데이트 테스트")
    public void memberUpdate(){
    }
}
