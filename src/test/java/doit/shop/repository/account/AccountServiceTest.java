package doit.shop.repository.account;

import doit.shop.controller.account.domain.Account;
import doit.shop.controller.account.domain.AccountRepository;
import doit.shop.controller.account.dto.AccountIdResponse;
import doit.shop.controller.account.dto.AccountRegisterRequest;
import doit.shop.controller.account.service.AccountService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@SpringBootTest
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @DisplayName("Account 등록 테스트")
    void registerAccountTest() {
        AccountRegisterRequest accountRegisterRequest = new AccountRegisterRequest("강범서의 통장", "1111-1111-1111", "국민은행");
        AccountIdResponse accountIdResponse = accountService.registerAccount(accountRegisterRequest);

        Optional<Account> savedAccount = accountRepository.findById(accountIdResponse.accountId());
        Assertions.assertThat(savedAccount).isPresent();
        Assertions.assertThat(savedAccount.get().getAccountName()).isEqualTo("강범서의 통장");
        Assertions.assertThat(savedAccount.get().getAccountNumber()).isEqualTo("1111-1111-1111");
        Assertions.assertThat(savedAccount.get().getAccountBankName()).isEqualTo("국민은행");
    }

    @Test
    @DisplayName("Account Deposit 테스트")
    void depositAccountTest() {
        Account account = Account.builder()
                .accountNumber("1111-1111-1111")
                .accountName("강범서의 통장")
                .accountBankName("국민은행")
                .balance(10000000)
                .build();

        accountRepository.save(account);

        accountService.depositAccount(account.getId(), 20000000);
        Account updatedAccount = accountRepository.findById(account.getId()).get();

        Assertions.assertThat(updatedAccount.getId()).isEqualTo(30000000);
    }

    @Test
    @DisplayName("Account Withdrawal 테스트")
    void withdrawAccountTest() {
        Account account = Account.builder()
                .accountNumber("1111-1111-1111")
                .accountName("강범서의 통장")
                .accountBankName("국민은행")
                .balance(10000000)
                .build();

        accountRepository.save(account);

        accountService.withdrawAccount(account.getId(), 5000000);
        Account updatedAccount = accountRepository.findById(account.getId()).get();

        Assertions.assertThat(updatedAccount.getId()).isEqualTo(5000000);
    }
}
