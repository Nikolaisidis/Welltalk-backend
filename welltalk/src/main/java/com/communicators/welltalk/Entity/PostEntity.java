package com.communicators.welltalk.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

import javax.persistence.Lob;

@Entity
@Table(name = "tblpost")
public class PostEntity {
    // user, postContent, postDate, postTime, isDeleted, postModified, postImage
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;

    @ManyToOne
    @JoinColumn(name = "authorId", referencedColumnName = "id")
    private CounselorEntity author;

    @Lob
    private String postContent;

    private LocalDate postDate;

    private LocalTime postTime;

    private boolean isDeleted;

    private LocalDate postModifiedDate;

    private LocalTime postModifiedTime;

    private String postImage;

    private boolean isPinned;

    public PostEntity() {
    }

    public PostEntity(CounselorEntity author, String postContent, String postImage) {
        this.author = author;
        this.postContent = postContent;
        isDeleted = false;
        this.postImage = postImage;
        isPinned = false;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public CounselorEntity getAuthor() {
        return author;
    }

    public void setAuthor(CounselorEntity author) {
        this.author = author;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public LocalDate getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDate postDate) {
        this.postDate = postDate;
    }

    public LocalTime getPostTime() {
        return postTime;
    }

    public void setPostTime(LocalTime postTime) {
        this.postTime = postTime;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public LocalDate getPostModifiedDate() {
        return postModifiedDate;
    }

    public void setPostModifiedDate(LocalDate postModifiedDate) {
        this.postModifiedDate = postModifiedDate;
    }

    public LocalTime getPostModifiedTime() {
        return postModifiedTime;
    }

    public void setPostModifiedTime(LocalTime postModifiedTime) {
        this.postModifiedTime = postModifiedTime;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    @PrePersist
    public void prePersist() {
        postDate = LocalDate.now(ZoneId.of("Asia/Manila"));
        postTime = LocalTime.now(ZoneId.of("Asia/Manila"));
    }

    @PreUpdate
    public void preUpdate() {
        postModifiedDate = LocalDate.now(ZoneId.of("Asia/Manila"));
        postModifiedTime = LocalTime.now(ZoneId.of("Asia/Manila"));
    }

    public void setIsPinned(boolean isPinned) {
        this.isPinned = isPinned;
    }

    public boolean getIsPinned() {
        return isPinned;
    }
}
