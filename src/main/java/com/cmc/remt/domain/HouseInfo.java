package com.cmc.remt.domain;
import org.springframework.roo.addon.javabean.annotations.RooJavaBean;
import org.springframework.roo.addon.javabean.annotations.RooToString;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaEntity;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RooJavaBean
@RooToString
@RooJpaEntity(sequenceName = "seq_house_info", schema = "remt")
public class HouseInfo {

    /**
     */
    @NotNull
    @Column(name = "house_name")
    @Size(min = 2)
    private String houseName;

    /**
     */
    @NotNull
    private String houseNumber;

    /**
     */
    private Boolean status;

    /**
     */
    private String city;

    /**
     */
    private String country;

    /**
     */
    private String house_category;
}
