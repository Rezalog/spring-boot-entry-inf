package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    // afterEach 로 DB clear 해주려면 Memory.. instance 필요
    @Autowired MemberRepository memberRepository;

    // 테스트는 빌드할때 포함 안되므로 메소드를 한글로 해도 무방하다.
    @Test
    void 회원가입() { // given, when, then 기법
        // given -- 주어진 상황에서(기반이 되는 데이터)
        Member member = new Member();
        member.setName("spring");

        // when -- 특정 경우에(검증해야하는 것)
        Long saveId = memberService.join(member);

        // then -- 이렇게 동작해야한다.(실제 검증)
        // member의 id 와 memberService의 saveId가 같은지
        Member findMember = memberRepository.findById(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test // 예외도 잘 터지는지 봐야한다.
    void 중복_회원_예외() {
        Member member1 = new Member();
        Member member2 = new Member();

        member1.setName("spring");
        member2.setName("spring");

        memberService.join(member1);

        // 람다식 이용 : 에러상태 검증
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        // 에러 메세지 검증
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    }

}
