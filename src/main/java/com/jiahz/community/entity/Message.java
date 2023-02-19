package com.jiahz.community.entity;

import lombok.Data;

import java.util.Date;

/**
 * Message
 *
 * @Author: jiahz
 * @Date: 2023/2/18 18:07
 * @Description:
 */
@Data
public class Message {
    private Integer id;
    private Integer fromId;
    private Integer toId;
    private String conversationId;
    private String content;
    private Integer status;
    private Date createTime;
}
