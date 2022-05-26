package com.bnta.ecommerce.services;

import com.bnta.ecommerce.models.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Test
    void getAllProducts() {
        assertEquals(1000, productService.getAllProducts().size());
    }

    @Test
    void alterProduct() throws Exception {

        Product product1 = productService.alterProduct(41L, "Audi", "A3", 126.89);

//      "should change price of audi a3 with id 41, from £12.51 to £126.89",
              assertEquals(126.89, product1.getPrice());
    }
        @Test
        void testExpectedException_withFakeManufacter_Model() {

            Exception thrown = Assertions.assertThrows(Exception.class, () -> {
                productService.searchForProducts("DiamondMake Shiny").get(0);
            });

            Assertions.assertEquals("No cars found!", thrown.getMessage());
        }
    }
