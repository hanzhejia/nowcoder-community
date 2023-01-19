package com.jiahz.community.entity;

import lombok.Data;

import java.util.Date;

/**
 * ClassName: DiscussPost
 *
 * @Author: jiahz
 * @Date: 2023/1/19 14:51
 * @Description:
 */
@Data
public class DiscussPost {

    private Integer id;
    private Integer userId;
    private String title;
    private String content;
    private Integer type;
    private Integer status;
    private Date createTime;
    private Integer commentCount;
    private Double score;

}
