package com.cmc.remt.web;
import com.cmc.remt.domain.HousePicture;
import com.cmc.remt.svr.HousePictureSvr;
import org.springframework.core.convert.ConversionService;
import org.springframework.roo.addon.web.mvc.controller.annotations.formatters.RooFormatter;

@RooFormatter(entity = HousePicture.class, service = HousePictureSvr.class)
public class HousePictureFormatter {
}
