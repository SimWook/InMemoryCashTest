package com.example.inmemorycashtest.service;

import com.example.inmemorycashtest.domain.Dto;
import com.example.inmemorycashtest.domain.Member;
import com.example.inmemorycashtest.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

//
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    // テスト用のダミーデータ
    @PostConstruct
    public void createMember() {
        for (int i = 0; i < 10; i++) {
            Member member = Member.createMember("m" + i, i + 1, "s" + i);
            memberRepository.save(member);
        }
    }

    public Long createMember(String name, int age, String city) {
        log.info("createMember implement!!");
        Member member = Member.createMember(name, age, city);
        return memberRepository.save(member).getId();
    }

    // キャッシュ適用前
    /*public List<Dto> findAll(){
        log.info("findAll implement!!");
        return memberRepository.findAll().stream().map(a -> new Dto(a.getName(),a.getAge(),a.getCity()))
                .collect(Collectors.toList());
    }*/
    // Cacheable：メソッドにキャッシュを利用できるようにします。
    @Cacheable(value = "memberList")
    public List<Dto> findAll(){
        log.info("findAll implement!!");
        return memberRepository.findAll().stream().map(a -> new Dto(a.getName(),a.getAge(),a.getCity()))
                .collect(Collectors.toList());
    }

    // キャッシュ適用前
    /*public Dto findOne(long id){
        log.info("findOne implement!!");
        return memberRepository.findById(id).map(a -> new Dto(a.getName(),a.getAge(),a.getCity())).get();
    }*/
    @Cacheable(value = "memberOne",key = "#id")
    public Dto findOne(long id){
        log.info("findOne implement!!");
        return memberRepository.findById(id).map(a -> new Dto(a.getName(),a.getAge(),a.getCity())).get();
    }

    // キャッシュ適用前
    /*public void deleteMember(long id){
        memberRepository.deleteById(id);
    }*/
    // CacheEvict：設定に応じてキャッシュの値を削除します。
    @CacheEvict(value = "memberList",allEntries = true)
    public void deleteMember(long id){
        memberRepository.deleteById(id);
    }

    // キャッシュ適用前
    /*public Dto changeAge(long id, int age){
        log.info("changeAge implement!!");
        Member member = memberRepository.findById(id).get();
        member.changeAge(age);
        return new Dto(member.getName(),member.getAge(),member.getCity());
    }*/
    // CachePut：キャッシュを更新する役割を果たします。
    @CachePut(value = "memberOne", key = "#id")
    public Dto changeAge(long id, int age){
        log.info("changeAge implement!!");
        Member member = memberRepository.findById(id).get();
        member.changeAge(age);
        return new Dto(member.getName(),member.getAge(),member.getCity());
    }
}
