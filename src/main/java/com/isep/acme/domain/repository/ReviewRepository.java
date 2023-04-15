package com.isep.acme.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isep.acme.domain.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {

}
