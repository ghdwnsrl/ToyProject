package study.login.domain;

import jakarta.persistence.*;
import study.login.global.BaseEntity;

@Entity @Table(name = "MEMBER_COMMENT")
public class MemberComment extends BaseEntity {

    @Id
    @Column @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

}
