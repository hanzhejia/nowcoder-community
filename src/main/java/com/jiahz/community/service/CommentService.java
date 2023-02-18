package com.jiahz.community.service;

import com.jiahz.community.entity.Comment;
import com.jiahz.community.mapper.CommentMapper;
import com.jiahz.community.mapper.DiscussPostMapper;
import com.jiahz.community.util.EntityTypeEnum;
import com.jiahz.community.util.SensitiveWordsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * CommentService
 *
 * @Author: jiahz
 * @Date: 2023/2/18 13:13
 * @Description:
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private SensitiveWordsFilter sensitiveWordsFilter;

    public List<Comment> getCommentByEntity(int entityType, int entityId, int offset, int limit) {
        return commentMapper.selectCommentByEntity(entityType, entityId, offset, limit);
    }

    public int getCountByEntity(int entityType, int entityId) {
        return commentMapper.selectCountByEntity(entityType, entityId);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int addComment(Comment comment) {
        if (comment == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        // 敏感词过滤
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveWordsFilter.filter(comment.getContent()));
        // 添加评论
        int rows = commentMapper.insertComment(comment);
        // 更新帖子评论数量
        if (comment.getEntityType() == EntityTypeEnum.ENTITY_TYPE_POST.getEntityType()) {
            int count = commentMapper.selectCountByEntity(comment.getEntityType(), comment.getEntityId());
            discussPostMapper.updateCommentCount(comment.getEntityId(), count);
        }
        return rows;
    }

}
