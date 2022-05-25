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
    void searchByManufacturerAndModel() throws Exception {

        Product product1 = productService.searchByManufacturerAndModel("Mitsubishi", "Eclipse").get(1);

        assertAll("should bring up car details; price and id",
                ()-> assertEquals("Mitsubishi", product1.getManufacturer()),
                ()-> assertEquals("Eclipse", product1.getModel()),
                ()-> assertEquals(654, product1.getId()),
                ()-> assertEquals(17.42, product1.getPrice())
        );
    }
        @Test
        void testExpectedException_withFakeManufacter_Model() {

            Exception thrown = Assertions.assertThrows(Exception.class, () -> {
                productService.searchByManufacturerAndModel("DiamondMake", "Shiny").get(0);
            });

            Assertions.assertEquals("No cars found!", thrown.getMessage());
        }
    }
