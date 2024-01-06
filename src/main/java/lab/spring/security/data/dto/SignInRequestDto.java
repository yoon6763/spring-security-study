package lab.spring.security.data.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SignInRequestDto {

    private String id;

    private String password;

}