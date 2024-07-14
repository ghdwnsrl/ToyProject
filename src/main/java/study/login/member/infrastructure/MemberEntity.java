package study.login.member.infrastructure;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.login.common.BaseEntity;
import study.login.member.domain.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class MemberEntity extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String userId;
    private String password;

    private String nickname;

    @Builder
    public MemberEntity(Long id, String userId, String password, String nickname) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
    }

    public static MemberEntity from(Member member) {
        return MemberEntity.builder()
                .id(member.getId())
                .userId(member.getUserId())
                .password(member.getPassword())
                .nickname(member.getNickname())
                .build();
    }

    public Member toModel() {
        return Member.builder()
                .id(id)
                .userId(userId)
                .password(password)
                .nickname(nickname)
                .build();
    }
}
