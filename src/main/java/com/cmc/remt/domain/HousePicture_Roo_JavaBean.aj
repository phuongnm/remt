// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.cmc.remt.domain;

import com.cmc.remt.domain.HouseInfo;
import com.cmc.remt.domain.HousePicture;

privileged aspect HousePicture_Roo_JavaBean {
    
    public String HousePicture.getImageName() {
        return this.imageName;
    }
    
    public void HousePicture.setImageName(String imageName) {
        this.imageName = imageName;
    }
    
    public String HousePicture.getImagePath() {
        return this.imagePath;
    }
    
    public void HousePicture.setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    
    public HouseInfo HousePicture.getHouseInfo() {
        return this.houseInfo;
    }
    
    public void HousePicture.setHouseInfo(HouseInfo houseInfo) {
        this.houseInfo = houseInfo;
    }
    
}
