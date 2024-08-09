package jin.oauthsession.repository;

import jin.oauthsession.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // 유저 네임에 대한 쿼리를 날리는 유저 엔티티를 리턴하는 jpa 쿼리 작성
    UserEntity findByUsername(String username);
}
