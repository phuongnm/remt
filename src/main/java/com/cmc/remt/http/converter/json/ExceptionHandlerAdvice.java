package com.cmc.remt.http.converter.json;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.roo.addon.web.mvc.controller.annotations.http.converters.json.RooJSONExceptionHandlerAdvice;

@ControllerAdvice
@RooJSONExceptionHandlerAdvice
public class ExceptionHandlerAdvice {

  @ExceptionHandler(BindingErrorException.class)
  @ResponseBody
  @SuppressWarnings({"rawtypes", "unchecked"})
  public ResponseEntity handleException(HttpServletRequest req, BindingErrorException ex) {
    BindingResult result = ex.getBindingResult();
    return new ResponseEntity(result, HttpStatus.UNPROCESSABLE_ENTITY);
  }
}
