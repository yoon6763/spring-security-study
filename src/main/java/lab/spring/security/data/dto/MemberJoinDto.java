package lab.spring.security.data.dto;

import lab.spring.security.data.Member;
import lombok.Data;

@Data
public class MemberJoinDto {
    private String id;
    private String password;
    private String nickname;
    private String email;

    public Member toEntity() {
        return Member.builder()
                .id(id)
                .password(password)
                .nickname(nickname)
                .email(email)
                .build();
    }
}
