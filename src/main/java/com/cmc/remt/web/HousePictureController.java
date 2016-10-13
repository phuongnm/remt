package com.cmc.remt.web;
import com.cmc.remt.domain.HousePicture;
import com.cmc.remt.svr.HousePictureSvr;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.responses.json.RooJSON;

@RooController(path = "/housepictures", entity = HousePicture.class, service = HousePictureSvr.class)
@RooJSON
public class HousePictureController {
}
