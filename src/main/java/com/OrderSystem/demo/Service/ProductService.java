package com.OrderSystem.demo.Service;

import com.OrderSystem.demo.Model.Product;
import com.OrderSystem.demo.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepo repo;

    public List<Product> saveAll(List<Product> products) {
        for (Product product: products) {
            product.setThreshold((long) (product.getStock() * 0.25));
        }
        return repo.saveAll(products);
    }

    public Product restock(long pId, long newStock) {
        Product product = repo.findById(pId)
                .orElseThrow();

        product.setStock(product.getStock() + newStock);
        product.setThreshold((long) (product.getStock() * 0.25));
        repo.save(product);

        return product;
    }

    public List<Product> findAll() {
        return repo.findAll();
    }
}
