package com.example.inmemorycashtest.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @Column(name = "city")
    private String city;

    // 静的メソッド生成
    public static Member createMember(String name, int age, String city) {
        Member member = new Member();
        member.name = name;
        member.age = age;
        member.city = city;
        return member;
    }

    public void changeAge(int age) {
        this.age = age;
    }

}
