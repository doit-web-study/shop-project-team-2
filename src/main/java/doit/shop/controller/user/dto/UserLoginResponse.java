package doit.shop.controller.user.dto;

import doit.shop.repository.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record UserLoginResponse(
        @Schema(description = "유저 식별 ID", example = "1")
        Long userId
) {
        public static UserLoginResponse from(User user) {
                return UserLoginResponse.builder()
                        .userId(user.getId())
                        .build();
        }
}
