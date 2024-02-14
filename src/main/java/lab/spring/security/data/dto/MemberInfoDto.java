package lab.spring.security.data.dto;

import lab.spring.security.data.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberInfoDto {
    private String id;
    private String nickname;
    private String email;

    public static MemberInfoDto of(Member member) {
        return MemberInfoDto.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .build();
    }
}
