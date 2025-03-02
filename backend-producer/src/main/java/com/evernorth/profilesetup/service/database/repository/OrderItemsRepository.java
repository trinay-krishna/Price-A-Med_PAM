package com.evernorth.profilesetup.service.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evernorth.profilesetup.service.database.entity.OrderItems;

public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {
    
}
