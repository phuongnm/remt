package com.cmc.remt.datatables;

import java.util.List;

import org.springframework.data.domain.Page;

import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleafDatatablesData;

/**
 * Datatables Thymeleaf integration
 * 
 * Response data for data requests datatables component. This class will be 
 * converted to JSON, so the property names must follow the name of the
 * properties expected by datatables.
 * 
 * For more information see: https://datatables.net/manual/server-side
 * 
 * @author Spring Roo
 *
 * @param <T> Response data type
 */
@RooThymeleafDatatablesData
public class DatatablesData<T> {

  private List<T> data;
  private Long recordsTotal;
  private Long recordsFiltered;
  private Integer draw;
  private String error;

  /**
   * Create a response for datatables with data obtained from a previous request.
   *
   * @param data the data to show
   * @param recordsTotal the total number of available data
   * @param recordsFiltered the number of data after filtering
   * @param draw counts datatables requests. It must be sent by datatables value 
   * in the data request.
   */
  public DatatablesData(List<T> data, Long recordsTotal, Long recordsFiltered, Integer draw) {
    this(data, recordsTotal, recordsFiltered, draw, null);
  }

  /**
   * Create an error response to datatables.
   * 
   * @param draw counts datatables requests. It must be sent by datatables value 
   * in the data request.
   * @param error the error produced to inform the user
   */
  public DatatablesData(Integer draw, String error) {
    this(null, null, null, draw, error);
  }

  /**
   * Create an answer for datatables with data obtained from a previous request.
   *
   * @param data the data to show
   * @param recordsTotal the total number of available data
   * @param recordsFiltered the number of data after filtering
   * @param draw counts datatables requests. It must be sent by datatables value 
   * in the data request.
   * @param error (optional) the produced error to inform the user
   */
  public DatatablesData(List<T> data, Long recordsTotal, Long recordsFiltered, Integer draw,
      String error) {
    this.data = data;
    this.recordsTotal = recordsTotal;
    this.recordsFiltered = recordsFiltered;
    this.draw = draw;
    this.error = error;
  }

  /**
   * Create an answer for datatables with data obtained from a previous request.
   *
   * @param dataPage the page of the obtained data
   * @param recordsTotal the total number of data with no filter and no pagination
   * @param draw counts datatables requests. It must be sent by datatables value 
   * in the data request.
   */
  public DatatablesData(Page<T> dataPage, Long recordsTotal, Integer draw) {
    this(dataPage.getContent(), recordsTotal, dataPage.getTotalElements(), draw);
  }

  public List<T> getData() {
    return data;
  }

  public Long getRecordsTotal() {
    return recordsTotal;
  }

  public Long getRecordsFiltered() {
    return recordsFiltered;
  }

  public Integer getDraw() {
    return draw;
  }

  public String getError() {
    return error;
  }
}