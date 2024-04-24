package com.example.inmemorycashtest.controller;

import com.example.inmemorycashtest.domain.Dto;
import com.example.inmemorycashtest.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members")
    public List<Dto> findAll(){
        return memberService.findAll();
    }

    @PostMapping("/members")
    public Long createMember(@RequestBody Dto dto){
        return memberService.createMember(dto.getName(),dto.getAge(),dto.getCity());
    }

    @GetMapping("/members/{id}")
    public Dto findOne(@PathVariable long id){
        return memberService.findOne(id);
    }

    @DeleteMapping("/members/{id}")
    public String deleteMember(@PathVariable long id){
        memberService.deleteMember(id);
        return "ok";
    }

    @PatchMapping("/members/{id}")
    public Dto changeAge(@PathVariable long id, @RequestParam int age){
        return memberService.changeAge(id,age);
    }
}
