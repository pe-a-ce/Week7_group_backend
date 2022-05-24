package com.bnta.ecommerce.repositories;

import com.bnta.ecommerce.models.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

//    @Query(
//            name = "SELECT " +
//                    "* " +
//                    "FROM " +
//                    "purchase " +
//                    "WHERE " +
//                    "purchase_quantity > 0 AND purchase_quantity < 5 " +
//                    "AND " +
//                    "purchased_date > '1000-01-01' AND purchased_date < '4000-03-01' " +
//                    "AND " +
//                    "customer_id = 2",
//            nativeQuery = true
//    )
//    List<Purchase> searchAll();

}
