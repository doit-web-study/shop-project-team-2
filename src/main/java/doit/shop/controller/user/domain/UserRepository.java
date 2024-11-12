package doit.shop.controller.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserLoginId(String userLoginId);
    User findByUserLoginIdAndUserPassword(String userLoginId, String userPassword);

}
