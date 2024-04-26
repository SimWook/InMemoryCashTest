package com.example.inmemorycashtest.controller;

import com.example.inmemorycashtest.domain.Dto;
import com.example.inmemorycashtest.service.RedisCacheMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/redis")
public class RedisCacheMemberController {

    private final RedisCacheMemberService memberService;

    @GetMapping("/members/{id}")
    public Dto findOne(@PathVariable String id){
        return memberService.findOne(id);
    }

    @DeleteMapping("/members/{id}")
    public String deleteMember(@PathVariable String id){
        memberService.deleteMember(id);
        return "ok";
    }

    @PatchMapping("/members/{id}")
    public Dto changeAge(@PathVariable String id, @RequestParam int age){
        return memberService.changeAge(age, id);
    }
}
