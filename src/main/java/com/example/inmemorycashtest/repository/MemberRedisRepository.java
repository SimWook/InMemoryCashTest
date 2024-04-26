package com.example.inmemorycashtest.repository;

import com.example.inmemorycashtest.domain.RedisMember;
import org.springframework.data.repository.CrudRepository;

public interface MemberRedisRepository extends CrudRepository<RedisMember,String> {

}
