package com.cmc.remt.repo;

import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import com.cmc.remt.domain.HousePicture;
import com.cmc.remt.domain.QHousePicture;
import com.mysema.query.jpa.JPQLQuery;

@RooJpaRepositoryCustomImpl(repository = HousePictureRepoCustom.class)
public class HousePictureRepoImpl extends QueryDslRepositorySupport{

    HousePictureRepoImpl() {
        super(HousePicture.class);
    }
    
    private JPQLQuery getQueryFrom(QHousePicture qEntity){
        return from(qEntity);
    }
}