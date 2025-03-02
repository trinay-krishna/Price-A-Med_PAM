package com.evernorth.profilesetup.api.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.evernorth.profilesetup.service.database.entity.CartItem;
import com.evernorth.profilesetup.service.database.entity.OrderItems;
import com.evernorth.profilesetup.service.database.entity.PharmMedInfo;
import com.evernorth.profilesetup.service.database.entity.User;
import com.evernorth.profilesetup.service.database.entity.UserOrders;
import com.evernorth.profilesetup.service.database.repository.CartItemRepository;
import com.evernorth.profilesetup.service.database.repository.OrderItemsRepository;
import com.evernorth.profilesetup.service.database.repository.UserOrdersRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class UserOrdersController {
    
    @Autowired
    UserOrdersRepository userOrdersRepository;

    @Autowired
    OrderItemsRepository orderItemsRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @PostMapping("/api/orders/placeOrder")
    public ResponseEntity<UserOrders> placeOrder(@RequestParam int userID) {
        //TODO: process POST request
        
        List<CartItem> cartItems = cartItemRepository.findByUserID(userID);

        if ( cartItems.size() == 0 ) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        User user = cartItems.get(0).getUser();

        HashMap<PharmMedInfo, Integer> medQuantity = new HashMap<>();
        BigDecimal totalPrice = BigDecimal.ZERO;;

        for( CartItem cartItem : cartItems ) {
            medQuantity.put(cartItem.getItem(), cartItem.getQuantity());
            totalPrice = totalPrice.add(cartItem.getUnitPrice());
        }

        UserOrders userOrder = new UserOrders(user, LocalDate.now(), totalPrice, LocalDate.now().plusDays(1), "Confirmed");

        UserOrders savedUserOrder = null;

        try {

            savedUserOrder = userOrdersRepository.save(userOrder);

            for( PharmMedInfo medication : medQuantity.keySet() ) {
                orderItemsRepository.save(
                    new OrderItems(
                        medication, savedUserOrder, medQuantity.get(medication)
                    )
                );
            }

        } catch(Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(savedUserOrder, HttpStatus.CREATED);
    }
    

}
