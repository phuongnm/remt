package com.cmc.remt.validation;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import org.springframework.roo.addon.web.mvc.controller.annotations.RooCollectionValidator;

/**
 * Spring {@link Validator} that iterates over the elements of a {@link Collection} and run the 
 * validation process for each of them individually.
 * 
 */
@Component
@RooCollectionValidator
public class CollectionValidator implements Validator {

  private final Validator validator;

  @Autowired
  public CollectionValidator(LocalValidatorFactoryBean validatorFactory) {
    this.validator = validatorFactory;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return Collection.class.isAssignableFrom(clazz);
  }

  /**
   * Validate each element inside the supplied {@link Collection}.
   * 
   * The supplied errors instance is used to report the validation errors.
   * 
   * @param target the collection that is to be validated
   * @param errors contextual state about the validation process
   */
  @Override
  @SuppressWarnings("rawtypes")
  public void validate(Object target, Errors errors) {
    Collection collection = (Collection) target;
    int index = 0;

    for (Object object : collection) {
      BeanPropertyBindingResult elementErrors = new BeanPropertyBindingResult(object,
          errors.getObjectName());
      elementErrors.setNestedPath("[".concat(Integer.toString(index++)).concat("]."));
      ValidationUtils.invokeValidator(validator, object, elementErrors);

      errors.addAllErrors(elementErrors);
    }
  }

}
