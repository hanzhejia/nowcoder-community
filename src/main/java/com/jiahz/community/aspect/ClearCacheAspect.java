package com.jiahz.community.aspect;

import com.jiahz.community.util.RedisKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * ClassName: ClearCacheAspect
 *
 * @Author: jiahz
 * @Date: 2023/3/1 21:43
 * @Description:
 */
@Component
@Aspect
@Slf4j
public class ClearCacheAspect {

    @Autowired
    private RedisTemplate redisTemplate;

    @Pointcut("execution(* com.jiahz.community.mapper.UserMapper.update*(..))")
    public void pointcut() {
    }

    @After("pointcut()")
    public void clearCache(JoinPoint joinPoint) {
        int userId = (int) joinPoint.getArgs()[0];
        String userKey = RedisKeyUtil.getUserKey(userId);
        redisTemplate.delete(userKey);
    }

}
