package com.jiahz.community.service;

import com.jiahz.community.entity.User;
import com.jiahz.community.util.EntityTypeEnum;
import com.jiahz.community.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * ClassName: FollowService
 *
 * @Author: jiahz
 * @Date: 2023/3/1 15:23
 * @Description:
 */
@Service
public class FollowService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    public void follow(int userId, int entityType, int entityId) {
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
                String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);

                operations.multi();

                operations.opsForZSet().add(followeeKey, entityId, System.currentTimeMillis());
                operations.opsForZSet().add(followerKey, userId, System.currentTimeMillis());

                return operations.exec();
            }
        });
    }

    public void unfollow(int userId, int entityType, int entityId) {
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
                String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);

                operations.multi();

                operations.opsForZSet().remove(followeeKey, entityId);
                operations.opsForZSet().remove(followerKey, userId);

                return operations.exec();
            }
        });
    }

    // 查询关注的实体的数量
    public long getFolloweeCount(int userId, int entityType) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        return redisTemplate.opsForZSet().zCard(followeeKey);
    }

    // 查询实体的粉丝数量
    public long getFollowerCount(int entityType, int entityId) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return redisTemplate.opsForZSet().zCard(followerKey);
    }

    // 查询当前用户是否已关注该实体
    public boolean hasFollowed(int userId, int entityType, int entityId) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        return redisTemplate.opsForZSet().score(followeeKey, entityId) != null;
    }

    // 查询某用户关注的人
    public List<Map<String, Object>> getFollowees(int userId, int offset, int limit) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, EntityTypeEnum.ENTITY_TYPE_USER.getEntityType());
        Set<Integer> targetIds = redisTemplate.opsForZSet().reverseRange(followeeKey, offset, offset + limit - 1);
        if (targetIds == null) return null;
        List<Map<String, Object>> list = new ArrayList<>();
        for (Integer targetId : targetIds) {
            Map<String, Object> map = new HashMap<>();
            User user = userService.getUserById(targetId);
            map.put("user", user);
            Double followTime = redisTemplate.opsForZSet().score(followeeKey, targetId);
            map.put("followTime", new Date(followTime.longValue()));
            list.add(map);
        }
        return list;
    }

    // 查询某用户的粉丝
    public List<Map<String, Object>> getFollowers(int userId, int offset, int limit) {
        String followerKey = RedisKeyUtil.getFollowerKey(EntityTypeEnum.ENTITY_TYPE_USER.getEntityType(), userId);
        Set<Integer> targetIds = redisTemplate.opsForZSet().reverseRange(followerKey, offset, offset + limit + 1);
        if (targetIds == null) return null;
        List<Map<String, Object>> list = new ArrayList<>();
        for (Integer targetId : targetIds) {
            Map<String, Object> map = new HashMap<>();
            User user = userService.getUserById(targetId);
            map.put("user", user);
            Double followTime = redisTemplate.opsForZSet().score(followerKey, targetId);
            map.put("followTime", new Date(followTime.longValue()));
            list.add(map);
        }
        return list;
    }
}
