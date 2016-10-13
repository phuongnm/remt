package com.cmc.remt.http.converter.json;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBase;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.NameTransformer;

import org.springframework.roo.addon.web.mvc.controller.annotations.http.converters.json.RooJSONDataBinderDeserializer;

/**
 * Jackson 2 {@link JsonDeserializer} based on Spring DataBinder.
 * 
 */
@RooJSONDataBinderDeserializer
public class DataBinderDeserializer extends BeanDeserializerBase {

  private static final long serialVersionUID = -7345091954698956061L;
  private static final Logger LOGGER = LoggerFactory.getLogger(DataBinderDeserializer.class);

  private ConversionService conversionService;

  /**
   * Specific validator for JSR-303 y JSR-349. A {@link Validator} is not used because the goal
   * is to validate model classes by its JSR-303/349 annotations.
   */
  @Autowired
  private LocalValidatorFactoryBean validatorFactory;

  public DataBinderDeserializer(BeanDeserializerBase source, ConversionService cs,
      LocalValidatorFactoryBean vf) {
    super(source);
    this.conversionService = cs;
    this.validatorFactory = vf;
  }

  public DataBinderDeserializer(BeanDeserializerBase source, ObjectIdReader objectIdReader) {
    super(source, objectIdReader);
  }

  public DataBinderDeserializer(BeanDeserializerBase source, HashSet<String> ignorableProps) {
    super(source, ignorableProps);
  }

  /**
   * {@inheritDoc}
   * 
   * Uses {@link DataBinderDeserializer}
   */
  @Override
  public BeanDeserializerBase withObjectIdReader(ObjectIdReader objectIdReader) {
    return new DataBinderDeserializer(this, objectIdReader);
  }

  /**
   * {@inheritDoc}
   * 
   * Uses {@link DataBinderDeserializer}
   */
  @Override
  public BeanDeserializerBase withIgnorableProperties(HashSet<String> ignorableProps) {
    return new DataBinderDeserializer(this, ignorableProps);
  }

  /**
   * Deserializes JSON content into Map<String, String> format and then uses a
   * Spring {@link DataBinder} to bind the data from JSON message to JavaBean
   * objects.
   * 
   * @param parser Parsed used for reading JSON content
   * @param ctxt Context that can be used to access information about this
   *        deserialization activity.
   * 
   * @return Deserializer value
   */
  @Override
  public Object deserialize(JsonParser parser, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    JsonToken t = parser.getCurrentToken();
    MutablePropertyValues propertyValues = new MutablePropertyValues();

    // Get target from DataBinder from local thread. If its a bean
    // collection
    // prepares array index for property names. Otherwise continue.

    //    DataBinder binder = (DataBinder) ThreadLocalUtil
    //        .getThreadVariable(BindingResult.MODEL_KEY_PREFIX.concat("JSON_DataBinder"));
    DataBinder binder = new DataBinder(ClassUtil.createInstance(handledType(), false));
    binder.setConversionService(this.conversionService);
    binder.setValidator(this.validatorFactory);

    Object target = binder.getTarget();

    if (t == JsonToken.START_OBJECT) {
      String prefix = null;
      if (target instanceof Map) {
        // TODO
        LOGGER.warn("Map deserialization not implemented yet!");
      }
      Map<String, String> obj = readObject(parser, ctxt, prefix);
      propertyValues.addPropertyValues(obj);
    } else {
      LOGGER.warn("Deserialization for non-object not implemented yet!");
      return null; // TODO?
    }

    // bind to the target object
    binder.bind(propertyValues);

    // Note there is no need to validate the target object because
    // RequestResponseBodyMethodProcessor.resolveArgument() does it on top
    // of including BindingResult as Model attribute.
    // If there were conversion errors an Exception will be thrown in order to notify it
    // in the same way that Jackson 2 do.
    if (binder.getBindingResult().hasErrors()) {
      throw new BindingErrorException(binder.getBindingResult());
    }
    return binder.getTarget();
  }

  @Override
  public Object deserialize(JsonParser p, DeserializationContext ctxt, Object bean)
      throws IOException {
    return super.deserialize(p, ctxt, bean);
  }

  /**
   * Deserializes JSON object into Map<String, String> format to use it in a
   * Spring {@link DataBinder}.
   * <p/>
   * Iterate over every object's property and delegates on
   * {@link #readField(JsonParser, DeserializationContext, JsonToken, String)}
   * 
   * @param parser JSON parser
   * @param ctxt context
   * @param prefix object DataBinder path
   * @return property values
   * @throws IOException
   * @throws JsonProcessingException
   */
  public Map<String, String> readObject(JsonParser parser, DeserializationContext ctxt,
      String prefix) throws IOException, JsonProcessingException {
    JsonToken t = parser.getCurrentToken();

    if (t == JsonToken.START_OBJECT) {
      t = parser.nextToken();
      // Skip it to locate on first object data token
    }

    // Deserialize object properties
    Map<String, String> deserObj = new HashMap<String, String>();
    for (; t != JsonToken.END_OBJECT; t = parser.nextToken()) {
      Map<String, String> field = readField(parser, ctxt, t, prefix);
      deserObj.putAll(field);
    }
    return deserObj;
  }

  /**
   * Deserializes JSON array into Map<String, String> format to use it in a
   * Spring {@link DataBinder}.
   * <p/>
   * Iterate over every array's item to generate a prefix for property names
   * on DataBinder style (
   * <em>{prefix}[{index}].<em>) and delegates on {@link #readField(JsonParser, DeserializationContext, JsonToken, String)}
   * 
   * @param parser JSON parser
   * @param ctxt context
   * @param prefix array dataBinder path
   * @return
   * @throws IOException
   * @throws JsonProcessingException
   */
  protected Map<String, String> readArray(JsonParser parser, DeserializationContext ctxt,
      String prefix) throws IOException, JsonProcessingException {
    JsonToken t = parser.getCurrentToken();

    if (t == JsonToken.START_ARRAY) {
      t = parser.nextToken();
      // Skip it to locate on first array data token
    }

    // Deserialize array properties
    int i = 0;
    Map<String, String> deserObj = new HashMap<String, String>();
    for (; t != JsonToken.END_ARRAY; t = parser.nextToken()) {
      // Property name must include prefix this way:
      // degrees[0].description
      Map<String, String> field =
          readField(parser, ctxt, t, prefix.concat("[").concat(Integer.toString(i++)).concat("]."));
      deserObj.putAll(field);
    }
    return deserObj;
  }

  /**
   * Deserializes JSON property into Map<String, String> format to use it in a
   * Spring {@link DataBinder}.
   * <p/>
   * Check token's type to perform an action:
   * <ul>
   * <li>If it's a property, stores it in map</li>
   * <li>If it's an object, calls to
   * {@link #readObject(JsonParser, DeserializationContext, String)}</li>
   * <li>If it's an array, calls to
   * {@link #readArray(JsonParser, DeserializationContext, String)}</li>
   * </ul>
   * 
   * @param parser
   * @param ctxt
   * @param token current token
   * @param prefix property dataBinder path
   * @return
   * @throws IOException
   * @throws JsonProcessingException
   */
  protected Map<String, String> readField(JsonParser parser, DeserializationContext ctxt,
      JsonToken token, String prefix) throws IOException, JsonProcessingException {

    String fieldName = null;
    String fieldValue = null;

    // Read the field name
    fieldName = parser.getCurrentName();

    // If current token contains a field name
    if (!isEmptyString(fieldName)) {

      // Append the prefix if given
      if (isEmptyString(prefix)) {
        fieldName = parser.getCurrentName();
      } else {
        fieldName = prefix.concat(parser.getCurrentName());
      }
    }
    // If current token contains mark array or object start markers.
    // Note it cannot be a field value because it will be read below and
    // then the token is advanced to the next
    else {

      // Use the prefix in recursive calls
      if (!isEmptyString(prefix)) {
        fieldName = prefix;
      }
    }

    // If current token has been used to read the field name, advance
    // stream to the next token that contains the field value
    if (token == JsonToken.FIELD_NAME) {
      token = parser.nextToken();
    }

    // Field value
    switch (token) {
      case VALUE_STRING:
      case VALUE_NUMBER_INT:
      case VALUE_NUMBER_FLOAT:
      case VALUE_EMBEDDED_OBJECT:
      case VALUE_TRUE:
      case VALUE_FALSE:
        // Plain field: Store value
        Map<String, String> field = new HashMap<String, String>();
        fieldValue = parser.getText();
        field.put(fieldName, fieldValue);
        return field;
      case START_ARRAY:
        // Read array items
        return readArray(parser, ctxt, fieldName);
      case START_OBJECT:
        // Read object properties
        return readObject(parser, ctxt, fieldName);
      case END_ARRAY:
      case END_OBJECT:
        // Skip array and object end markers
        parser.nextToken();
        break;
      default:
        throw ctxt.mappingException(handledType());
    }
    return Collections.emptyMap();
  }

  /**
   * @param string
   * @return true if string is null or is empty (ignore spaces)
   */
  private boolean isEmptyString(String string) {
    return string == null || string.trim().isEmpty();
  }

  /**
   * {@inheritDoc}
   * 
   * Not used
   */
  @Override
  public Object deserializeFromObject(JsonParser jp, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    // Not used
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * Not used
   */
  @Override
  protected BeanDeserializerBase asArrayDeserializer() {
    // Not used
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * Not used
   */
  @Override
  protected Object _deserializeUsingPropertyBased(JsonParser jp, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    // Not used
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   * Not used
   */
  @Override
  public JsonDeserializer<Object> unwrappingDeserializer(NameTransformer unwrapper) {
    // Not used
    return null;
  }

  /**
   * Method that will modify caught exception (passed in as argument)
   * as necessary to include reference information, and to ensure it
   * is a subtype of {@link IOException}, or an unchecked exception.
   *<p>
   * Rules for wrapping and unwrapping are bit complicated; essentially:
   *<ul>
   * <li>Errors are to be passed as is (if uncovered via unwrapping)
   * <li>"Plain" IOExceptions (ones that are not of type
   *   {@link JsonMappingException} are to be passed as is
   *</ul>
   */
  public void wrapAndThrow(Throwable t, Object bean, String fieldName, DeserializationContext ctxt)
      throws IOException {
    // [JACKSON-55] Need to add reference information
    throw JsonMappingException.wrapWithPath(throwOrReturnThrowable(t, ctxt), bean, fieldName);
  }

  private Throwable throwOrReturnThrowable(Throwable t, DeserializationContext ctxt)
      throws IOException {
    /* 05-Mar-2009, tatu: But one nasty edge is when we get
     *   StackOverflow: usually due to infinite loop. But that
     *   often gets hidden within an InvocationTargetException...
     */
    while (t instanceof InvocationTargetException && t.getCause() != null) {
      t = t.getCause();
    }
    // Errors to be passed as is
    if (t instanceof Error) {
      throw (Error) t;
    }
    boolean wrap = (ctxt == null) || ctxt.isEnabled(DeserializationFeature.WRAP_EXCEPTIONS);
    // Ditto for IOExceptions; except we may want to wrap JSON exceptions
    if (t instanceof IOException) {
      if (!wrap || !(t instanceof JsonProcessingException)) {
        throw (IOException) t;
      }
    } else if (!wrap) { // [JACKSON-407] -- allow disabling wrapping for unchecked exceptions
      if (t instanceof RuntimeException) {
        throw (RuntimeException) t;
      }
    }
    return t;
  }

}
