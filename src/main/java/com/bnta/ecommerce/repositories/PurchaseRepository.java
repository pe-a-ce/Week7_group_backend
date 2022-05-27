package com.bnta.ecommerce.repositories;

import com.bnta.ecommerce.models.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
                    "AND LOWER(manufacturer) LIKE LOWER(?5)",
            nativeQuery = true
    )
    List<Purchase> searchAll(Integer minQuantity,
                             Integer maxQuantity,
                             String fromDate,
                             String toDate,
                             String manufacturer);


    @Query(
            value = "SELECT *" +
                    " FROM " +
                    " purchase " +
                    " WHERE " +
                    " customer_id = ?1 AND product_id = ?2 " +
                    "AND purchased = FALSE",
            nativeQuery = true
    )
    Optional<Purchase> findByProductCustomerId(Long customerId, Long productId);


    @Query(
            value = "SELECT * FROM purchase WHERE customer_id = ?1 AND purchased = TRUE",
            nativeQuery = true
    )
    List<Purchase> getAllPurchasesForCustomer(Long customerId);


    @Query(
            value = "SELECT * FROM purchase WHERE customer_id = ?1 AND purchased = FALSE",
            nativeQuery = true
    )
    List<Purchase> getBasketForCustomer(Long customerId);



    @Query(
            value = "SELECT * FROM purchase WHERE customer_id = ?1 AND purchased = FALSE",
            nativeQuery = true
    )
    List<Purchase> getBasketByCustomerId(Long customerId);



    @Transactional
    @Modifying
    @Query(
            value = "insert into " +
                    " purchase " +
                    " (purchase_quantity, " +
                    " purchased, " +
                    " purchased_date, " +
                    " customer_id, " +
                    " product_id) " +
                    " values (?1, FALSE, null, ?2, ?3) ",
            nativeQuery = true
    )
    void addToBasket(Integer quantity, Long customerId, Long productId);



    @Transactional
    @Modifying
    @Query(
            value = "UPDATE purchase " +
                    " SET purchase_quantity = purchase_quantity + ?1 " +
                    " WHERE id = ?2",
            nativeQuery = true)
    Integer updateBasketQuantity(Integer change, Long purchaseId);



    @Transactional
    @Modifying
    @Query(
            value = "UPDATE purchase SET purchase_quantity = ?1 WHERE id = ?2",
            nativeQuery = true
    )
    Integer setItemBasketQuantity(Integer newQuantity, Long purchaseId);



    @Transactional
    @Modifying
    @Query(
            value = "DELETE FROM purchase WHERE customer_id = ?1 AND product_id = ?2",
            nativeQuery = true
    )
    Integer removeFromBasket(Long customerId, Long productId);



    @Transactional
    @Modifying
    @Query(
            value = "UPDATE purchase " +
                    "SET (purchased, purchased_date) = (TRUE, CAST(?1 AS DATE)) " +
                    "WHERE customer_id = ?2 AND purchased = FALSE",
            nativeQuery = true
    )
    Integer makePurchase(String purchaseDate, Long customerId);



    @Transactional
    @Modifying
    @Query(
            value = "DELETE FROM purchase WHERE customer_id = ?1",
            nativeQuery = true
    )
    Integer deletePurchaseByCustomerId(Long id);
}

