package com.jiahz.community.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author: jiahz
 * @Date: 2023/1/26 22:20
 */
@Data
public class LoginTicket {

    private Integer id;
    private Integer userId;
    private String ticket;
    private Integer status;
    private Date expired;
}
