package doit.shop.controller.account.service;

import doit.shop.controller.account.domain.Account;
import doit.shop.controller.account.domain.AccountRepository;
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

    public AccountInfoResponse getAccountInfo(Long accountId) {
        Optional<Account> result = accountRepository.findById(accountId);
        Account account;
        if(result.isPresent()) {
            account = result.get();
        } else {
            throw new RuntimeException("Account not found!");
        }

        return new AccountInfoResponse(
                account.getId(),
                account.getAccountName(),
                account.getAccountNumber(),
                account.getAccountBankName(),
                account.getBalance()
        );
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

        account.setAccountName(account.getAccountName());
        Account updatedAccount = accountRepository.save(account);

        return new AccountInfoResponse(
                updatedAccount.getId(),
                updatedAccount.getAccountName(),
                updatedAccount.getAccountNumber(),
                updatedAccount.getAccountBankName(),
                updatedAccount.getBalance()
        );
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
