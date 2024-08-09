package jin.oauthsession.service;

import jin.oauthsession.dto.*;
import jin.oauthsession.entity.UserEntity;
import jin.oauthsession.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    // DefaultOAuth2UserService OAuth2UserService의 구현체

    // db와 연동하기 위해 의존성 주입을 받게 세팅
    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository; // 레포 초기화
    }


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

        /**
         * DB 와 연동을 위한 레포지터리 연동 코드
         */
        // 소셜에서 로그인을 하게 되면 특정한 Id 값을 모르기 때문에 소셜에서 제공하는 provider 와 providerId 가지고 우리가 Id 라고 생각하자
        String username = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();

        // 위에서 받은 데이터로 해당 유저가 db 에 이미 존재하는지 아니면 없는지 조회
        UserEntity existData = userRepository.findByUsername(username);

        String role = null; // role 이 if 문 안에 있을 수 없어 외부에서 초기화
        if (existData == null) { // 처음 로그인 하는 사람

            UserEntity userEntity = new UserEntity();

            userEntity.setUsername(username);
            userEntity.setEmail(oAuth2Response.getEmail());
            userEntity.setRole("ROLE_USER");

            userRepository.save(userEntity); // 들어온 값들을 저장

        } else { // 이미 db 에 존재하는 사람 (이미 존재한다면 업데이트 시켜주자)

            role = existData.getRole(); // role 값 세팅

            // 이미 있는 데이터를 업데이트 하기 위해서 조회한 데이터 set으로 가져와서 다시 진행
            existData.setEmail(oAuth2Response.getEmail());

            userRepository.save(existData);
        }


        // dto 를 만들어 응답해준다.
        return new CustomOAuth2User(oAuth2Response, role);  //  특정한 유저에 대한 롤 값이 없기 때문에 롤을 넣어준다.
    }
}
