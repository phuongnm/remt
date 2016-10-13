package com.cmc.remt.http.converter.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.springframework.roo.addon.web.mvc.controller.annotations.http.converters.json.RooJSONFieldErrorSerializer;

/**
 * {@link FieldError} Jackson 2 Serializer.
 * 
 */
@RooJSONFieldErrorSerializer
public class FieldErrorSerializer extends JsonSerializer<FieldError> {

  private static final Logger LOGGER = LoggerFactory.getLogger(FieldErrorSerializer.class);
  private static final String ERROR_WRITTING_BINDING = "Error writting BindingResult";

  @Override
  public void serialize(FieldError result, JsonGenerator gen, SerializerProvider serializers)
      throws IOException, JsonProcessingException {

    try {

      // Create the result map
      Map<String, Object> bindingResult = new HashMap<String, Object>();
      bindingResult.put("defaultMessage", result.getDefaultMessage());
      bindingResult.put("field", result.getField());
      bindingResult.put("rejectedValue", result.getRejectedValue());
      bindingResult.put("error-code", result.getCode());

      gen.writeObject(bindingResult);
    } catch (JsonProcessingException e) {
      LOGGER.warn(ERROR_WRITTING_BINDING, e);
      throw e;
    } catch (IOException e) {
      LOGGER.warn(ERROR_WRITTING_BINDING, e);
      throw e;
    } catch (Exception e) {
      LOGGER.warn(ERROR_WRITTING_BINDING, e);
      throw new IOException(ERROR_WRITTING_BINDING, e);
    }
  }
}
