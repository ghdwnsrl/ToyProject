package study.login.member.domain;

import lombok.Builder;
import lombok.Getter;
import study.login.common.BaseEntity;

@Getter
public class Member extends BaseEntity {

    private final Long id;
    private final String userId;
    private final String password;
    private final String nickname;

    @Builder
    public Member(Long id, String userId, String password, String nickname) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
    }
}