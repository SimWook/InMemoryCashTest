package com.example.inmemorycashtest.service;

import com.example.inmemorycashtest.domain.Dto;
import com.example.inmemorycashtest.domain.RedisMember;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RedisCacheMemberService {

    private final RedisTemplate<String, RedisMember> redisTemplate;

    @PostConstruct
    public void init(){
        for(int i=0; i<10; i++){
            RedisMember redisMember = new RedisMember("m" + i, i);
            redisTemplate.opsForValue().set(redisMember.getId(),redisMember);
        }
    }

    @Cacheable(value = "redisMemberOne",key = "#id")
    public Dto findOne(String id){
        log.info("findOne implement!!");
        RedisMember redisMember = redisTemplate.opsForValue().get(id);
        return new Dto(redisMember.getName(),redisMember.getAge(),"noCity");
    }

    @CacheEvict(value = "redisMemberOne", key = "#id")
    public void deleteMember(String id){
        redisTemplate.delete(id);
    }

    @CachePut(value = "redisMemberOne",key = "#id")
    public Dto changeAge(int age, String id){
        log.info("changeAge implement!!");
        RedisMember redisMember = redisTemplate.opsForValue().get(id);
        redisMember.changeAge(age);
        redisTemplate.opsForValue().set(id,redisMember);
        return new Dto(redisMember.getName(),redisMember.getAge(),"noCity");
    }
}
