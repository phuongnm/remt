// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.cmc.remt.web;

import com.cmc.remt.svr.HousePictureSvr;
import com.cmc.remt.web.HousePictureController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

privileged aspect HousePictureController_Roo_Controller {
    
    declare @type: HousePictureController: @Controller;
    
    declare @type: HousePictureController: @RequestMapping("/housepictures");
    
    public HousePictureSvr HousePictureController.housePictureSvr;
    
    @Autowired
    public HousePictureController.new(HousePictureSvr housePictureSvr) {
        this.housePictureSvr = housePictureSvr;
    }

}
