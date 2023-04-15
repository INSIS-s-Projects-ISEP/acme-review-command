package com.isep.acme.domain.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@NoArgsConstructor
@ToString
public class Product {

    @Id
    private UUID productId = UUID.randomUUID();

    @Column(nullable = false, unique = true)
    private String sku;

    public Product(String sku) {
        this.sku = sku;
    }

    public void setSku(String sku) {
        if(sku == null || sku.isBlank()) {
            throw new IllegalArgumentException("SKU is a mandatory attribute of Product.");
        }
        if(sku.length() != 12) {
            throw new IllegalArgumentException("SKU must be 12 characters long.");
        }

        this.sku = sku;
    }

}
