package doit.shop.controller.account;

import doit.shop.controller.ListWrapper;
import doit.shop.controller.account.dto.AccountIdResponse;
import doit.shop.controller.account.dto.AccountInfoResponse;
import doit.shop.controller.account.dto.AccountRegisterRequest;
import doit.shop.controller.account.dto.AccountUpdateRequest;
import doit.shop.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/accounts")
public class AccountController implements AccountControllerDocs {

    private final AccountService accountService;

    @PostMapping
    public AccountIdResponse registerAccount(@RequestBody AccountRegisterRequest request, @RequestParam Long userId) {
        return accountService.registerAccount(request, userId);
    }

    @GetMapping
    public ListWrapper<AccountInfoResponse> getAccountList(@RequestParam Long userId) {
        List<AccountInfoResponse> accountList = List.of(accountService.getAccountList(userId));
        return new ListWrapper<>(accountList);
    }

    @GetMapping("/{accountId}")
    public AccountInfoResponse getAccountInfo(@PathVariable Long accountId) {
        return accountService.getAccountInfo(accountId);
    }

    @PutMapping("/{accountId}")
    public AccountInfoResponse updateAccountInfo(@PathVariable Long accountId,
                                                 @RequestBody AccountUpdateRequest request) {
        return accountService.updateAccountInfo(accountId, request);
    }

    @PostMapping("/{accountId}/deposit")
    public void depositAccount(@PathVariable Long accountId, @RequestParam Integer amount) {
        accountService.depositAccount(accountId, amount);
    }

    @PostMapping("/{accountId}/withdraw")
    public void withdrawAccount(@PathVariable Long accountId, @RequestParam Integer amount) {
        accountService.withdrawAccount(accountId, amount);
    }
}
