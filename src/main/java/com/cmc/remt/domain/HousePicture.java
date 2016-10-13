package com.cmc.remt.domain;
import org.springframework.roo.addon.javabean.annotations.RooJavaBean;
import org.springframework.roo.addon.javabean.annotations.RooToString;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaEntity;
import javax.persistence.Column;
import javax.persistence.ManyToOne;

@RooJavaBean
@RooToString
@RooJpaEntity(sequenceName = "seq_house_picture", schema = "remt")
public class HousePicture {

    /**
     */
    @Column(name = "image_name")
    private String imageName;

    /**
     */
    @Column(name = "image_path")
    private String imagePath;

    /**
     */
    @ManyToOne
    private HouseInfo houseInfo;
}
