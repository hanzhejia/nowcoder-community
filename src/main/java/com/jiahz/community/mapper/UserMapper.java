package com.jiahz.community.mapper;

import com.jiahz.community.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * InterfaceName: UserMapper
 *
 * @Author: jiahz
 * @Date: 2023/1/19 15:58
 * @Description:
 */
@Mapper
public interface UserMapper {
    User selectUserById(@Param("id") int id);

    User selectUserByUsername(@Param("username") String username);

    User selectUserByEmail(@Param("email") String email);

    int insertUser(User user);

    int updateStatus(@Param("id") int id, @Param("status") int status);
}
