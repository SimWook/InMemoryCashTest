package com.example.inmemorycashtest.repository;

import com.example.inmemorycashtest.domain.RedisMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRedisRepositoryTest {

    @Autowired
    MemberRedisRepository memberRedisRepository;

    @Autowired
    RedisTemplate<String, RedisMember> redisMemberRedisTemplate;

    @Test
    @DisplayName("redisRepository test")
    void repositoryTest() {
        memberRedisRepository.save(new RedisMember("hi", 10));
        RedisMember redisMember = memberRedisRepository.findById("hi").get();
        assertThat(redisMember.getName()).isEqualTo("hi");
    }

    @Test
    @DisplayName("redisRepository テータ変更テスト")
    void repositoryUpdateTest() {
        memberRedisRepository.save(new RedisMember("hi", 10));
        memberRedisRepository.save(new RedisMember("hi", 11));
        RedisMember redisMember = memberRedisRepository.findById("hi").get();
        assertThat(redisMember.getAge()).isEqualTo(11);
    }

    @Test
    @DisplayName("redisRepository全体返却テスト")
    void repositoryFindAllTest() {
        memberRedisRepository.save(new RedisMember("hi1", 10));
        memberRedisRepository.save(new RedisMember("hi2", 11));
        Iterable<RedisMember> all = memberRedisRepository.findAll();
        List<RedisMember> list = new ArrayList<>();
        all.forEach(list::add);
        assertThat(list.size()).isEqualTo(3);
    }

    /**
     * - 最初にオペレータを取得します。その後、setを使用してオブジェクトを保存します。
     * - オブジェクトを探すときには、指定したキーを使用してデータを探します。
     * - ここで知っておくべきことは、通常ValueはStringに対して演算を行いますが、RedisConfigでオブジェクトのシリアライズ設定を設定したため、自動的にシリアライズと逆シリアライズが行われ、RedisMemberを保存し、データを探すことができます。
     * - 同様に、更新操作もset(key, 変更)を使用すれば行えます。
     */
    @Test
    @DisplayName("redisTemplate value テスト")
    void redisTemplateTest() {
        ValueOperations<String, RedisMember> stringRedisMemberValueOperations = redisMemberRedisTemplate.opsForValue();
        stringRedisMemberValueOperations.set("hi", new RedisMember("hi", 10));
        RedisMember redisMember = redisMemberRedisTemplate.opsForValue().get("hi");
        assertThat(redisMember.getAge()).isEqualTo(10);
    }

    @Test
    @DisplayName("redisTemplate list テスト")
    void redisTemplateListTest() {
        ListOperations<String, RedisMember> stringRedisMemberListOperations = redisMemberRedisTemplate.opsForList();
        stringRedisMemberListOperations.rightPush("redisMemberList", new RedisMember("hi1", 10));
        stringRedisMemberListOperations.rightPush("redisMemberList", new RedisMember("hi2", 11));
        Long size = stringRedisMemberListOperations.size("redisMemberList");
        RedisMember result = stringRedisMemberListOperations.index("redisMemberList", 1);
        List<RedisMember> redisMemberList = stringRedisMemberListOperations.range("redisMemberList", 0, 1);
        stringRedisMemberListOperations.set("redisMemberList", 0, new RedisMember("m1", 10));
    }

    @Test
    @DisplayName("redisTemplate set テスト")
    void redisTemplateSetTest() {
        SetOperations<String, RedisMember> stringRedisMemberSetOperations = redisMemberRedisTemplate.opsForSet();
        stringRedisMemberSetOperations.add("memberSet", new RedisMember("h1", 10));
        stringRedisMemberSetOperations.add("memberSet", new RedisMember("h2", 10));

        RedisMember randomRedisMember = stringRedisMemberSetOperations.pop("memberSet");
        List<RedisMember> randomMemberSet = stringRedisMemberSetOperations.pop("memberSet", 2);
        Set<RedisMember> memberSet = stringRedisMemberSetOperations.members("memberSet");
        Boolean result = stringRedisMemberSetOperations.isMember("memberSet", new RedisMember("h1", 10));
        Long re = stringRedisMemberSetOperations.remove("memberSet", new RedisMember("hi2", 10));
    }

    @Test
    @DisplayName("redisTemplate zSet テスト")
    void redisTemplateZSetTest() {
        ZSetOperations<String, RedisMember> stringRedisMemberZSetOperations = redisMemberRedisTemplate.opsForZSet();
        stringRedisMemberZSetOperations.add("memberZSet", new RedisMember("hi1", 10), 1);
        stringRedisMemberZSetOperations.add("memberZSet", new RedisMember("hi2", 10), 2);

        Long memberZSet = stringRedisMemberZSetOperations.count("memberZSet", 0, 1);
        RedisMember result = stringRedisMemberZSetOperations.popMin("memberZSet").getValue();
    }

    @Test
    @DisplayName("redisTemplate hash テスト")
    void redisTemplateHashTest() {
        HashOperations<String, Object, Object> stringObjectObjectHashOperations = redisMemberRedisTemplate.opsForHash();
        stringObjectObjectHashOperations.put("memberHashOne", "name", new RedisMember("hi3", 10));

        Map<String, RedisMember> map = new HashMap<>();
        map.put("hi1", new RedisMember("hi1", 10));
        map.put("hi2", new RedisMember("hi2", 10));

        stringObjectObjectHashOperations.putAll("memberHashMap", map);
        RedisMember result = (RedisMember) stringObjectObjectHashOperations.get("memberHashMap", "hi1");
        RedisMember redisMember = (RedisMember) stringObjectObjectHashOperations.get("memberHashOne", "name");
    }
}
