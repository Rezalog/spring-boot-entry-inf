package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member); // 회원이 저장되면 저장된 회원이 반환된다.

    // Optional : Java 8 의 기능, null을 그대로 반환하지 않도록함
    Optional<Member> findById(Long id); // Member 의 id 로 회원을 찾도록 하는 메서드
    Optional<Member> findByName(String name);
    List<Member> findAll(); // 지금까지 저장된 모든 회원을 반환
}
