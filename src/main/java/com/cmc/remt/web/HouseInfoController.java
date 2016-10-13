package com.cmc.remt.web;
import com.cmc.remt.domain.HouseInfo;
import com.cmc.remt.svr.HouseInfoSvr;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.responses.json.RooJSON;

@RooController(path = "/houseinfoes", entity = HouseInfo.class, service = HouseInfoSvr.class)
@RooJSON
public class HouseInfoController {
}
