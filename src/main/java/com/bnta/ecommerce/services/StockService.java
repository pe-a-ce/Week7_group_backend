package com.bnta.ecommerce.services;

import com.bnta.ecommerce.models.Product;
import com.bnta.ecommerce.models.Stock;
import com.bnta.ecommerce.repositories.ProductRepository;
import com.bnta.ecommerce.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    public static Object findAll;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private ProductRepository productRepository;

    public StockService() {
    }

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

   public List<Stock> getAll() {
       return  stockRepository.findAll();
   }

   public Optional<Stock> findById(Long id){
        return stockRepository.findById(id);
   }

   public Stock addToStock(Long id, Product product){
       Stock stock = new Stock(0, product);
       stock.setId(id);
       return stockRepository.save(stock);
   }

    public Boolean deleteStock(Long id) throws Exception{
        Optional<Stock> stock = stockRepository.findById(id);
        if (stock.isPresent()){
            stockRepository.deleteById(id);
            return true;
        } else {
            throw new Exception("Product not found");
        }
    }

    public Stock recreateDeletedStock(Long id, int quantity) throws Exception{
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()){
            throw new Exception("No such product in database");
        }
        if (product.get().getStock() != null){
            throw new Exception("Stock already assigned to product");
        }
        if (stockRepository.findById(id).isPresent()){
            throw new Exception("Stock already exists");
        }
        return stockRepository.save(new Stock(quantity, product.get()));
    }

    public Stock changeQuantity(Long id, int quantity) throws Exception{
        if (quantity < 0){
            throw new Exception("Quantity cannot be negative!");
        } else if (stockRepository.findById(id).isEmpty()) {
            throw new Exception("No such stock count in database!");
        }
        Optional<Stock> stockToUpdate = stockRepository.findById(id);
        stockToUpdate.get().setQuantity(quantity);
        stockRepository.save(stockToUpdate.get());
        return stockToUpdate.get();
    }
}
