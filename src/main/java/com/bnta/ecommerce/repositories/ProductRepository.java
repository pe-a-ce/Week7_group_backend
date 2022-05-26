package com.bnta.ecommerce.repositories;

import com.bnta.ecommerce.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * FROM product WHERE product.id in (SELECT product.id FROM product JOIN stock on product.id = stock.product_id WHERE quantity >= ?1)",
            nativeQuery = true)
    List<Product> findProductsMinStock(Integer quantity);

    List<Product> findByManufacturerContainingIgnoreCase(String manufacturer);

    List<Product> findByModelContainingIgnoreCase(String model);

    List<Product> findByPriceGreaterThanEqualAndPriceLessThanEqual(Double minPrice, Double maxPrice);

    @Query(value = "select * from product where lower(manufacturer) like lower(?1) or lower(model) like lower(?1)", nativeQuery = true)
    List<Product> findEitherManufacturerOrModel(String query);
}
