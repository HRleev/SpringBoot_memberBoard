package com.its.member.repository;

import com.its.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity,Long> {
    //요 리파지토리를 이용해서 어떤 엔팉티클래스를 다룰 것이냐 . 그 엔티티클래스 피케이 타입이 뭐냐


    //selct * from member_table where memberEmail=?
    //리턴타입 : MemberEntity
    //매개변수 : memberEmail(String)
    //인터페이스에서 정의한 메서드는 실행블록을 가질 수 없다 (추상메서드)
    Optional<MemberEntity> findByMemberEmail(String memberEmail);

}
