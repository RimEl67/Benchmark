package com.example.repositories;

import com.example.entities.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findByCategoryId(Long categoryId, Pageable pageable);
}
