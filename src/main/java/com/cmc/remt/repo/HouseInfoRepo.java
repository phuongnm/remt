package com.cmc.remt.repo;
import com.cmc.remt.domain.HouseInfo;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;

@RooJpaRepository(entity = HouseInfo.class)
public interface HouseInfoRepo {
}
