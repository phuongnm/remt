package com.cmc.remt.repo;
import com.cmc.remt.domain.HouseInfo;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustom;

@RooJpaRepositoryCustom(entity = HouseInfo.class, defaultSearchResult = HouseInfo.class)
public interface HouseInfoRepoCustom {
}
