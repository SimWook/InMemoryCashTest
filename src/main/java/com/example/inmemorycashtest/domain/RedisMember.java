package com.example.inmemorycashtest.domain;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor
@RedisHash(value = "redisMember")
public class RedisMember{

    @Id
    private String id;
    private String name;
    private int age;

    public RedisMember(String name, int age) {
        this.id= name;
        this.name = name;
        this.age = age;
    }

}
