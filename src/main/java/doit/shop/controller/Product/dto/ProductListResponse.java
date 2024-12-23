package doit.shop.controller.Product.dto;

import java.util.List;

public record ProductListResponse(
        List<ProductDetailResponse> result,
        Integer pageNumber,
        boolean hasPrevious,
        boolean hasNext
) {
}
