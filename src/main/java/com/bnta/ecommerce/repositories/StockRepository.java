package com.bnta.ecommerce.repositories;

import com.bnta.ecommerce.models.Product;
import com.bnta.ecommerce.models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {


    @Transactional
    @Modifying
    @Query(
            value = "UPDATE stock SET quantity = quantity + ?1 WHERE id = ?2",
            nativeQuery = true
    )
    void alterStockQuantity(Integer quantity, Long id);

    @Query(
            value = "SELECT * FROM stock WHERE product_id = ?1",
            nativeQuery = true
    )
    Optional<Stock> findByProductId(Long id);

}
