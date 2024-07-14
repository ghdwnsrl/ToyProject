package study.login.member.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginMember {
    private String userId;
    private String nickname;
}
