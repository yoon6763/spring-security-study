package lab.spring.security.data;

import lombok.Data;

@Data
public class OAuth2Response {

    private String access_token;
    private String token_type;
    private String expires_in;
    private String score;
    private String id_token;

}
