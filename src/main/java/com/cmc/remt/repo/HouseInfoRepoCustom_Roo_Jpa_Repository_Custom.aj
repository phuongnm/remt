// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.cmc.remt.repo;

import com.cmc.remt.domain.HouseInfo;
import com.cmc.remt.repo.GlobalSearch;
import com.cmc.remt.repo.HouseInfoRepoCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

privileged aspect HouseInfoRepoCustom_Roo_Jpa_Repository_Custom {
    
    public abstract Page<HouseInfo> HouseInfoRepoCustom.findAll(GlobalSearch globalSearch, Pageable pageable);    
}