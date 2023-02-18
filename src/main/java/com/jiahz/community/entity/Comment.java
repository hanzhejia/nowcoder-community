package com.jiahz.community.entity;

import lombok.Data;

import java.util.Date;

/**
 * Comment
 *
 * @Author: jiahz
 * @Date: 2023/2/18 13:03
 * @Description:
 */
@Data
public class Comment {

    private Integer id;
    private Integer userId;
    private Integer entityType;
    private Integer entityId;
    private Integer targetId;
    private String content;
    private Integer status;
    private Date createTime;

}
