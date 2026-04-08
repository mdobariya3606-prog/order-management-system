package com.OrderSystem.demo.Controller;

import com.OrderSystem.demo.Model.Product;
import com.OrderSystem.demo.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/amazon")
public class ProductController {
    @Autowired
    private ProductService service;

    @PostMapping
    public List<Product> saveAll(@RequestBody List<Product> products) {
        return service.saveAll(products);
    }

    @PutMapping("{pId}/{quantity}")
    public Product restock(@PathVariable long pId,
                                  @PathVariable long quantity) {
        return service.restock(pId, quantity);
    }

    @GetMapping
    public List<Product> findAll() {
        return service.findAll();
    }
}
