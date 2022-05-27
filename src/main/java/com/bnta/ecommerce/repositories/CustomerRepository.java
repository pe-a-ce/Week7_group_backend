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


    @Query(
            value = "SELECT * FROM customer WHERE email = ?1 AND password = ?2",
            nativeQuery = true
    )
    Optional<Customer> findByEmailAndPassword(String email, String password);


    @Transactional
    @Modifying
    @Query(
            value = "UPDATE customer SET wallet = wallet + ?1 WHERE email = ?2",
            nativeQuery = true
    )
    Integer customerAddCredit(Double credit, String email);

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

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE customer SET password = ?1 WHERE id = ?2",
            nativeQuery = true
    )
    Integer updateCustomerPassword(String password, Long id);

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE customer SET wallet = wallet + ?1 WHERE id = ?2",
            nativeQuery = true
    )
    Integer updateCustomerWallet(Double change, Long id);


    @Transactional
    @Modifying
    @Query(
            value = "UPDATE customer SET name = ?1 WHERE id = ?2",
            nativeQuery = true
    )
    Integer updateCustomerName(String name, Long id);

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE customer SET address = ?1 WHERE id = ?2",
            nativeQuery = true
    )
    Integer updateCustomerAddress(String address, Long id);


    @Transactional
    @Modifying
    @Query(
            value = "UPDATE customer SET mobile = ?1 WHERE id = ?2",
            nativeQuery = true
    )
    Integer updateCustomerMobile(Long mobile, Long id);


    @Transactional
    @Modifying
    @Query(
            value = "UPDATE customer SET deleted = TRUE WHERE id = ?1",
            nativeQuery = true
    )
    Integer customerSelfDelete(Long id);


    @Transactional
    @Modifying
    @Query(
            value = "DELETE FROM customer WHERE id = ?1",
            nativeQuery = true
    )
    Integer customerPermanentDelete(Long id);

}
