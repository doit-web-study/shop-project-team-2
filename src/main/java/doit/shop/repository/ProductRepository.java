package doit.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    default Product findByIdOrDefault(Long productId) {
        return findById(productId)
                .orElseThrow(() -> new RuntimeException("404001: 해당 상품을 찾을 수 없습니다!"));
    }

    Page<Product> findByNameContainingAndCategoryId(String keyword, Long categoryId, Pageable pageable);

    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

    Page<Product> findByNameContaining(String keyword, Pageable pageable);

    Page<Product> findAll(Pageable pageable);
}
