package com.cmc.remt.http.converter.json;

import java.beans.PropertyEditor;
import java.util.List;
import java.util.Map;

import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.roo.addon.web.mvc.controller.annotations.http.converters.json.RooJSONBindingErrorException;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
@RooJSONBindingErrorException
public class BindingErrorException extends RuntimeException implements BindingResult {

  private static final long serialVersionUID = 3173335735776325694L;

  private final BindingResult bindingResult;

  public BindingResult getBindingResult() {
    return bindingResult;
  }

  public BindingErrorException(BindingResult bindingResult) {
    super();
    this.bindingResult = bindingResult;
  }

  @Override
  public void reject(String errorCode, String defaultMessage) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void reject(String errorCode, Object[] errorArgs, String defaultMessage) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void rejectValue(String field, String errorCode) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void rejectValue(String field, String errorCode, String defaultMessage) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void rejectValue(String field, String errorCode, Object[] errorArgs,
      String defaultMessage) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void addAllErrors(Errors errors) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean hasErrors() {
    return this.bindingResult.hasErrors();
  }

  @Override
  public int getErrorCount() {
    return this.bindingResult.getErrorCount();
  }

  @Override
  public List<ObjectError> getAllErrors() {
    return this.bindingResult.getAllErrors();
  }

  @Override
  public boolean hasGlobalErrors() {
    return this.bindingResult.hasGlobalErrors();
  }

  @Override
  public int getGlobalErrorCount() {
    return this.bindingResult.getGlobalErrorCount();
  }

  @Override
  public List<ObjectError> getGlobalErrors() {
    return this.bindingResult.getGlobalErrors();
  }

  @Override
  public ObjectError getGlobalError() {
    return this.bindingResult.getGlobalError();
  }

  @Override
  public boolean hasFieldErrors() {
    return this.bindingResult.hasFieldErrors();
  }

  @Override
  public int getFieldErrorCount() {
    return this.bindingResult.getFieldErrorCount();
  }

  @Override
  public List<FieldError> getFieldErrors() {
    return this.bindingResult.getFieldErrors();
  }

  @Override
  public FieldError getFieldError() {
    return this.bindingResult.getFieldError();
  }

  @Override
  public boolean hasFieldErrors(String field) {
    return this.bindingResult.hasFieldErrors(field);
  }

  @Override
  public int getFieldErrorCount(String field) {
    return this.bindingResult.getFieldErrorCount();
  }

  @Override
  public List<FieldError> getFieldErrors(String field) {
    return this.bindingResult.getFieldErrors(field);
  }

  @Override
  public FieldError getFieldError(String field) {
    return this.bindingResult.getFieldError(field);
  }

  @Override
  public Object getFieldValue(String field) {
    return this.bindingResult.getFieldValue(field);
  }

  @Override
  public Class<?> getFieldType(String field) {
    return this.bindingResult.getFieldType(field);
  }

  @Override
  public Object getTarget() {
    return this.bindingResult.getTarget();
  }

  @Override
  public Map<String, Object> getModel() {
    return this.bindingResult.getModel();
  }

  @Override
  public Object getRawFieldValue(String field) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public PropertyEditor findEditor(String field, Class<?> valueType) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public PropertyEditorRegistry getPropertyEditorRegistry() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void addError(ObjectError error) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public String[] resolveMessageCodes(String errorCode) {
    return this.bindingResult.resolveMessageCodes(errorCode);
  }

  @Override
  public String[] resolveMessageCodes(String errorCode, String field) {
    return this.bindingResult.resolveMessageCodes(errorCode, field);
  }

  @Override
  public void recordSuppressedField(String field) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public String[] getSuppressedFields() {
    return this.bindingResult.getSuppressedFields();
  }

  @Override
  public String getObjectName() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setNestedPath(String nestedPath) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public String getNestedPath() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void pushNestedPath(String subPath) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void popNestedPath() throws IllegalStateException {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void reject(String errorCode) {
    // TODO Auto-generated method stub
    
  }

}
