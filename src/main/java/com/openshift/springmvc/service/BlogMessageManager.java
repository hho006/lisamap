package com.openshift.springmvc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.openshift.springmvc.dao.BlogMessageDao;
import com.openshift.springmvc.model.BlogMessage;

public class BlogMessageManager {
    
    private final BlogMessageDao dao = new BlogMessageDao();
    
    public void createBlogMessage(final BlogMessage blogMessage) {
        if(blogMessage.getSubject() == null || blogMessage.getSubject().isEmpty()) {
            blogMessage.setSubject("No Subject");
        }
        dao.save(blogMessage);
    }
    
    public void deleteBlogMessage(final BlogMessage blogMessage) {
        dao.delete(blogMessage);
    }
    
    public void updateBlogMessage(final BlogMessage blogMessage) {
        dao.update(blogMessage);
    }
    
    public final List<BlogMessage> getBlogMessagesByUserIdTo(final int userIdTo) {
        final Map<String, Object> critMap = new HashMap<>();
        critMap.put("userIdTo", userIdTo);
        return dao.getByCriteria(critMap);
    }

}
