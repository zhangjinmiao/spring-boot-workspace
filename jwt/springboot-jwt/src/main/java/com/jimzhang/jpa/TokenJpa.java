package com.jimzhang.jpa;

import com.jimzhang.entity.TokenInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author jimzhang
 * @version V1.0.0
 * @description
 * @home <>https://segmentfault.com/u/itzhangjm</>
 * @date 2017-12-25 10:28
 */
public interface TokenJpa extends JpaRepository<TokenInfoEntity,String>,JpaSpecificationExecutor<TokenInfoEntity>{
}
