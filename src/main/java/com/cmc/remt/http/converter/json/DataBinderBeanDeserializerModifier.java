package com.cmc.remt.http.converter.json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBase;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;

import org.springframework.roo.addon.web.mvc.controller.annotations.http.converters.json.RooJSONDataBinderBeanDeserializerModifier;

/**
 * Jackson {@link BeanDeserializerModifier} which return
 * {@link DataBinderDeserializer}.
 * 
 */
@RooJSONDataBinderBeanDeserializerModifier
public class DataBinderBeanDeserializerModifier extends BeanDeserializerModifier {

  private final ConversionService conversionService;

  /**
   * Specific validator for JSR-303 y JSR-349. A {@link Validator} is not used because the goal
   * is to validate model classes by its JSR-303/349 annotations.
   */
  @Autowired
  private LocalValidatorFactoryBean validatorFactory;

  public DataBinderBeanDeserializerModifier(ConversionService cs, LocalValidatorFactoryBean vf) {
    super();
    this.conversionService = cs;
    this.validatorFactory = vf;
  }

  @Override
  public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config,
      BeanDescription beanDesc, JsonDeserializer<?> deserializer) {

    if (deserializer instanceof BeanDeserializerBase) {
      return new DataBinderDeserializer((BeanDeserializerBase) deserializer, conversionService,
          validatorFactory);
    }

    // When there is no custom-deserializer implementation returns the
    // default jackson deserializer
    return deserializer;
  }

}
