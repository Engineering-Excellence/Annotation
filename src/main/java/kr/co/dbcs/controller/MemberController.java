package kr.co.dbcs.controller;

import kr.co.dbcs.model.MemberVO;
import kr.co.dbcs.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;

@Log4j2
@Controller
@RequestMapping(value = "/member")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = "/{path}")
    public String handlePath(@PathVariable String path, Model model, Principal principal,
                             @RequestParam(required = false, defaultValue = "1") int page,
                             @RequestParam(required = false, defaultValue = "") String keyword) {
        switch (path) {
            case "chat":
                return "/member/chat";

            case "update":
                model.addAttribute("data", memberService.read(principal.getName()));
                break;

            case "updatePassword":
//    			model.addAttribute("data", memberService.read(principal.getName()));
                return "/member/updatePassword";
//    			break;

            case "deleteForm":
                return "/member/deleteForm";

        }
        return "/member/home";
    }

    @PostMapping(value = "/join")
    public String joinSubmit(@ModelAttribute(value = "memberVO") MemberVO memberVO) {

        log.info("회원가입 {}", memberService.create(memberVO) ? "성공" : "실패");
        return "redirect:/login";
    }

    @PostMapping("/check")
    @ResponseBody
    public boolean idCheck(@RequestBody MemberVO memberVO) {
        return memberService.loadUserByUsername(memberVO.getUsername()) != null;
    }

    @PostMapping(value = "/delete")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public String deleteMember(@ModelAttribute(value = "memberVO") MemberVO memberVO, Principal principal) {
        MemberVO vo = memberService.read(principal.getName());
        memberVO.setUsername(principal.getName());
        if (memberService.deleteUserByPasswordChk(principal.getName(), memberVO.getPassword(), vo)) {
            return "redirect:/login";
        } else {
            return "/member/deleteForm";
        }
    }

    @PostMapping(value = "/update")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public String updateSubmit(@ModelAttribute(value = "memberVO") MemberVO memberVO) {

        log.info("회원수정 {}", memberService.update(memberVO) ? "성공" : "실패");
        return "redirect:/";
    }

    @PostMapping(value = "/updatePassword")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public String updatePassword(@RequestParam HashMap<String, Object> map, Principal principal) {
        MemberVO vo = memberService.read(principal.getName());
        log.info("회원수정 {}", memberService.updatePassword(map, vo) ? "성공" : "실패");
        return "redirect:/";
    }
}
