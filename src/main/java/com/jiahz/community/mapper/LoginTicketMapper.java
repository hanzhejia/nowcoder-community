package com.jiahz.community.mapper;

import com.jiahz.community.entity.LoginTicket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: jiahz
 * @Date: 2023/1/26 22:22
 */
@Mapper
@Deprecated
public interface LoginTicketMapper {

    int insertLoginTicket(LoginTicket loginTicket);

    LoginTicket selectByTicket(@Param("ticket") String ticket);

    int updateStatus(@Param("ticket") String ticket, @Param("status") int status);
}
