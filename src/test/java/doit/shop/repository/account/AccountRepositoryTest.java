package doit.shop.repository.account;

import doit.shop.repository.Account;
import doit.shop.repository.AccountRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@SpringBootTest
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @DisplayName("Save 테스트")
    @Test
    void saveTest() {
        Account account = getAccount();
        Account savedAccount = accountRepository.save(account);

        Assertions.assertThat(savedAccount).extracting("accountName", "accountNumber", "accountBankName", "balance")
                .contains("1111-1111-1111", "강범서의 통장", "국민은행", 10000000);
    }

    private static Account getAccount() {
        Account account = Account.builder()
                .accountNumber("1111-1111-1111")
                .accountName("강범서의 통장")
                .accountBankName("국민은행")
                .balance(10000000)
                .build();
        return account;
    }

    @DisplayName("Save 후 아이디로 찾기 테스트")
    @Test
    void saveAndFindByIdTest() {
        Account account = getAccount();

        Account savedAccount = accountRepository.save(account);
        Optional<Account> foundAccount = accountRepository.findById(savedAccount.getId());

        Assertions.assertThat(foundAccount).isPresent();
        Assertions.assertThat(foundAccount.get().getAccountNumber()).isEqualTo("1111-1111-1111");
    }

    @DisplayName("Update 테스트")
    @Test
    void UpdateBalanceTest() {
        Account account = getAccount();

        Account savedAccount = accountRepository.save(account);
        savedAccount.setBalance(50000);
        accountRepository.save(savedAccount);

        Account updatedAccount = accountRepository.findById(savedAccount.getId()).get();
        Assertions.assertThat(updatedAccount.getBalance()).isEqualTo(50000);
    }

    @DisplayName("Delete 테스트")
    @Test
    void DeleteAccountTest() {
        Account account = getAccount();

        Account savedAccount = accountRepository.save(account);
        accountRepository.delete(savedAccount);

        Optional<Account> deletedAccount = accountRepository.findById(savedAccount.getId());
        Assertions.assertThat(deletedAccount).isEmpty();
    }
}
