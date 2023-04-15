package com.isep.acme.domain.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.isep.acme.domain.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    Optional<Product> findBySku(String sku);

    @Modifying
    @Transactional
    @Query("DELETE FROM Product p WHERE p.sku=:sku")
    void deleteBySku(@Param("sku") String sku);

}
