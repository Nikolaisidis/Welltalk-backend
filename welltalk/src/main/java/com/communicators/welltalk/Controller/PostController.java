package com.communicators.welltalk.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.communicators.welltalk.Entity.PostEntity;
import com.communicators.welltalk.Service.NotificationsService;
import com.communicators.welltalk.Service.PostService;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/counselor/post")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    private NotificationsService notificationsService;

    @GetMapping("/getAllPosts")
    public ResponseEntity<List<PostEntity>> getAllPosts() {
        List<PostEntity> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/getAllPinnedPosts")
    public ResponseEntity<List<PostEntity>> getAllPinnedPosts() {
        List<PostEntity> pinnedPosts = postService.getAllPinnedPosts();
        return new ResponseEntity<>(pinnedPosts, HttpStatus.OK);
    }

    @GetMapping("/getPostById/{id}")
    public ResponseEntity<PostEntity> getPostById(@PathVariable int id) {
        PostEntity post = postService.getPostById(id);
        if (post != null) {
            return new ResponseEntity<>(post, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/createPost")
    public ResponseEntity<PostEntity> insertPost(@RequestParam int counselorId, @RequestBody PostEntity post) {
        PostEntity newPost = postService.savePost(counselorId, post);

        notificationsService.createPostNotification(counselorId, newPost);

        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    @PutMapping("/updatePost/{id}")
    public ResponseEntity<PostEntity> updatePost(@PathVariable int id, @RequestBody PostEntity post) {
        PostEntity updatedPost = postService.updatePost(id, post);
        if (updatedPost == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @PutMapping("/pinPost")
    public ResponseEntity<PostEntity> pinPost(@RequestParam int id, @RequestBody PostEntity post) {
        PostEntity pinPost = postService.pinPost(id, post);
        if (pinPost == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pinPost, HttpStatus.OK);
    }

    @PutMapping("/unpinPost")
    public ResponseEntity<PostEntity> unpinPost(@RequestParam int id, @RequestBody PostEntity post) {
        PostEntity unpinPost = postService.unpinPost(id, post);
        if (unpinPost == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(unpinPost, HttpStatus.OK);
    }

    @DeleteMapping("/deletePost/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable int id) {
        boolean deleted = postService.deletePost(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
