package com.zingpay.commission.calculation.repository;

import com.zingpay.commission.calculation.entity.AppUser;

/**
 * @author Bilal Hassan on 01-Feb-21
 * @project commission-calculation-microservice
 */

public interface AppUserRepository extends BaseRepository<AppUser, Integer> {
    AppUser findByUsername(String username);
    AppUser findByParentId(int parentId);
}
