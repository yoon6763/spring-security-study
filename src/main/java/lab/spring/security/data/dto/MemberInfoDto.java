package lab.spring.security.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberInfoDto {

    private String id;

    public static MemberInfoDto of(String id) {
        return new MemberInfoDto(id);
    }
}
