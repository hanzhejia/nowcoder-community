package com.jiahz.community.mapper;

import com.jiahz.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * InterfaceName: DiscussPostMapper
 *
 * @Author: jiahz
 * @Date: 2023/1/19 14:55
 * @Description:
 */
@Mapper
public interface DiscussPostMapper {

    List<DiscussPost> selectDiscussPosts(int userId, int offset, int size);

    //如果只有一个参数，且在动态sql标签中使用，则必须加别名@Param
    int selectDiscussPostCount(@Param("userId") int userId);

    int insertDiscussPost(DiscussPost discussPost);

    DiscussPost selectDiscussPostById(@Param("id") int id);

    int updateCommentCount(int id, int commentCount);
}
