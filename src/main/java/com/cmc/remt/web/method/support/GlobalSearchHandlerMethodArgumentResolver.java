package com.cmc.remt.web.method.support;

import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import com.cmc.remt.repo.GlobalSearch;

import org.springframework.roo.addon.web.mvc.controller.annotations.RooGlobalSearchHandler;

@RooGlobalSearchHandler
public class GlobalSearchHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

  private static final String DEFAULT_REGEXP_PARAMETER = "search[regex]";

  private static final String DEFAULT_SEARCH_VALUE_PARAMETER = "search[value]";

  private String searchValueParameter = DEFAULT_SEARCH_VALUE_PARAMETER;

  private String regexpParameter = DEFAULT_REGEXP_PARAMETER;;

  public String getSearchValueParameter() {
    return searchValueParameter;
  }

  public void setSearchValueParameter(String searchValueParameter) {
    this.searchValueParameter = searchValueParameter;
  }

  public String getRegexpParameter() {
    return regexpParameter;
  }

  public void setRegexpParameter(String regexpParameter) {
    this.regexpParameter = regexpParameter;
  }

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return GlobalSearch.class.equals(parameter.getParameterType());
  }

  @Override
  public GlobalSearch resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

    String searchValue = webRequest.getParameter(getSearchValueParameter());
    if (StringUtils.isEmpty(searchValue)) {
      return null;
    }
    String regexp = webRequest.getParameter(getRegexpParameter());
    if ("true".equalsIgnoreCase(regexp)) {
      return new GlobalSearch(searchValue, true);
    } else if ("false".equalsIgnoreCase(regexp)) {
      return new GlobalSearch(searchValue, false);
    }

    return new GlobalSearch(searchValue);
  }

}