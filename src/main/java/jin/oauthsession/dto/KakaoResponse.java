package jin.oauthsession.dto;

import java.util.Map;

public class KakaoResponse implements OAuth2Response {

    private final Map<String, Object> attributes;

    // 생성자를 통해서 위의 해당 attributes를 초기화 위한 Map 데이터를 받는다.
    public KakaoResponse(Map<String, Object> attributes) {
        this.attributes = (Map<String, Object>) attributes.get("response"); // 네이버의 경우 데이터 내부에 또 데이터가 존재 하기에 (Map<String, Object>) 캐스팅하고.get 사용해서 꺼내준다.
    }


    @Override
    public String getProvider() {
        return "kakao"; // 네이버에 대한 답이기 때문에
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString(); // id 는 attributes 에서 id 키를 꺼낸다.toString 으로 스트링을 응답한다.
    }

    @Override
    public String getEmail() {
        return attributes.get("email").toString(); // email 은 attributes 에서 email 키를 꺼낸다.toString 으로 스트링을 응답한다.
    }

    @Override
    public String getName() {
        return attributes.get("nickname").toString(); // name 은 attributes 에서 name 키를 꺼낸다.toString 으로 스트링을 응답한다.
    }
}
