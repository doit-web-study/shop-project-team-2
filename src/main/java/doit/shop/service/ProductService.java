package doit.shop.service;

import doit.shop.controller.Product.dto.ProductDetailResponse;
import doit.shop.controller.Product.dto.ProductListResponse;
import doit.shop.controller.Product.dto.ProductRegisterResponse;
import doit.shop.controller.Product.dto.ProductUpdateResponse;
import doit.shop.repository.Category;
import doit.shop.repository.CategoryRepository;
import doit.shop.repository.Product;
import doit.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    //private final UserRepository userRepository;

    public ProductDetailResponse getProductById(Long productId) {
        Product product = productRepository.findByIdOrDefault(productId);
        return ProductDetailResponse.from(product);
    }

    @Transactional
    public ProductRegisterResponse createProduct(String productName, String productDescription, Integer productPrice, Integer productStock, MultipartFile productImage, Long categoryId, Long userId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        //User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        String imageUrl = null;
        if (productImage != null && !productImage.isEmpty()) {
            imageUrl = "/images/" + productImage.getOriginalFilename();
        }

        Product product = Product.builder()
                .name(productName)
                .description(productDescription)
                .price(productPrice)
                .stock(productStock)
                .imageUrl(imageUrl)
                .category(category)
                .build();
        Product savedProduct = productRepository.save(product);
        return ProductRegisterResponse.from(savedProduct);
    }

    @Transactional
    public ProductUpdateResponse modifyProduct(Long productId, String productName, String productDescription, Integer productPrice, Integer productStock, MultipartFile productImage, Long categoryId, Long userId) {
        Product product = productRepository.findByIdOrDefault(productId);

//        if(!product.getUser().getId().equals(userId)) {
//            return ProductUpdateResponse.fromError("403002", "상품 등록자만이 상품을 수정할 수 있습니다.");
//        }
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("400003: 유효하지 않은 카테고리 ID입니다."));

        String imageUrl = product.getImageUrl();
        if (productImage != null && !productImage.isEmpty()) {
            imageUrl = "/images/" + productImage.getOriginalFilename();
        }
        product.setName(productName);
        product.setDescription(productDescription);
        product.setPrice(productPrice);
        product.setStock(productStock);
        product.setImageUrl(imageUrl);
        product.setCategory(category);

        productRepository.save(product);
        return ProductUpdateResponse.from(product);
    }

    public void deleteProduct(Long productId) {
        Product product = productRepository.findByIdOrDefault(productId);
        productRepository.delete(product);
    }

//    public boolean isProductOwner(Long productId, Long userId) {
//        Product product = productRepository.findByIdOrDefault(productId);
//        return product.getUserLoginId().equals(userId);
//    }

    public ProductListResponse getProducts(Integer pageNumber, String keyword, Long categoryId, String orderBy) {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, "조회순".equals(orderBy) ? Sort.by("viewCount").descending() : Sort.by("createdAt").descending());

        Page<Product> productPage;
        if (keyword != null && categoryId != null) {
            productPage = productRepository.findByNameContainingAndCategoryId(keyword, categoryId, pageable);
        } else if (categoryId != null) {
            productPage = productRepository.findByCategoryId(categoryId, pageable);
        } else if (keyword != null) {
            productPage = productRepository.findByNameContaining(keyword, pageable);
        } else {
            productPage = productRepository.findAll(pageable);
        }

        List<ProductDetailResponse> result = new ArrayList<>();
        for (Product product : productPage.getContent()) {
            result.add(new ProductDetailResponse(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getStock(),
                    product.getImageUrl(),
                    product.getCreatedAt(),
                    product.getModifiedAt(),
                    product.getCategory().getId(),
                    product.getCategory().getType(),
                    product.getId(), // 주석달린거 에러나서 임시로
                    product.getDescription()    // 주석달린거 에러나서 임시로
//                    product.getUser().getId(),
//                    product.getUser().getNickName()
            ));
        }

        return new ProductListResponse(result, productPage.getNumber()+1, productPage.hasPrevious(), productPage.hasNext());
    }
}
