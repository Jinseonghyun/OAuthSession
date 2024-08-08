package jin.oauthsession.dto;

import java.util.Map;

/*
각 서비스별로 다른 dto 즉 response 의 역할
기본적으로 데이터를 받을 수 있는 바구니를 만들었고 우리가 만든 CustomOAuth2UserService 에서
각 서비스에 맞은 서비스와 바구니를 연결 시켜서 데이터를 받아오면 된다.
*/

public class GoogleResponse implements OAuth2Response {

    // 변수로 json 데이터를 받을 맵 형식 선언
    private final Map<String, Object> attributes;

    // 해당 데이터를 인자로 받고
    public GoogleResponse(Map<String, Object> attributes) {

        // 인자로 받은 attributes 를 초기화
        this.attributes = attributes; // 네이버랑 다르게 바로 데이터가 다 담겨있다.

    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProviderId() {
        return attributes.get("sub").toString(); // 구글의 경우 id 가 sub 으로 되어 있다.
    }

    @Override
    public String getEmail() {
        return attributes.get("email").toString();
    }

    @Override
    public String getName() {
        return attributes.get("name").toString();
    }
}
