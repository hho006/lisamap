package com.openshift.springmvc.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "messages")
public class BlogMessage {
    
    @Id
    @GeneratedValue
    @Column(name = "messageid", nullable = false)
    private int messageId;
    
    @Column(name = "userid_to", nullable = false)
    private int userIdTo;
    
    @Column(name = "userid_from", nullable = false)
    private int userIdFrom;
    
    @Column(name = "savedtime", nullable = false)
    private Date savedTime;
    
    @Column(name = "subject", nullable = false, length = 128)
    private String subject;
    
    @Column(name = "body", nullable = false, length = 2048)
    private String body;
    
    public final int getMessageId() {
        return messageId;
    }
    
    public void setMessageId(final int messageId) {
        this.messageId = messageId;
    }
    
    public final int getUserIdTo() {
        return userIdTo;
    }
    
    public void setUserIdTo(final int userIdTo) {
        this.userIdTo = userIdTo;
    }
    
    public final int getUserIdFrom() {
        return userIdFrom;
    }
    
    public void setUserIdFrom(final int userIdFrom) {
        this.userIdFrom = userIdFrom;
    }
    
    public final Date getSavedTime() {
        return savedTime;
    }
    
    public void setSavedTime(final Date savedTime) {
        this.savedTime = savedTime;
    }
    
    public final String getSubject() {
        return subject;
    }
    
    public void setSubject(final String subject) {
        this.subject = subject;
    }
    
    public final String getBody() {
        return body;
    }
    
    public void setBody(final String body) {
        this.body = body;
    }
}
