package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest { // 다른 곳에서 사용될 것이 아니므로 public 일 필요 없음

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test // main 메소드 처럼 바로 실행 가능
    public void save() {
        Member member = new Member();
        member.setName("spring"); // name set
        repository.save(member); // id set

        Member result = repository.findById(member.getId()).get();
        //-- save 된 id로 찾고, Optional 이 반환되므로 get 으로 꺼냄(비권장)

        // 검증 : DB에서 꺼낸 result 값과 set 한 member 객체의 결과가 같으면 된다.
        // 1. System.out.println("result = " + (result == member));
        // 2. Assertions.assertEquals(member, result);
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }

}
