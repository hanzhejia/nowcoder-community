package com.jiahz.community.controller;

import com.jiahz.community.entity.Comment;
import com.jiahz.community.entity.DiscussPost;
import com.jiahz.community.entity.Page;
import com.jiahz.community.entity.User;
import com.jiahz.community.service.CommentService;
import com.jiahz.community.service.DiscussPostService;
import com.jiahz.community.service.LikeService;
import com.jiahz.community.service.UserService;
import com.jiahz.community.util.CommunityUtil;
import com.jiahz.community.util.EntityTypeEnum;
import com.jiahz.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * DiscussPostController
 *
 * @Author: jiahz
 * @Date: 2023/2/14 21:11
 * @Description:
 */
@Controller
@RequestMapping("/discuss")
public class DiscussPostController {

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private LikeService likeService;

    @PostMapping("/add")
    @ResponseBody
    public String addDiscussPost(String title, String content) {
        User user = hostHolder.getUser();
        if (user == null) {
            return CommunityUtil.getJSONString(403, "未登录");
        }
        DiscussPost post = new DiscussPost();
        post.setUserId(user.getId());
        post.setTitle(title);
        post.setContent(content);
        post.setType(0);
        post.setStatus(0);
        post.setCreateTime(new Date());
        post.setCommentCount(0);
        post.setScore(0.0);
        discussPostService.addDiscussPost(post);

        return CommunityUtil.getJSONString(0, "发布成功");
    }

    @GetMapping("/detail/{discussPostId}")
    public String getDiscussPost(@PathVariable("discussPostId") int id, Model model, Page page) {
        // 帖子
        DiscussPost post = discussPostService.getDiscussPostById(id);
        model.addAttribute("post", post);
        // 作者
        User user = userService.getUserById(post.getUserId());
        model.addAttribute("user", user);
        // 点赞数量
        long likeCount = likeService.getEntityLikeCount(EntityTypeEnum.ENTITY_TYPE_POST.getEntityType(), post.getId());
        model.addAttribute("likeCount", likeCount);
        // 点赞状态
        int likeStatus = hostHolder.getUser() == null ? 0 :
                likeService.getEntityLikeStatus(hostHolder.getUser().getId(), EntityTypeEnum.ENTITY_TYPE_POST.getEntityType(), id);
        model.addAttribute("likeStatus", likeStatus);

        // 评论
        page.setLimit(5);
        page.setPath("/discuss/detail/" + id);
        page.setRows(post.getCommentCount());

        // 评论: 给帖子的评论; 回复: 给评论的评论
        // 评论列表
        List<Comment> commentList = commentService.getCommentByEntity(
                EntityTypeEnum.ENTITY_TYPE_POST.getEntityType(), post.getId(), page.getOffset(), page.getLimit());

        // 评论VO列表
        List<Map<String, Object>> commentVoList = new ArrayList<>();
        if (commentList != null) {
            for (Comment comment : commentList) {
                // 评论VO
                Map<String, Object> commentVo = new HashMap<>();
                // 评论
                commentVo.put("comment", comment);
                // 作者
                commentVo.put("user", userService.getUserById(comment.getUserId()));
                // 点赞数量
                likeCount = likeService.getEntityLikeCount(EntityTypeEnum.ENTITY_TYPE_COMMENT.getEntityType(), comment.getId());
                commentVo.put("likeCount", likeCount);
                // 点赞状态
                likeStatus = hostHolder.getUser() == null ? 0 :
                        likeService.getEntityLikeStatus(hostHolder.getUser().getId(), EntityTypeEnum.ENTITY_TYPE_COMMENT.getEntityType(), comment.getId());
                commentVo.put("likeStatus", likeStatus);

                // 回复列表
                List<Comment> replyList = commentService.getCommentByEntity(
                        EntityTypeEnum.ENTITY_TYPE_COMMENT.getEntityType(), comment.getId(), 0, Integer.MAX_VALUE);
                // 回复VO列表
                List<Map<String, Object>> replyVoList = new ArrayList<>();
                if (replyList != null) {
                    for (Comment reply : replyList) {
                        Map<String, Object> replyVo = new HashMap<>();
                        // 回复
                        replyVo.put("reply", reply);
                        // 作者
                        replyVo.put("user", userService.getUserById(reply.getUserId()));
                        // 回复目标
                        User target = reply.getTargetId() == 0 ? null : userService.getUserById(reply.getTargetId());
                        replyVo.put("target", target);
                        // 点赞数量
                        likeCount = likeService.getEntityLikeCount(EntityTypeEnum.ENTITY_TYPE_COMMENT.getEntityType(), reply.getId());
                        replyVo.put("likeCount", likeCount);
                        // 点赞状态
                        likeStatus = hostHolder.getUser() == null ? 0 :
                                likeService.getEntityLikeStatus(hostHolder.getUser().getId(), EntityTypeEnum.ENTITY_TYPE_COMMENT.getEntityType(), reply.getId());
                        replyVo.put("likeStatus", likeStatus);
                        replyVoList.add(replyVo);
                    }
                }
                commentVo.put("replys", replyVoList);

                // 回复数量
                int replyCount = commentService.getCountByEntity(
                        EntityTypeEnum.ENTITY_TYPE_COMMENT.getEntityType(), comment.getId());
                commentVo.put("replyCount", replyCount);
                commentVoList.add(commentVo);
            }
        }

        model.addAttribute("comments", commentVoList);

        return "/site/discuss-detail";
    }

}
