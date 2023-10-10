package com.example.gallery.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.example.gallery.Utils.FileManager;
import com.example.gallery.Utils.JsonFileManager;
import com.example.gallery.Utils.FragmentManagerUtil;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gallery.Adapters.PostAdapter;
import com.example.gallery.R;
import com.example.gallery.model.PostFolder;
import com.example.gallery.model.Publications;

import java.util.ArrayList;

public class PublishFragment extends Fragment {
    private ArrayList<PostFolder> posts;
    private Publications publications;
    private ImageButton addPostButton;
    private RecyclerView postView;

    public PublishFragment() {

    }

    public static PublishFragment getPublishFragment() {
        return getPublishFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.publish_fragment, container, false);

        FragmentManagerUtil.fm=getParentFragmentManager();
        JsonFileManager.ReadJsonFile();

        publications=Publications.getPublications();
        posts = publications.getPosts();

        setFields(view);
        setButtons();

        setPostView();

        FileManager.WriteFile();
       // //FileManager.WriteJsonFile();
        return view;
    }

    private void setButtons(){
        setAddPostButton();
    }
    private void setFields(View view){
        addPostButton=view.findViewById(R.id.addPostButton);
        postView=view.findViewById(R.id.posts);
    }
    private void setPostView(){

        postView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        postView.setLayoutManager(gridLayoutManager);

        if(!posts.isEmpty()){
            RecyclerView.Adapter postAdapter = new PostAdapter(posts,this.getContext());
            postView.setAdapter(postAdapter);
        }

    }
    public void loadAddPostFragment() {
        Fragment fragment = new AddPostFragment();
        FragmentManager fm = getParentFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment, fragment);
        ft.commit();
    }

    private void setAddPostButton() {
        addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              loadAddPostFragment();
            }
        });
    }
}