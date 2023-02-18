package com.jiahz.community.mapper;

import com.jiahz.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * CommentMapper
 *
 * @Author: jiahz
 * @Date: 2023/2/18 13:04
 * @Description:
 */
@Mapper
public interface CommentMapper {

    List<Comment> selectCommentByEntity(int entityType, int entityId, int offset, int limit);

    int selectCountByEntity(int entityType, int entityId);

    int insertComment(Comment comment);


}
