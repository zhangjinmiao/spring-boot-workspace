package com.jimzhang.controller;

import com.jimzhang.config.TokenResult;
import com.jimzhang.entity.TokenInfoEntity;
import com.jimzhang.entity.UserInfoEntity;
import com.jimzhang.jpa.TokenJpa;
import com.jimzhang.jpa.UserInfoJpa;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author jimzhang
 * @version V1.0.0
 * @description
 * @home <>https://segmentfault.com/u/itzhangjm</>
 * @date 2017-12-25 10:30
 */
@RestController
@RequestMapping(value = "/jwt")
public class TokenController {
    @Autowired
    private TokenJpa tokenJpa;

    @Autowired
    private UserInfoJpa userInfoJpa;

    /**
     * 获取token，更新token
     *
     * @param appId     用户编号
     * @param appSecret 用户密码
     * @return
     */
    @RequestMapping(value = "/token", method = {RequestMethod.POST, RequestMethod.GET})
    public TokenResult token
    (
            @RequestParam String appId,
            @RequestParam String appSecret
    ) {
        TokenResult token = new TokenResult();
        //appId is null
        if (appId == null || appId.trim() == "") {
            token.setFlag(false);
            token.setMsg("appId is not found!");
        }
        //appSecret is null
        else if (appSecret == null || appSecret.trim() == "") {
            token.setFlag(false);
            token.setMsg("appSecret is not found!");
        } else {
            //根据appId查询用户实体l
            UserInfoEntity userDbInfo = userInfoJpa.findOne(new Specification<UserInfoEntity>() {
                @Override
                public Predicate toPredicate(Root<UserInfoEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    criteriaQuery.where(criteriaBuilder.equal(root.get("appId"), appId));
                    return null;
                }
            });
            //如果不存在
            if (userDbInfo == null) {
                token.setFlag(false);
                token.setMsg("appId : " + appId + ", is not found!");
            }
            //验证appSecret是否存在
            else if (!new String(userDbInfo.getAppSecret()).equals(appSecret.replace(" ", "+"))) {
                token.setFlag(false);
                token.setMsg("appSecret is not effective!");
            } else {
                //检测数据库是否存在该appId的token值
                TokenInfoEntity tokenDBEntity = tokenJpa.findOne(new Specification<TokenInfoEntity>() {
                    @Override
                    public Predicate toPredicate(Root<TokenInfoEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                        criteriaQuery.where(criteriaBuilder.equal(root.get("appId"), appId));
                        return null;
                    }
                });
                //返回token值
                String tokenStr = null;
                //tokenDBEntity == null -> 生成newToken -> 保存数据库 -> 写入内存 -> 返回newToken
                if (tokenDBEntity == null) {
                    //生成jwt,Token
                    tokenStr = createNewToken(appId);
                    //将token保持到数据库
                    tokenDBEntity = new TokenInfoEntity();
                    tokenDBEntity.setAppId(userDbInfo.getAppId());
                    tokenDBEntity.setBuildTime(String.valueOf(System.currentTimeMillis()));
                    tokenDBEntity.setToken(tokenStr.getBytes());
                    tokenJpa.save(tokenDBEntity);
                }
                //tokenDBEntity != null -> 验证是否超时 ->
                //不超时 -> 直接返回dbToken
                //超时 -> 生成newToken -> 更新dbToken -> 更新内存Token -> 返回newToken
                else {
                    //判断数据库中token是否过期，如果没有过期不需要更新直接返回数据库中的token即可
                    //数据库中生成时间
                    long dbBuildTime = Long.valueOf(tokenDBEntity.getBuildTime());
                    //当前时间
                    long currentTime = System.currentTimeMillis();
                    //如果当前时间 - 数据库中生成时间 < 7200 证明可以正常使用
                    long second = TimeUnit.MILLISECONDS.toSeconds(currentTime - dbBuildTime);
                    if (second > 0 && second < 7200) {
                        tokenStr = new String(tokenDBEntity.getToken());
                    }
                    //超时
                    else {
                        //生成newToken
                        tokenStr = createNewToken(appId);
                        //更新token
                        tokenDBEntity.setToken(tokenStr.getBytes());
                        //更新生成时间
                        tokenDBEntity.setBuildTime(String.valueOf(System.currentTimeMillis()));
                        //执行更新
                        tokenJpa.save(tokenDBEntity);
                    }
                }
                //设置返回token
                token.setToken(tokenStr);
            }
        }
        return token;
    }

    /**
     * 创建新token
     *
     * @param appId
     * @return
     */
    private String createNewToken(String appId) {
        //获取当前时间
        Date now = new Date(System.currentTimeMillis());
        //过期时间
        Date expiration = new Date(now.getTime() + 7200000);
        return Jwts
                .builder()
                .setSubject(appId)
                //.claim(YAuthConstants.Y_AUTH_ROLES, userDbInfo.getRoles())
                .setIssuedAt(now)
                .setIssuer("Online YAuth Builder")
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, "HengYuAuthv1.0.0")
                .compact();
    }
}
