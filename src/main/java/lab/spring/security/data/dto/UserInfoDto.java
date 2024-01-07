package lab.spring.security.data.dto;

import lab.spring.security.data.User;
import lombok.Data;

@Data
public class UserInfoDto {

    private String id;
    private String name;

    public static UserInfoDto of(User user) {
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.id = user.getUid();
        userInfoDto.name = user.getName();
        return userInfoDto;
    }

}
