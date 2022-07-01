package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    //-- key : Long type, value : Member type, 동시성 문제 고려 안함
    private static long sequence = 0L; // 0,1,2 등 key 값을 생성해줌. 동시성 문제 고려 안함.


    @Override
    public Member save(Member member) {
        member.setId(++sequence); // member 의 name 은 고객이 입력할 것
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // return store.get(id); -- 과거에는 이렇게 썼으나 null 반환 가능성을 고려하여 Optional로 감싸준다.
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        // 람다식으로 돌리면서 파라미터의 name 이 같은지 확인
        return store.values().stream()
                .filter(member -> member.getName().equals(name)) // 같은지 확인
                .findAny(); // 루프를 돌면서 하나라도 찾으면 반환되고 없으면 Optional에 null이 포함되어 반환, Optional type return
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); // store 가 Map<Long, Member> 이므로 이 Map의 모든 Member의 values 를 반환하면됨.
    }

    public void clearStore() {
        store.clear();
    }
}
