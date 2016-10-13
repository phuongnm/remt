package com.cmc.remt.repo;
import com.cmc.remt.domain.HousePicture;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustom;

@RooJpaRepositoryCustom(entity = HousePicture.class, defaultSearchResult = HousePicture.class)
public interface HousePictureRepoCustom {
}
