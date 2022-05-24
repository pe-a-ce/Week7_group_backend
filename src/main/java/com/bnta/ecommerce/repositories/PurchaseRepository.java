package com.bnta.ecommerce.repositories;

import com.bnta.ecommerce.models.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {


    @Query(
            value = "SELECT " +
                    "purchase.id," +
                    "purchase.product_id," +
                    "purchase.customer_id," +
                    "purchase.purchase_quantity," +
                    "purchase.purchased," +
                    "purchase.purchased_date " +
                    "FROM " +
                    "purchase " +
                    "JOIN " +
                    "product " +
                    "ON " +
                    "purchase.product_id = product.id " +
                    "WHERE purchase_quantity >= ?1 AND purchase_quantity < ?2 " +
                    "AND purchased_date >= CAST(?3 AS DATE) AND purchased_date <= CAST(?4 AS DATE) " +
                    "AND manufacturer LIKE ?5",
            nativeQuery = true
    )
    List<Purchase> searchAll(Integer minQuantity,
                                     Integer maxQuantity,
                                     String fromDate,
                                     String toDate,
                                     String manufacturer);

}
