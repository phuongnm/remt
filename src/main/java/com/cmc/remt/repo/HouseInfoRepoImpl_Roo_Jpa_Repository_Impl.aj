// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.cmc.remt.repo;

import com.cmc.remt.domain.HouseInfo;
import com.cmc.remt.domain.QHouseInfo;
import com.cmc.remt.repo.GlobalSearch;
import com.cmc.remt.repo.HouseInfoRepoCustom;
import com.cmc.remt.repo.HouseInfoRepoImpl;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.Order;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.path.NumberPath;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

privileged aspect HouseInfoRepoImpl_Roo_Jpa_Repository_Impl {
    
    declare parents: HouseInfoRepoImpl implements HouseInfoRepoCustom;
    
    declare @type: HouseInfoRepoImpl: @Transactional(readOnly = true);
    
    public Page<HouseInfo> HouseInfoRepoImpl.findAll(GlobalSearch globalSearch, Pageable pageable) {
        NumberPath<Long> idHouseInfo = new NumberPath<Long>(Long.class, "id");
        QHouseInfo houseInfo = QHouseInfo.houseInfo;
        JPQLQuery query = getQueryFrom(houseInfo);
        BooleanBuilder where = new BooleanBuilder();

        if (globalSearch != null) {
            String txt = globalSearch.getText();
            where.and(
                houseInfo.houseName.containsIgnoreCase(txt)
                .or(houseInfo.houseNumber.containsIgnoreCase(txt))
                .or(houseInfo.city.containsIgnoreCase(txt))
                .or(houseInfo.country.containsIgnoreCase(txt))
                .or(houseInfo.house_category.containsIgnoreCase(txt))
            );

        }
        query.where(where);

        long totalFound = query.count();
        if (pageable != null) {
            if (pageable.getSort() != null) {
                for (Sort.Order order : pageable.getSort()) {
                    Order direction = order.isAscending() ? Order.ASC : Order.DESC;

                    switch(order.getProperty()){
                        case "houseName":
                           query.orderBy(new OrderSpecifier<String>(direction, houseInfo.houseName));
                           break;
                        case "houseNumber":
                           query.orderBy(new OrderSpecifier<String>(direction, houseInfo.houseNumber));
                           break;
                        case "city":
                           query.orderBy(new OrderSpecifier<String>(direction, houseInfo.city));
                           break;
                        case "country":
                           query.orderBy(new OrderSpecifier<String>(direction, houseInfo.country));
                           break;
                        case "house_category":
                           query.orderBy(new OrderSpecifier<String>(direction, houseInfo.house_category));
                           break;
                    }
                }
            }
            query.offset(pageable.getOffset()).limit(pageable.getPageSize());
        }
        query.orderBy(idHouseInfo.asc());
        
        List<HouseInfo> results = query.list(houseInfo);
        return new PageImpl<HouseInfo>(results, pageable, totalFound);
    }
    
}