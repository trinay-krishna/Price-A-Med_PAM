package com.evernorth.profilesetup.dto;

import java.util.List;
import java.util.Map;

import com.evernorth.profilesetup.service.database.entity.PharmMedInfo;


public class HomeDTO {

    private List<PharmMedInfo> pharmMedInfos;

    private Map<Long, Integer> quantities;

    public HomeDTO(List<PharmMedInfo> pm, Map<Long, Integer> quantities) {
        this.pharmMedInfos = pm;
        this.quantities = quantities;
    }

    public List<PharmMedInfo> getPharmMedInfos() {
        return pharmMedInfos;
    }
    
    public Map<Long, Integer> getQuantities() {
        return quantities;
    }
}
