package com.example.gallery.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.gallery.Adapters.PostAdapter;
import com.example.gallery.R;
import com.example.gallery.Utils.FragmentManagerUtil;
import com.example.gallery.model.PostFolder;

import java.io.File;
import java.io.IOException;

public class PostFragment extends Fragment {
    private ImageView postImage;
    private TextView postTitle;
    private WebView webView;
    private TextView postText;
    private Button backButton;
    private Button websiteButton;
    private ImageButton playSoundButton;

    private PostFolder post;
    private MediaPlayer player=new MediaPlayer();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.post_fragment, container, false);

        setFields(view);
        setButtons();

        setPost();

        return view;
    }
    public PostFragment(PostFolder post){
        this.post=post;
    }

    private void setBackButton(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.getVisibility() == View.VISIBLE) {
                    setVisibility();
                } else {
                    Fragment fragment = new PublishFragment();
                    FragmentManager fm = getParentFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.fragment, fragment);
                    ft.commit();
                }
            }
        });
    }
    private void setPlaySoundButton(){
        if(post.getSoundPath()==null || post.getSoundPath().isEmpty()){
            playSoundButton.setVisibility(View.INVISIBLE);
            return;
        }
        playSoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player.isPlaying()) {
                    player.stop();
                    player.reset();
                    return;
                }
                player.stop();
                player.reset();

                try {
                    player.setDataSource(post.getSoundPath());
                    player.prepare();
                    player.start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    private void setWebsiteButton(){
        websiteButton.setVisibility(View.INVISIBLE);
        websiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibility();
            }
        });
    }
    public void setButtons() {
        setBackButton();
        setWebsiteButton();
        setPlaySoundButton();
    }

    private void setFields(View view){
        postTitle=view.findViewById(R.id.postTitle);
        postText=view.findViewById(R.id.postText);
        postImage=view.findViewById(R.id.postImage);
        backButton=view.findViewById(R.id.back_button);
        webView=view.findViewById(R.id.webView);
        websiteButton=view.findViewById(R.id.websiteButton);
        playSoundButton=view.findViewById(R.id.playSoundButton);
    }

   private void setPost(){
     postTitle.setText(post.getPostTitle());
     postText.setText(post.getPostText());
     postText.setMovementMethod(new ScrollingMovementMethod());

     setImageView();
     setWebUrl();
   }
    private void setImageView() {
        File imageFile = new File(post.getImagePath());
        Bitmap bm = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        postImage.setImageBitmap(bm);
    }

    private void setWebUrl(){
        String webUrl=post.getWebUrl();
        if(webUrl!=null && !webUrl.isEmpty()) {
            webView.loadUrl(webUrl);
            websiteButton.setVisibility(View.VISIBLE);
        }
    }
    private void setVisibility(){

        int vis_web=webView.getVisibility();
        int vis_el=postImage.getVisibility();

        postImage.setVisibility(vis_web);
        postTitle.setVisibility(vis_web);
        postText.setVisibility(vis_web);
        websiteButton.setVisibility(vis_web);
        playSoundButton.setVisibility(vis_web);

        webView.setVisibility(vis_el);
    }
    @Override
    public void onStop() {
        super.onStop();
        player.stop();
        player.release();
    }
}