package com.cmc.remt.repo;
import com.cmc.remt.domain.HousePicture;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;

@RooJpaRepository(entity = HousePicture.class)
public interface HousePictureRepo {
}
