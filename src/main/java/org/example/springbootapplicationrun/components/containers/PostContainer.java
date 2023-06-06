package org.example.springbootapplicationrun.components.containers;

import org.example.springbootapplicationrun.enums.PostStatus;
import org.example.springbootapplicationrun.models.Post;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
@Component
public class PostContainer {

    private LinkedHashMap<Integer, Post> posts;

    public PostContainer() {
        posts = new LinkedHashMap<>();

    }
    public void addPost(Post post){
        Integer postId = post.getPostId();
        if(posts.containsKey(postId)){
            return;
        }
        posts.put(postId, post);
        System.out.println("added");


    }

    public boolean doesPostExist(Integer postId){
        return posts.containsKey(postId);
    }

    private void schedulePost(Post post){
        if(!post.isDownloaded()){
            return;
        }
        LocalDateTime schedule = post.getScheduledTo();

        if(schedule.isAfter(LocalDateTime.now())){
            return;
        }
        post.setStatus(PostStatus.SCHEDULED);

    }

    public List<Post> getPosts(){
        List<Post> selectedPosts = new ArrayList<>();
        posts.forEach((postId, post) ->{
            if (post.isScheduled()){

                selectedPosts.add(post);
            }
        });

        return selectedPosts;
    }

    public void schedulePosts(){
        posts.forEach((postId, post) ->{
            schedulePost(post);
        });

    }




}
