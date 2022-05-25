package com.bnta.ecommerce.repositories;

import com.bnta.ecommerce.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(
            value = "SELECT * FROM customer WHERE email = ?1",
            nativeQuery = true
    )
    Optional<Customer> findByEmail(String email);

    @Query(
            value = "SELECT * FROM customer WHERE username = ?1",
            nativeQuery = true
    )
    Optional<Customer> findByUsername(String username);

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE customer SET username = ?1 WHERE id = ?2",
            nativeQuery = true
    )
    Integer updateCustomerUsername(String username, Long id);

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE customer SET email = ?1 WHERE id = ?2",
            nativeQuery = true
    )
    Integer updateCustomerEmail(String email, Long id);
}
