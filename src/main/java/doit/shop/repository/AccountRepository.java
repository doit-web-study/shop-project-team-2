package doit.shop.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUserId(Long userId);

    default Account findAccountByUserIdOrThrow(Long userId) {
        return findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Account not found for user!"));
    }

    default Account findAccountByIdOrThrow(Long accountId) {
        return findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found!"));
    }

}
