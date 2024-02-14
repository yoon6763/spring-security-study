package lab.spring.security.service;

import com.fasterxml.jackson.databind.JsonNode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lab.spring.security.data.dto.GoogleUserResourceDto;
import lab.spring.security.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class SocialLoginService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final MemberRepository memberRepository;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.client.registration.google.token-uri}")
    private String tokenUri;
    @Value("${spring.security.oauth2.client.registration.google.resource-uri}")
    private String resourceUri;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    private final String clientRedirectUri = "http://localhost:3000/redirect";

    public GoogleUserResourceDto socialLogin(String code, String registrationId) {
        String accessToken = getAccessToken(code, registrationId);
        GoogleUserResourceDto userResourceNode = getUserResource(accessToken, registrationId);
        System.out.println("userResourceNode = " + userResourceNode);

        String email = userResourceNode.getEmail();
        System.out.println("email = " + email);

//        String id = userResourceNode.get("id").asText();
//        String email = userResourceNode.get("email").asText();
//        System.out.println("id = " + id);
//        System.out.println("email = " + email);

        return userResourceNode;
    }

    private String getAccessToken(String authorizationCode, String registrationId) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", authorizationCode);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", clientRedirectUri);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity entity = new HttpEntity(params, headers);

        ResponseEntity<JsonNode> responseNode = restTemplate.exchange(tokenUri, HttpMethod.POST, entity, JsonNode.class);
        JsonNode accessTokenNode = responseNode.getBody();

        log.info("responseNode = {}", responseNode); // 'responseNode = <200 OK, [Content-Type:"application/json; charset=utf-8", Pragma:"no-cache", Cache-Control:"no-store", X-Content-Type-Options:"nosniff", X-Frame-Options:"SAMEORIGIN", X-XSS-Protection:"0", Server:"scaffolding on HTTPServer2", Transfer-Encoding:"chunked", Accept-Ranges:"none", Vary:"Origin,Accept-Encoding", Date:"Thu, 02 Sep 2021 07:00:00 GMT", Connection:"close"]>'
        log.info("accessTokenNode = {}", accessTokenNode); // 'accessTokenNode = {"access_token":"ya29.a0AfH6SMBz","expires_in":3599,"scope":"https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email","token_type":"Bearer","id_token":"eyJhbGciOiJSUzI1NiIsImtpZCI6I

        return accessTokenNode.get("access_token").asText();
    }

//    private JsonNode getUserResource(String accessToken, String registrationId) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + accessToken);
//        HttpEntity entity = new HttpEntity(headers);
//        return restTemplate.exchange(resourceUri, HttpMethod.GET, entity, JsonNode.class).getBody();
//    }

    private GoogleUserResourceDto getUserResource(String accessToken, String registrationId) {
        HttpHeaders headers = new HttpHeaders();

        if (!accessToken.startsWith("Bearer ")) {
            accessToken = "Bearer " + accessToken;
        }

        headers.set("Authorization", accessToken);
        HttpEntity entity = new HttpEntity(headers);
        return restTemplate.exchange(resourceUri, HttpMethod.GET, entity, GoogleUserResourceDto.class).getBody();
    }

    private String getEmail(String accessToken) {
        String jwtToken = accessToken.split(" ")[1];
        String email = null;
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(clientSecret.getBytes())
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getBody();
            email = claims.get("email", String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return email;
    }

    public String getAuthorizationUrl(String google) {
        return "https://accounts.google.com/o/oauth2/v2/auth?client_id=" + clientId + "&redirect_uri=" + redirectUri + "&response_type=code&scope=openid%20profile%20email";
    }
}