package com.evernorth.profilesetup.service.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evernorth.profilesetup.service.database.entity.UserOrders;

public interface UserOrdersRepository extends JpaRepository<UserOrders, Long> {
    

}
