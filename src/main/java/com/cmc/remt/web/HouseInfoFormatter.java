package com.cmc.remt.web;
import com.cmc.remt.domain.HouseInfo;
import com.cmc.remt.svr.HouseInfoSvr;
import org.springframework.core.convert.ConversionService;
import org.springframework.roo.addon.web.mvc.controller.annotations.formatters.RooFormatter;

@RooFormatter(entity = HouseInfo.class, service = HouseInfoSvr.class)
public class HouseInfoFormatter {
}
