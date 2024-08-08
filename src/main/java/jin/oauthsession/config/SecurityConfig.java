package jin.oauthsession.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean // SecurityFilterChain 인터페이스를 리턴하는 메소드를 작성할건데 인자는 http
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        // 개발 환경이기에 csrf 설정을 꺼준다.
        http
                .csrf((csrf) -> csrf.disable());
        // 폼 로그인을 사용하지 않을 거기 때문에 꺼준다.
        http
                .formLogin((login) -> login.disable());
        // http 베이지 방식 인증도 도 꺼준다.
        http
                .httpBasic((basic) -> basic.disable());
        // oauth2 client 방식을 세팅하면 각각의 필터와 내무등록 정보 다 커스텀 해야한다. 그래서 로그인 방식으로 한다
        http
                .oauth2Login(Customizer.withDefaults()); // 초기 세팅

        // oauth2 각각의 경로에 대한 세팅
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/oauth2/**", "/login/**").permitAll() // oauth2 나 login 으로 시작하는 경로 모두 아무나 접속 가능하도록
                        .anyRequest().authenticated()); // 그 외 나머지 경로는 일단은 로그인을 한 사람만 접근할 수 있도록

        return http.build();

    }
}
