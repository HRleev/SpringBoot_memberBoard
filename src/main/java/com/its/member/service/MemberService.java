package com.its.member.service;

import com.its.member.dto.MemberDTO;
import com.its.member.entity.MemberEntity;
import com.its.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Long save(MemberDTO memberDTO) {

        MemberEntity memberEntity = MemberEntity.toSaveEntity(memberDTO);
//        memberRepository.save(MemberEntity.toSaveEntity(memberDTO)); 요렇게 쓰자아
        Long id = memberRepository.save(memberEntity).getId();
        return id;
    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()) {
//            MemberEntity memberEntity=optionalMemberEntity.get();
//            MemberDTO memberDTO=MemberDTO.toDTO(memberEntity);
//            return memberDTO;
            return MemberDTO.toDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

    public MemberDTO login(MemberDTO memberDTO) {
        /**
         * login.html에서 입력받은 이메일, 비번을 받아오고
         * DB로부터 해당 이메일의 정보를 가져와서
         * 입력받은 비번과 DB에서 조회한 비번의 일치여부를 판단하여
         * 일치하면 로그인 성공 , 일치하지 않으면 로그인 실패로처리
         */
        //selct * from member_table where memberEmail=?
        Optional<MemberEntity> optionalMemberEntity =
                memberRepository.findByMemberEmail(memberDTO.getMemberEmail());

        if (optionalMemberEntity.isPresent()) {
            MemberEntity loginEntity = optionalMemberEntity.get();
            if (loginEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
                return MemberDTO.toDTO(loginEntity);
            } else {
                return null;//조회는 됐는데 비번이 틀릴때
            }
        } else {
            return null;//해당 계정이 없을때
        }
    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (MemberEntity member : memberEntityList) {
            MemberDTO memberDTO = MemberDTO.toDTO(member);
            memberDTOList.add(memberDTO);

//            memberDTOList.add(MemberDTO.toDTO(member));
        }
        return memberDTOList;
    }


    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    public void update(MemberDTO memberDTO) {
        memberRepository.save(MemberEntity.toUpdateEntity(memberDTO));
    }

    public String emailCheck(String memberEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);
        if(optionalMemberEntity.isPresent()){
            return "no";
        } else {
            return "ok";
        }
    }
}
