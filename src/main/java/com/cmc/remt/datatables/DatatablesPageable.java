package com.cmc.remt.datatables;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleafDatatablesPageable;

@RooThymeleafDatatablesPageable
public class DatatablesPageable extends PageRequest {

  private static final long serialVersionUID = -5222098249548875453L;

  public DatatablesPageable(Pageable pageable) {
    super(pageable.getPageNumber() / pageable.getPageSize(), pageable.getPageSize(),
        pageable.getSort());
  }

}