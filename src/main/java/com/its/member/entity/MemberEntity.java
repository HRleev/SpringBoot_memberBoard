package com.its.member.entity;

import com.its.member.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "member_table")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "memberEmail", length = 50)
    private String memberEmail;

    @Column(name="memberPassword",length = 20)
    private String memberPassword;

    @Column(name = "memberName",length = 20)
    private String memberName;

    @Column(name = "memberAge")
    private int memberAge;

    @Column(name="memberMobile",length = 30)
    private String memberMobile;

    public static MemberEntity toSaveEntity (MemberDTO memberDTO) {
        MemberEntity memberEntity=new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberAge(memberDTO.getMemberAge());
        memberEntity.setMemberMobile(memberDTO.getMemberMobile());
        return memberEntity;
    }

    public static MemberEntity toUpdateEntity(MemberDTO memberDTO){
        MemberEntity memberEntity=new MemberEntity();
        memberEntity.setId(memberDTO.getId());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberAge(memberDTO.getMemberAge());
        memberEntity.setMemberMobile(memberDTO.getMemberMobile());
        return memberEntity;
    }
}
