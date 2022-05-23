package com.bnta.ecommerce.repositories;

import com.bnta.ecommerce.models.Product;
import com.bnta.ecommerce.models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
}
