package doit.shop.repository;

import doit.shop.repository.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserLoginId(String userLoginId);
    User findByUserLoginIdAndUserPassword(String userLoginId, String userPassword);

}
