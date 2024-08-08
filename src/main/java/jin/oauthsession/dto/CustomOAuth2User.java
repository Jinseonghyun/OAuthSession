package jin.oauthsession.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    // 데이터 받아올 애들 필드 선언
    // 아래에서 생성자로 두개의 데이터를 주입 받는다.
    private final OAuth2Response oAuth2Response;
    private final String role;

    public CustomOAuth2User(OAuth2Response oAuth2Response, String role) {
        this.oAuth2Response = oAuth2Response;
        this.role = role;
    }

    // Override 를 한 내부 메서드에 데이터를 다 넣어 주면 된다.
    @Override
    public Map<String, Object> getAttributes() { // Attributes 값는 로그인을 진행하면 이제 리소스 서버로부터 넘어오는 모든 데이터
        return null;                             // 각 서비스마다 데이터들의 키가 다르기 때문에 굳이 넣지 않는다.
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // oauth 는 롤값에 해당한다. 롤 값을 정희하기 위해서 컬렉션으로 인터페이스를 받는다.

        Collection<GrantedAuthority> collection = new ArrayList<>(); // 여기서 컬렉션을 객체를 ArrayList 만든다.
        // 컬렉션에 데이터를 추가하기 위해 아래처럼 생성
        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return role; // 내부에 입력받은 필드의 롤값을 넣어주면 된다.
            }
        });
        return collection;
    }

    @Override
    public String getName() {

        return oAuth2Response.getName();
    }

    // oauth2 로부터 받은 소셜로그인 데이터는 username 이라고 지칭 할 수 있는게 없다.
    // 그래서 id 를 특별하게 만들어 준다.
    // id 값은 provider 로 정의
    public String getUsername() {

        return oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
    }
}

