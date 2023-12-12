package study.login.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MemberDto {


    private String userId;
    private String password;

    private String nickname;

}
