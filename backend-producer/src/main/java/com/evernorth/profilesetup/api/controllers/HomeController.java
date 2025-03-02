package com.evernorth.profilesetup.api.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.util.Tuple;

import com.evernorth.profilesetup.dto.HomeDTO;
import com.evernorth.profilesetup.service.database.entity.Medication;
import com.evernorth.profilesetup.service.database.entity.PharmMedInfo;
import com.evernorth.profilesetup.service.database.repository.CartItemRepository;
import com.evernorth.profilesetup.service.database.repository.PharmacyMedInfoRepository;
import com.mysql.cj.protocol.x.OkBuilder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class HomeController {
    
    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    PharmacyMedInfoRepository pharmacyMedInfoRepository;

    @GetMapping("/api/home/getHome")
    public ResponseEntity<HomeDTO> getHome(@RequestParam int userID) {
        List<PharmMedInfo> pharmMedInfos = pharmacyMedInfoRepository.findAll();

        List<Object[]> quantities = cartItemRepository.findQuantitiesByUserID(userID);

        HashMap<Long, Integer> map = new HashMap<>();

        for( Object[] quantity : quantities ) {
            map.put(
                (Long)quantity[0],
                (Integer)quantity[1]
            );
        }

        HomeDTO homeDTO = new HomeDTO(pharmMedInfos, map);

        return new ResponseEntity(homeDTO, HttpStatus.OK);

    }

    @GetMapping("/api/home/search")
    public List<PharmMedInfo> searchMeds(@RequestParam String query) {
        return pharmacyMedInfoRepository.findMedicationsBySearch(query);
    }

    @GetMapping("/api/home/FilterType")
    public ResponseEntity<List<PharmMedInfo>> getMethodName(@RequestParam Medication.Type type) {

        return new ResponseEntity<>( pharmacyMedInfoRepository.findMedicationByType(type), HttpStatus.OK );

    }
    
    
    

}
