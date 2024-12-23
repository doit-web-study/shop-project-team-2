package doit.shop.controller.Product;

import doit.shop.controller.Product.dto.ProductDetailResponse;
import doit.shop.controller.Product.dto.ProductListResponse;
import doit.shop.controller.Product.dto.ProductRegisterResponse;
import doit.shop.controller.Product.dto.ProductUpdateResponse;
import doit.shop.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{productId}")
    public ProductDetailResponse getProductById(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }

    @PostMapping
    public ProductRegisterResponse createProduct(@RequestParam("productName") String productName, @RequestParam("productDescription") String productDescription,
                                                 @RequestParam("productPrice") Integer productPrice, @RequestParam("productStock") Integer productStock,
                                                 @RequestParam(value = "productImage", required = false) MultipartFile productImage,
                                                 @RequestParam("categoryId") Long categoryId, HttpSession session) {

        Long userId = (Long)session.getAttribute("userId");
        if(userId == null) {
            return ProductRegisterResponse.fromError("400002", "사용자 정보가 세션에 존재하지 않습니다.");
        }

        if (productName == null || productDescription == null || productPrice == null || productStock == null || categoryId == null) {
            return ProductRegisterResponse.fromError("400001", "상품 이름, 상품 설명, 상품 가격, 상품 재고, 카테고리 ID는 필수로 입력해주셔야 합니다.");
        }

        return productService.createProduct(productName, productDescription, productPrice, productStock, productImage, categoryId, userId);
    }

    @PutMapping("/{productId}")
    public ProductUpdateResponse modifyProduct(@RequestParam("productId") Long productId, @RequestParam("productName") String productName, @RequestParam("productDescription") String productDescription,
                                               @RequestParam("productPrice") Integer productPrice, @RequestParam("productStock") Integer productStock,
                                               @RequestParam(value = "productImage", required = false) MultipartFile productImage,
                                               @RequestParam("categoryId") Long categoryId, HttpSession session) {

        Long userId = (Long)session.getAttribute("userId");
        if(userId == null) {
            return ProductUpdateResponse.fromError("400002", "사용자 정보가 세션에 존재하지 않습니다.");
        }

        if (productName == null || productDescription == null || productPrice == null || productStock == null || categoryId == null) {
            return ProductUpdateResponse.fromError("400001", "상품 이름, 상품 설명, 상품 가격, 상품 재고, 카테고리 ID는 필수로 입력해주셔야 합니다.");
        }

        return productService.modifyProduct(productId, productName, productDescription, productPrice, productStock, productImage, categoryId, userId);
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable Long productId, HttpSession session) {
        Long userId = (Long)session.getAttribute("userId");
        if(userId == null) {
            throw new RuntimeException("사용자 정보가 세션에 존재하지 않습니다.");
        }
//        if (!productService.isProductOwner(productId, userId)) {
//            throw new RuntimeException("상품 등록자만이 상품을 삭제할 수 있습니다.");
//        }
        productService.deleteProduct(productId);
    }

    @GetMapping
    public ProductListResponse getProducts(@RequestParam("page") Integer pageNumber, @RequestParam(value = "keyword", required = false) String keyword,
                                           @RequestParam(value = "categoryId", required = false) Long categoryId,
                                           @RequestParam(value = "orderBy", required = false, defaultValue = "최신순") String orderBy) {

        return productService.getProducts(pageNumber, keyword, categoryId, orderBy);
    }
}
