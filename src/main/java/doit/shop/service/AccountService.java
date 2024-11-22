package doit.shop.service;

import doit.shop.repository.Account;
import doit.shop.repository.AccountRepository;
import doit.shop.controller.account.dto.AccountIdResponse;
import doit.shop.controller.account.dto.AccountInfoResponse;
import doit.shop.controller.account.dto.AccountRegisterRequest;
import doit.shop.controller.account.dto.AccountUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public AccountIdResponse registerAccount(AccountRegisterRequest accountRegisterRequest, long userId) {
        if (accountRepository.findByUserId(userId) != null) {
            throw new RuntimeException("User already has an account!");
        }
        return getAccountIdResponse(accountRegisterRequest);
    }

    private AccountIdResponse getAccountIdResponse(AccountRegisterRequest accountRegisterRequest) {
        Account account = getAccount(accountRegisterRequest);

        Account saved = accountRepository.save(account);
        return AccountIdResponse.from(saved);
    }

    private static Account getAccount(AccountRegisterRequest accountRegisterRequest) {
        return Account.builder()
                .accountName(accountRegisterRequest.accountName())
                .accountNumber(accountRegisterRequest.accountNumber())
                .accountBankName(accountRegisterRequest.accountBankName())
                .balance(0)
                .build();
    }

    public List<AccountInfoResponse> getAccountList(Long userId) {
        List<AccountInfoResponse> accountInfoResponses = new ArrayList<>();

        Account account = accountRepository.findByUserId(userId);
        if (account == null) {
            throw new RuntimeException("Account not found for user.");
        }
        accountInfoResponses.add(AccountInfoResponse.from(account));
        return accountInfoResponses;
    }

    public AccountInfoResponse getAccountInfo(Long accountId) {
        Optional<Account> result = accountRepository.findById(accountId);
        Account account;
        if(result.isPresent()) {
            account = result.get();
        } else {
            throw new RuntimeException("Account not found!");
        }
        return AccountInfoResponse.from(account);
    }

    @Transactional
    public AccountInfoResponse updateAccountInfo(Long accountId, AccountUpdateRequest request) {
        Optional<Account> result = accountRepository.findById(accountId);
        Account account;
        if(result.isPresent()) {
            account = result.get();
        } else {
            throw new RuntimeException("Account not found!");
        }
        account.setAccountName(request.accountName());
        Account updatedAccount = accountRepository.save(account);

        return AccountInfoResponse.from(updatedAccount);
    }

    @Transactional
    public void depositAccount(Long accountId, Integer amount) {
        Optional<Account> result = accountRepository.findById(accountId);
        Account account;
        if(result.isPresent()) {
            account = result.get();
        } else {
            throw new RuntimeException("Account not found!");
        }
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }

    @Transactional
    public void withdrawAccount(Long accountId, Integer amount) {
        Optional<Account> result = accountRepository.findById(accountId);
        Account account;
        if(result.isPresent()) {
            account = result.get();
        } else {
            throw new RuntimeException("Account not found!");
        }
        if(account.getBalance() < amount) {
            throw new RuntimeException("Balance is not enough!");
        }

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
    }
}
