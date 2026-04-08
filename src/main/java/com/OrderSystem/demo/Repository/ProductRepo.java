package com.OrderSystem.demo.Repository;

import com.OrderSystem.demo.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE product SET quantity=?2 WHERE p_id=?1",
            nativeQuery = true)
    void updateStock(long pId, long quantity);
}
