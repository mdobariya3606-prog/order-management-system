package com.OrderSystem.demo.Controller;

import com.OrderSystem.demo.Model.Order;
import com.OrderSystem.demo.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService service;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Order order) {
        try {
            Order order1 = service.save(order);
            return new ResponseEntity<>(order1, HttpStatus.CREATED);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @GetMapping
    public List<Order> findAll() {
        return service.findAll();
    }
}
