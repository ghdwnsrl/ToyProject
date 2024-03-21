package study.login.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import study.login.domain.Member;

@Data
@ToString
@NoArgsConstructor
public class MemberDto {

    private Long id;

    @NotBlank(message = "아이디를 입력해주세요.")
    private String userId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;


    public MemberDto(Member member) {
        this.id = member.getId();
        this.userId = member.getUserId();
        this.password = member.getPassword();
        this.nickname = member.getNickname();
    }
}
