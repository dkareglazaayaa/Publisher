package com.example.gallery.Adapters;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gallery.Fragments.PostFragment;
import com.example.gallery.Fragments.PublishFragment;
import com.example.gallery.R;
import com.example.gallery.Utils.FileManager;
import com.example.gallery.Utils.JsonFileManager;
import com.example.gallery.Utils.FragmentManagerUtil;
import com.example.gallery.model.PostFolder;
import com.example.gallery.model.Publications;

import java.io.File;
import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {
    private ArrayList<PostFolder> posts;
    private Context postContex;
private   View postCell;
    public PostAdapter(ArrayList<PostFolder> posts, Context postContex) {
        this.posts = posts;
        this.postContex = postContex;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        postCell = inflater.inflate(R.layout.post, parent, false);

        return new PostHolder(postCell);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        final PostFolder post = posts.get(position);

        holder.postTitle.setText(post.getPostTitle());
        holder.postText.setText(post.getPostText());
        setPostImage(post.getImagePath(),holder);

        setFullViewFunction(postCell,post);
        setDeleteButton(postCell,post);
    }

    private void setFullViewFunction(View view, PostFolder post){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new PostFragment(post);
                FragmentTransaction ft = FragmentManagerUtil.fm.beginTransaction();
                ft.replace(R.id.fragment, fragment);
                ft.commit();
            }
        });
    }
    private void setDeleteButton(View view, PostFolder post){
        ImageButton button=view.findViewById(R.id.delete_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posts.remove(post);
                Publications.deletePost(post);
                JsonFileManager.WriteJsonFile();
                FileManager.WriteFile();

                Fragment fragment = new PublishFragment();
                FragmentTransaction ft = FragmentManagerUtil.fm.beginTransaction();
                ft.replace(R.id.fragment, fragment);
                ft.commit();
            }
        });
    }

    private void setPostImage(String imagePath, PostHolder holder) {
        File imageFile = new File(imagePath);
        Bitmap bm = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        holder.postImage.setImageBitmap(bm);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class PostHolder extends RecyclerView.ViewHolder {
        TextView postTitle;
        TextView postText;
        ImageView postImage;
      //  WebView webView;

        public PostHolder(@NonNull View itemView) {
            super(itemView);

            this.postTitle = itemView.findViewById(R.id.postTitle);
            this.postText = itemView.findViewById(R.id.postText);
            this.postImage = itemView.findViewById(R.id.postImage);
            //this.webView=itemView.findViewById(R.id.webView);
        }

    }
}
