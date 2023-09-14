package kr.co.dbcs.controller;

import kr.co.dbcs.model.LovedVO;
import kr.co.dbcs.service.LovedService;
import kr.co.dbcs.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Controller
@RequestMapping(value = "/movie")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MovieController {

    private final LovedService lovedService;
    private final MemberService memberService;

    @GetMapping(value = "/{path}")
    public String handlePath(@PathVariable String path, Model model, Principal principal,
                             @RequestParam(required = false, defaultValue = "1") int page,
                             @RequestParam(required = false, defaultValue = "") String keyword) {
        return "/member/home";
    }

    @GetMapping(value = "/crawl")
    @ResponseBody
    public List crawl() {
        return memberService.crawl();
    }

    @PostMapping("/loved/{username}/{movieId}")
    @ResponseBody

    public boolean addLoved(@PathVariable String username,
                            @PathVariable String movieId) {
        return lovedService.create(new LovedVO(username, movieId));
    }

    @DeleteMapping("/loved/{username}/{movieId}")
    @ResponseBody
    public boolean deleteLoved(@PathVariable String username,
                               @PathVariable String movieId) {
        return lovedService.deleteByUsernameWithId(new LovedVO(username, movieId));
    }

    @GetMapping("/loved/{username}")
    @ResponseBody
    public ArrayList<String> getLovedByUsername(@PathVariable String username) {
        ArrayList<String> ret = lovedService.getLovedByUsername(username);
        log.info("TEST : {}", ret);
        return ret;
    }
}
