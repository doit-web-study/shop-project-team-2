package doit.shop.controller.Product.dto;

import doit.shop.repository.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record ProductDetailResponse(
        @Schema(description = "상품 ID", example = "1")
        Long productId,
        @Schema(description = "상품 이름", example = "Fantasy Book")
        String productName,
        @Schema(description = "상품 설명", example = "A thrilling fantasy novel.")
        String productDescription,
        @Schema(description = "상품 가격", example = "15000")
        Integer productPrice,
        @Schema(description = "상품 재고", example = "100")
        Integer productStock,
        @Schema(description = "상품 이미지 URL", example = "http://google.com/image.jpg")
        String productImage,
        @Schema(description = "생성 날짜", example = "2023-01-01T10:00:00")
        LocalDateTime productCreatedAt,
        @Schema(description = "수정 날짜", example = "2023-01-02T12:00:00")
        LocalDateTime productModifiedAt,
        @Schema(description = "카테고리 ID", example = "1")
        Long categoryId,
        @Schema(description = "카테고리 이름", example = "Fantasy")
        String categoryType,
        @Schema(description = "사용자 ID", example = "10")
        Long userId,
        @Schema(description = "사용자 닉네임", example = "John")
        String userNickname
) {
    public static ProductDetailResponse from(Product product) {
        return ProductDetailResponse.builder()
                .productId(product.getId())
                .productName(product.getName())
                .productDescription(product.getDescription())
                .productPrice(product.getPrice())
                .productStock(product.getStock())
                .productImage(product.getImageUrl())
                .productCreatedAt(product.getCreatedAt())
                .productModifiedAt(product.getModifiedAt())
                .categoryId(product.getCategory().getId())
                .categoryType(product.getCategory().getType())
//                .userId(product.getUser().getId())
//                .userNickname(product.getUser.getNickName())
                .build();
    }
}
