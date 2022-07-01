package com.its.member.controller;

import com.its.member.dto.MemberDTO;
import com.its.member.entity.MemberEntity;
import com.its.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor//생성자주입
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/save-form")
    public String saveForm() {
        return "/memberPages/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        memberService.save(memberDTO);
        return "/memberPages/login";
    }

    @GetMapping("/login-form")
    public String loginForm(@RequestParam(value = "redirectURL",defaultValue = "/")String redirectURL,Model model) {
        model.addAttribute("redirectURL",redirectURL);
        return "/memberPages/login";
    }


    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, Model model, HttpSession session,
                        @RequestParam(value = "redirectURL",defaultValue = "/")String redirectURL) {
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null) {
            model.addAttribute("longinResult", loginResult);
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            session.setAttribute("id", loginResult.getId());
            return "redirect:"+redirectURL;
        } else {
            return "memberPages/login";
        }
    }

    @GetMapping("/")
    public String findAll(Model model) {
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList", memberDTOList);
        return "memberPages/list";
    }

    //    /member/3
    @PostMapping("/ajax/{id}")
    public @ResponseBody MemberDTO findByIdAjax(@PathVariable Long id) {
        MemberDTO memberDTO = memberService.findById(id);
        return memberDTO;
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "memberPages/detail";
    }

    //get요청삭제
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        memberService.delete(id);
        return "redirect:/member/";
    }
    //delete요청삭제

    /**
     * /member/3:조회(get),저장(post),수정(put),삭제(delete)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteAjax(@PathVariable Long id) {
        memberService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK); //ajax호출한 부분에 리턴으로 200응담을 줌
    }

    //수정화면요청
    @GetMapping("/update")
    public String updateForm(HttpSession session, Model model) {
        Long id = (Long) session.getAttribute("id");
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("updateMember", memberDTO);
        return "memberPages/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute MemberDTO memberDTO) {
        memberService.update(memberDTO);
        return "redirect:/member/" + memberDTO.getId();
    }

    //수정처리(put요청)
    @PutMapping("/{id}")
    public ResponseEntity updateByAjax(@RequestBody MemberDTO memberDTO) {
        memberService.update(memberDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //이메일체크
    @PostMapping("/emailCheck")
    public @ResponseBody String emailCheck(@RequestParam String memberEmail){
        String checkResult=memberService.emailCheck(memberEmail);
        return checkResult;
    }
    @GetMapping("/logout")
    public String logout (HttpSession session){
        session.invalidate();
        session.removeAttribute("loginEmail");
        return "redirect:/";
    }
}
