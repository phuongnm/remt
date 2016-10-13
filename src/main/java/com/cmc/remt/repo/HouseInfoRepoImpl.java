package com.cmc.remt.repo;

import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;

import com.cmc.remt.domain.HouseInfo;
import com.cmc.remt.domain.QHouseInfo;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;

@RooJpaRepositoryCustomImpl(repository = HouseInfoRepoCustom.class)
public class HouseInfoRepoImpl extends QueryDslRepositorySupport{

    HouseInfoRepoImpl() {
        super(HouseInfo.class);
    }
    
    private JPQLQuery getQueryFrom(QHouseInfo qEntity){
        return from(qEntity);
    }
    
    public String callTest(){
    	return "aaaa";
    }
    
  
}