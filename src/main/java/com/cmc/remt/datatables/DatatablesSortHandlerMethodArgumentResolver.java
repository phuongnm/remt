package com.cmc.remt.datatables;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleafDatatablesSortHandler;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

@RooThymeleafDatatablesSortHandler
public class DatatablesSortHandlerMethodArgumentResolver extends SortHandlerMethodArgumentResolver {

  private static final int MAX_ORDERED_COLUMNS = 5;

  private final int maxOrderedColumns;

  public DatatablesSortHandlerMethodArgumentResolver() {
    this(MAX_ORDERED_COLUMNS);
  }

  public DatatablesSortHandlerMethodArgumentResolver(int maxOrderedColumns) {
    this.maxOrderedColumns = maxOrderedColumns;
  }

  @Override
  public DatatablesSort resolveArgument(MethodParameter parameter,
      ModelAndViewContainer mavContainer,
      NativeWebRequest request, WebDataBinderFactory binderFactory) {

    DatatablesSort sort =
        new SortParametersParser(maxOrderedColumns, request.getParameterMap()).getSort();

    return sort;
  }

  static class SortParametersParser {

    private static final Pattern PATTERN = Pattern.compile("order\\[([0-9]*)?\\]\\[column\\]");

    private final int maxColumnCount;
    private Map<String, String[]> parameters;

    public SortParametersParser(int maxOrderedColumns, Map<String, String[]> parameters) {
      this.maxColumnCount = maxOrderedColumns;
      this.parameters = parameters;
    }

    int getColumnCount() {

      if (parameters == null || parameters.isEmpty()) {
        return 0;
      }

      int columnCount = -1;
      for (String paramName : parameters.keySet()) {
        int columnNumber = getColumnPosition(paramName);
        if (columnNumber > columnCount) {
          columnCount = columnNumber;
        }
      }

      // The column array is zero based
      columnCount++;

      // Just in case there is an error or someone is tampering with the parameters
      return columnCount > maxColumnCount ? maxColumnCount : columnCount;
    }

    static int getColumnPosition(String paramName) {
      Matcher matcher = PATTERN.matcher(paramName);
      while (matcher.find()) {
        try {
          return Integer.parseInt(matcher.group(1));
        } catch (NumberFormatException ex) {
          // Ignore number, it has a format error or its is not a number
        }
      }
      return -1;
    }

    String getPropertyNameInOrderPosition(int pos) {
      String columnPosition = getParameter("order[" + pos + "][column]");

      if (columnPosition == null) {
        return null;
      }

      return getParameter("columns[" + columnPosition + "][data]");
    }

    Direction getOrderDirection(int pos) {
      String direction = getParameter("order[" + pos + "][dir]");
      if ("desc".equals(direction)) {
        return Direction.DESC;
      }
      return Direction.ASC;
    }

    Order getOrderInPosition(int pos) {
      String propertyName = getPropertyNameInOrderPosition(pos);
      if (propertyName == null) {
        return null;
      }
      Direction direction = getOrderDirection(pos);
      return new Order(direction, propertyName);
    }

    public DatatablesSort getSort() {
      int columnCount = getColumnCount();

      if (columnCount <= 0) {
        return null;
      }

      List<Order> orderList = new ArrayList<Order>(columnCount);

      for (int i = 0; i < columnCount; i++) {
        Order order = getOrderInPosition(i);
        if (order != null) {
          orderList.add(order);
        }
      }

      if (orderList.size() == 0) {
        return null;
      }

      return new DatatablesSort(orderList);
    }

    private String getParameter(String name) {
      String[] values = parameters.get(name);
      return (values == null || values.length == 0) ? null : values[0];
    }
  }

}