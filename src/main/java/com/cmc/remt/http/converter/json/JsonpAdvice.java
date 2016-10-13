package com.cmc.remt.http.converter.json;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import org.springframework.roo.addon.web.mvc.controller.annotations.http.converters.json.RooJSONJsonpAdvice;

/**
 * Indicates to MappingJackson2HttpMessageConverter that it should serialize the response
 * with JSONP format.
 * 
 * The annotation {@link ControllerAdvice} is detected automatically by Spring MVC and it will apply
 * the implemented aspect to all methods annotated with {@link RequestMapping}. In that case the 
 * implemented aspect is {@link ResponseBodyAdvice}. 
 */
@ControllerAdvice
@RooJSONJsonpAdvice
public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {

  public JsonpAdvice() {
    super("callback");
  }
}