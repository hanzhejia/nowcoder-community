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


}
