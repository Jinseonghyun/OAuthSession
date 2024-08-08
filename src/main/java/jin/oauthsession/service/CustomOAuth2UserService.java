package jin.oauthsession.service;

import jin.oauthsession.dto.*;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    // DefaultOAuth2UserService OAuth2UserService의 구현체

    // 유저 메소드는 각 서비스의 사용자 정보 데이터를 내부 인자로 받아온다.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // 부모 클래스로 부터 해당 인증자 데이터를 갖고 오기 위한 구연부 작성
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User.getAttributes());  // 각 서비스의 사용자 데이터 잘 오나 보는 용도

        String registratinId = userRequest.getClientRegistration().getRegistrationId(); // 넘어온 데이터 어떤 서비스인지 알기 위해서 일단 받아오고 아래에서 if 문 활용해서 검증
        OAuth2Response oAuth2Response = null; // 아래에서 각 소셜에 맞는 바구니로 담을 준비

        // 각 서비스 마다 보내는 인증 규격이 다르다
        if (registratinId.equals("naver")) {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes()); // 만들어둔 바구니로 맞는 소셜에 연결해서 데이터 받기
        } else if (registratinId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else if (registratinId.equals("kakao")) {
            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }

        // 기본적인 롤이 유저라고 가정한다
        // role 값을 아래에서 CustomOAuth2User 유저로 넘겨주기 때문에 CustomOAuth2User 에서 받아줘야 한다.
        String role = "ROLE_USER";

        // dto 를 만들어 응답해준다.
        return new CustomOAuth2User(oAuth2Response, role);  //  특정한 유저에 대한 롤 값이 없기 때문에 롤을 넣어준다.
    }
}
