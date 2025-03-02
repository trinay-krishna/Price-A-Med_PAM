package com.evernorth.profilesetup.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.evernorth.profilesetup.service.database.entity.CartItem;
import com.evernorth.profilesetup.service.database.entity.PharmMedInfo;
import com.evernorth.profilesetup.service.database.entity.User;
import com.evernorth.profilesetup.service.database.repository.CartItemRepository;
import com.evernorth.profilesetup.service.database.repository.PharmacyMedInfoRepository;
import com.evernorth.profilesetup.service.database.repository.UserRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class CartItemController {
    
    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PharmacyMedInfoRepository pharmacyMedInfoRepository;

    @GetMapping("/api/getuserCart")
    public List<CartItem> getuserCart(@RequestParam int userID) {
        return cartItemRepository.findByUserID(userID);
    }

    @GetMapping("/api/findItemIds")
    public List<Long> findItemIds(@RequestParam int userID) {
        return cartItemRepository.findItemIdsByUserID(userID);
    }

    @PostMapping("/api/addToCart")
    public ResponseEntity<CartItem> addToCart(@RequestParam int userID, @RequestParam Long itemID) {
        //TODO: process POST request
        
        User user = userRepository.findById(userID).orElse(null);

        PharmMedInfo item = pharmacyMedInfoRepository.findById(itemID).orElse(null);

        if ( user == null || item == null ) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        CartItem cartItem = new CartItem(item, user, item.getUnitPrice(), 1);

        CartItem savedCartItem = cartItemRepository.save(cartItem);

        return new ResponseEntity<>(savedCartItem, HttpStatus.CREATED);
    }

    @PostMapping("/api/updateCartQuantity")
    public ResponseEntity<CartItem> updateCartQuantity(@RequestParam int userID, @RequestParam Long itemID, @RequestParam Character operation) {

        if ( operation != '+' && operation != '-' ) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        CartItem cartItem = cartItemRepository.findByUserIDAndItemID(userID, itemID).orElse(null);

        if ( cartItem == null ) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        if ( operation == '+' ) {
            cartItem.setQuantity( cartItem.getQuantity() + 1 );
        } else {
            cartItem.setQuantity( cartItem.getQuantity() - 1 );
        }

        CartItem savedCartItem = cartItemRepository.save(cartItem);

        return new ResponseEntity<>(savedCartItem, HttpStatus.OK);
    }
    


    
    
    

}
