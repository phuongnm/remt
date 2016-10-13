package com.cmc.remt.http.converter.json;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import org.springframework.roo.addon.web.mvc.controller.annotations.http.converters.json.RooJSONConversionServiceBeanSerializerModifier;


/**
 * Configure Jackson 2 seralizers for a bean.
 * 
 * If it's possible, register a Serializer based on {@link ConversionService}
 * registered conversions.
 * 
 * If a property already has a serialized configured (normally by
 * {@link JsonSerialize#using()}) this will be use.
 * 
 * Also, if {@link JsonSerialize#as()} is set, it tries to use this class as
 * serialize target class.
 *
 */
@RooJSONConversionServiceBeanSerializerModifier
public class ConversionServiceBeanSerializerModifier extends BeanSerializerModifier {

  private final ConversionService conversionService;

  public ConversionServiceBeanSerializerModifier(ConversionService conversionService) {
    super();
    this.conversionService = conversionService;
  }

  @Override
  public List<BeanPropertyWriter> changeProperties(SerializationConfig config,
      BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {

    // We need the BeanPropertyDefinition to get the related Field
    List<BeanPropertyDefinition> properties = beanDesc.findProperties();
    Map<String, BeanPropertyDefinition> propertyDefMap =
        new HashMap<String, BeanPropertyDefinition>();
    for (BeanPropertyDefinition property : properties) {
      propertyDefMap.put(property.getName(), property);
    }

    // iterate over bean's properties to configure serializers
    for (int i = 0; i < beanProperties.size(); i++) {
      BeanPropertyWriter beanPropertyWriter = beanProperties.get(i);
      Class<?> propertyType = beanPropertyWriter.getPropertyType();

      if (beanPropertyWriter.hasSerializer()) {
        continue;
      }

      // For conversion between collection, array, and map types,
      // ConversionService.canConvert() method will return 'true'
      // but better to delegate in default Jackson property writer for
      // right start and ends markers serialization and iteration
      if (propertyType.isArray() || Collection.class.isAssignableFrom(propertyType)
          || Map.class.isAssignableFrom(propertyType)) {

        // Don't set ConversionService serializer, let Jackson
        // use default Collection serializer
        continue;
      } else {

        // ConversionService uses value Class plus related Field
        // annotations to be able to select the right converter,
        // so we must get/ the Field annotations for success
        // formatting
        BeanPropertyDefinition propertyDef = propertyDefMap.get(beanPropertyWriter.getName());
        AnnotatedField annotatedField = propertyDef.getField();
        if (annotatedField == null) {
          continue;
        }
        AnnotatedElement annotatedEl = annotatedField.getAnnotated();

        // Field contains info about Annotations, info that
        // ConversionService uses for success formatting, use it if
        // available. Otherwise use the class of given value.
        TypeDescriptor sourceType = annotatedEl != null ? new TypeDescriptor((Field) annotatedEl)
            : TypeDescriptor.valueOf(propertyType);

        TypeDescriptor targetType = TypeDescriptor.valueOf(String.class);
        if (beanPropertyWriter.getSerializationType() != null) {
          targetType =
              TypeDescriptor.valueOf(beanPropertyWriter.getSerializationType().getRawClass());
        }
        if (ObjectUtils.nullSafeEquals(sourceType, targetType)) {
          // No conversion needed
          continue;
        } else if (sourceType.getObjectType() == Object.class
            && targetType.getObjectType() == String.class
            && beanPropertyWriter.getSerializationType() == null) {
          // Can't determine source type and no target type has been
          // configure. Delegate on jackson.
          continue;
        }

        // All other converters must be set in ConversionService
        if (this.conversionService.canConvert(sourceType, targetType)) {

          // We must create BeanPropertyWriter own Serializer that
          // has knowledge about the Field related to that
          // BeanPropertyWriter in order to have access to
          // Field Annotations for success serialization
          JsonSerializer<Object> jsonSerializer = new ConversionServicePropertySerializer(
              this.conversionService, sourceType, targetType);

          beanPropertyWriter.assignSerializer(jsonSerializer);
        }
        // If no converter set, use default Jackson property writer
        else {
          continue;
        }
      }
    }
    return beanProperties;
  }
}
