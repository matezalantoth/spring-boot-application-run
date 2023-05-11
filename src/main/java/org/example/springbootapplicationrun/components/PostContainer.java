package org.example.springbootapplicationrun.components;

import models.Post;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class PostContainer {


    private static PostContainer INSTANCE;
    private LinkedHashMap<Integer, Post> posts;

    public static PostContainer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PostContainer();

        }

        return INSTANCE;
    }

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

    public List<Post> getPosts(){
        List<Post> selectedPosts = new ArrayList<>();
        posts.forEach((postId, post) ->{
            selectedPosts.add(post);

        });

        return selectedPosts;
    }



}
