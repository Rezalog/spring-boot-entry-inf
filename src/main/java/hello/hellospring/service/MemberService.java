package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */

    public Long join(Member member) {
        // 중복 회원 검증
        validateDuplicateMember(member); // 개선된 코드 -- method를 따로 뽑는게 좋다. control + T

        // 이전 코드
        //-- cmd + option + v : 입력한 로직의 반환값에 맞는 변수 생성
        //-- 하지만 Optional<Member> result 는 불필요해보임, 바로 ifPresent
        // Optional<Member> result = memberRepository.findByName(member.getName());

        // result.get(); -- 권장되지 않음
        // result.orElseGet -- 값이 있으면 꺼내고 없으면 특정 메소드를 실행할 때 사용
        // result.ifPresent(m -> { // Optional method : result 의 결과값이 존재하면(null x) throw
        //    throw new IllegalStateException("이미 존재하는 회원입니다.");
        //}); //

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                        .ifPresent(m -> {
                            throw new IllegalStateException("이미 존재하는 회원입니다.");
                        });
    }

    /**
     * 전체 회원 조회
     */

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
