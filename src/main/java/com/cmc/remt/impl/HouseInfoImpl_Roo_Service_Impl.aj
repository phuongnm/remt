// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.cmc.remt.impl;

import com.cmc.remt.domain.HouseInfo;
import com.cmc.remt.impl.HouseInfoImpl;
import com.cmc.remt.repo.GlobalSearch;
import com.cmc.remt.repo.HouseInfoRepo;
import com.cmc.remt.svr.HouseInfoSvr;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

privileged aspect HouseInfoImpl_Roo_Service_Impl {
    
    declare parents: HouseInfoImpl implements HouseInfoSvr;
    
    declare @type: HouseInfoImpl: @Service;
    
    declare @type: HouseInfoImpl: @Transactional(readOnly = true);
    
    public HouseInfoRepo HouseInfoImpl.houseInfoRepo;
    
    @Autowired
    public HouseInfoImpl.new(HouseInfoRepo houseInfoRepo) {
        this.houseInfoRepo = houseInfoRepo;
    }

    @Transactional(readOnly = false)
    public HouseInfo HouseInfoImpl.save(HouseInfo entity) {
        return houseInfoRepo.save(entity);
    }
    
    @Transactional(readOnly = false)
    public void HouseInfoImpl.delete(Long id) {
         houseInfoRepo.delete(id);
    }
    
    @Transactional(readOnly = false)
    public List<HouseInfo> HouseInfoImpl.save(Iterable<HouseInfo> entities) {
        return houseInfoRepo.save(entities);
    }
    
    @Transactional(readOnly = false)
    public void HouseInfoImpl.delete(Iterable<Long> ids) {
        List<HouseInfo> toDelete = houseInfoRepo.findAll(ids);
        houseInfoRepo.deleteInBatch(toDelete);
    }
    
    public List<HouseInfo> HouseInfoImpl.findAll() {
        return houseInfoRepo.findAll();
    }
    
    public List<HouseInfo> HouseInfoImpl.findAll(Iterable<Long> ids) {
        return houseInfoRepo.findAll(ids);
    }
    
    public HouseInfo HouseInfoImpl.findOne(Long id) {
        return houseInfoRepo.findOne(id);
    }
    
    public long HouseInfoImpl.count() {
        return houseInfoRepo.count();
    }
    
    public Page<HouseInfo> HouseInfoImpl.findAll(GlobalSearch globalSearch, Pageable pageable) {
        return houseInfoRepo.findAll(globalSearch, pageable);
    }
    
}
