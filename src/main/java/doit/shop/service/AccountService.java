package doit.shop.service;

import doit.shop.controller.account.dto.AccountIdResponse;
import doit.shop.controller.account.dto.AccountInfoResponse;
import doit.shop.controller.account.dto.AccountRegisterRequest;
import doit.shop.controller.account.dto.AccountUpdateRequest;
import doit.shop.repository.Account;
import doit.shop.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
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

    public AccountInfoResponse getAccountList(Long userId) {
        Account account = accountRepository.findAccountByUserIdOrThrow(userId);
        return AccountInfoResponse.from(account);
    }

    public AccountInfoResponse getAccountInfo(Long accountId) {
        Account account = accountRepository.findAccountByIdOrThrow(accountId);
        return AccountInfoResponse.from(account);
    }

    @Transactional
    public AccountInfoResponse updateAccountInfo(Long accountId, AccountUpdateRequest request) {
        Account account = accountRepository.findAccountByIdOrThrow(accountId);
        account.setAccountName(request.accountName());
        Account updatedAccount = accountRepository.save(account);

        return AccountInfoResponse.from(updatedAccount);
    }

    @Transactional
    public void depositAccount(Long accountId, Integer amount) {
        Account account = accountRepository.findAccountByIdOrThrow(accountId);
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }

    @Transactional
    public void withdrawAccount(Long accountId, Integer amount) {
        Account account = accountRepository.findAccountByIdOrThrow(accountId);
        if(account.getBalance() < amount) {
            throw new RuntimeException("Balance is not enough!");
        }

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
    }
}
