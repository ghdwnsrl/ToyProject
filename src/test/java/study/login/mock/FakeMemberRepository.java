package study.login.mock;

import study.login.article.domain.Article;
import study.login.member.domain.Member;
import study.login.member.service.port.MemberRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class FakeMemberRepository implements MemberRepository {

    private final List<Member> data = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public Member save(Member member) {

        if (member.getId() == null || member.getId() == 0) {
            Member newMember = Member.builder()
                    .id(sequence.incrementAndGet())
                    .nickname(member.getNickname())
                    .password(member.getPassword())
                    .userId(member.getUserId())
                    .build();

            data.add(newMember);
            return newMember;
        } else {
            data.removeIf(m -> Objects.equals(m.getId(), member.getId()));
            data.add(member);
            return member;
        }
    }

    @Override
    public Optional<Member> findByUserId(String userId) {
        return data.stream().filter(member -> member.getUserId().equals(userId)).findAny();
    }

    @Override
    public Optional<Member> findById(Long id) {
        return data.stream().filter(m -> m.getId().equals(id)).findAny();
    }
}
