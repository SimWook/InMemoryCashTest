package com.example.inmemorycashtest.repository;

import com.example.inmemorycashtest.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
