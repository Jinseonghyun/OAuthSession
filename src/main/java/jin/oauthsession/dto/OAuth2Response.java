package jin.oauthsession.dto;

public interface OAuth2Response {

    String getProvider(); // 각 서비스의 이름 (ex : 네이버, 카카와, 구글)

    String getProviderId(); // 각 서비스가 사용자에게 제공하는 id (각 서비스 마다 부여하는 번호가 다름)

    String getEmail();  // 사용자가 설정한 이메일

    String getName();   // 사용자가 설정한 이름
}
