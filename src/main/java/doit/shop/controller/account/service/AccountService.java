package doit.shop.controller.account.service;

import doit.shop.controller.account.domain.Account;
import doit.shop.controller.account.domain.AccountRepository;
import doit.shop.controller.account.dto.AccountIdResponse;
import doit.shop.controller.account.dto.AccountInfoResponse;
import doit.shop.controller.account.dto.AccountRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Transactional
    public AccountIdResponse registerAccount(AccountRegisterRequest accountRegisterRequest) {
        Account account = Account.builder()
                .accountName(accountRegisterRequest.accountName())
                .accountNumber(accountRegisterRequest.accountNumber())
                .accountBankName(accountRegisterRequest.accountBankName())
                .balance(0)
                .build();

        Account saved = accountRepository.save(account);
        return new AccountIdResponse(saved.getId());
    }

    @Transactional
    public List<AccountInfoResponse> getAccountList() {
        List<Account> accounts = accountRepository.findAll();
        List<AccountInfoResponse> accountInfoResponses = new ArrayList<>();

        for(Account account: accounts) {
            AccountInfoResponse response = new AccountInfoResponse(
                    account.getId(),
                    account.getAccountName(),
                    account.getAccountNumber(),
                    account.getAccountBankName(),
                    account.getBalance()
            );
            accountInfoResponses.add(response);
        }
        return accountInfoResponses;
    }
}
