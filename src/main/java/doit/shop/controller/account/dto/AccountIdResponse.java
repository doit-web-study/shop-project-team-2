package doit.shop.controller.account.dto;

import doit.shop.repository.Account;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record AccountIdResponse (
        @Schema(description = "계좌 식별 ID", example = "1")
        Long accountId
){
        public static AccountIdResponse from(Account account) {
                return AccountIdResponse.builder()
                        .accountId(account.getId())
                        .build();
        }
}
