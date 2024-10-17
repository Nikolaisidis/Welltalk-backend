package com.communicators.welltalk.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import com.communicators.welltalk.Repository.PostRepository;
import com.communicators.welltalk.Entity.PostEntity;

import java.util.List;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CounselorService counselorService;

    public List<PostEntity> getAllPosts() {
        Sort sort = Sort.by(Sort.Order.desc("postDate"), Sort.Order.desc("postTime"));
        return postRepository.findByIsDeletedFalse(sort);
    }

    public List<PostEntity> getAllPinnedPosts() {
        Sort sort = Sort.by(Sort.Order.desc("postDate"), Sort.Order.desc("postTime"));
        return postRepository.findByIsPinnedTrue(sort);
    }

    public PostEntity getPostById(int id) {
        return postRepository.findByPostIdAndIsDeletedFalse(id).get();
    }

    public PostEntity savePost(int counselorId, PostEntity post) {
        post.setAuthor(counselorService.getCounselorById(counselorId));
        return postRepository.save(post);
    }

    @SuppressWarnings("finally")
    public PostEntity updatePost(int id, PostEntity post) {
        PostEntity postToUpdate = new PostEntity();
        try {
            postToUpdate = postRepository.findByPostIdAndIsDeletedFalse(id).get();

            postToUpdate.setPostContent(post.getPostContent());
            postToUpdate.setPostImage(post.getPostImage());
        } catch (Exception e) {
            throw new IllegalArgumentException("Post " + post.getPostId() + " does not exist.");
        } finally {
            return postRepository.save(postToUpdate);
        }
    }

    @SuppressWarnings("finally")
    public PostEntity pinPost(int id, PostEntity post) {
        PostEntity postToPin = new PostEntity();
        try {
            postToPin = postRepository.findByPostIdAndIsDeletedFalse(id).get();
            postToPin.setIsPinned(true);
        } catch (Exception e) {
            throw new IllegalArgumentException("Post " + post.getPostId() + " does not exist.");
        } finally {
            return postRepository.save(postToPin);
        }
    }

    @SuppressWarnings("finally")
    public PostEntity unpinPost(int id, PostEntity post) {
        PostEntity postToUnpin = new PostEntity();
        try {
            postToUnpin = postRepository.findByPostIdAndIsDeletedFalse(id).get();
            postToUnpin.setIsPinned(false);
        } catch (Exception e) {
            throw new IllegalArgumentException("Post " + post.getPostId() + " does not exist.");
        } finally {
            return postRepository.save(postToUnpin);
        }
    }

    public boolean deletePost(int id) {
        PostEntity post = postRepository.findByPostIdAndIsDeletedFalse(id).get();
        if (post != null) {
            post.setIsDeleted(true);
            postRepository.save(post);
            return true;
        } else {
            System.out.println("Post " + id + " does not exist.");
            return false;
        }
    }
}
