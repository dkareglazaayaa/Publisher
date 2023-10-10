package com.example.gallery.Fragments;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.gallery.R;
import com.example.gallery.Utils.FileManager;
import com.example.gallery.Utils.JsonFileManager;
import com.example.gallery.model.PostFolder;
import com.example.gallery.model.Publications;

import java.io.File;
import java.io.IOException;

public class AddPostFragment extends Fragment {

    private ImageView imageView;
    private EditText postTitle;
    private EditText postText;
    private EditText webUrl;
    private Button publishButton;
    private Button backButton;
    private ImageButton playSoundButton;
    private Button selectSoundButton;
    private String imagePath="/sdcard/Pictures/no_photo.jpg";
    private String soundPath;

    private PostFolder post=new PostFolder();
    private MediaPlayer player =new MediaPlayer();;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_post_fragment, container, false);


        setFields(view);
        setButtons();

        return view;

    }
    private void setFields(View view){
        imageView=view.findViewById(R.id.postImage);
        postTitle=view.findViewById(R.id.postTitle);
        postText=view.findViewById(R.id.postText);
        webUrl=view.findViewById(R.id.webUrl);

        selectSoundButton=view.findViewById(R.id.selectSoundButton);
        publishButton=view.findViewById(R.id.publishButton);
        backButton=view.findViewById(R.id.back_button);
        playSoundButton=view.findViewById(R.id.playSoundButton);
    }
    private void setButtons(){
        setPublishButton();
        setImageButton();
        setBackButton();
        setSelectSoundButton();
        setPlaySoundButton();
    }
    private void setPublishButton(){
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputPostTitle = postTitle.getText().toString();
                String inputPostText = postText.getText().toString();
                String inputWebUrl=webUrl.getText().toString();
                if (checkInputData(inputPostTitle, inputPostText)) {
                    PostFolder post = new PostFolder(imagePath, soundPath, inputPostTitle, inputPostText,inputWebUrl);
                    Publications.addPost(post);
                    JsonFileManager.WriteJsonFile();
                    FileManager.WriteFile();
                    loadPublishFragment();
                }
            }
        });
    }
    private void loadPublishFragment(){
        Fragment publishFragment = new PublishFragment();
        FragmentManager fm = getParentFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment, publishFragment);
        ft.commit();
    }
    private void setImageButton(){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                imageActivityResultLauncher.launch(intent);

            }
        });
    }
    private void setSelectSoundButton(){
        selectSoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                soundActivityResultLauncher.launch(intent);

            }
        });
    }
    ActivityResultLauncher<Intent> imageActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        if (result.getResultCode() == RESULT_OK) {
                            Uri selectedImage = result.getData().getData();
                            String[] filePathColumn = {MediaStore.Images.Media.DATA};

                            Cursor cursor = getActivity().getContentResolver()
                                    .query(selectedImage, filePathColumn, null, null, null);
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imagePath = cursor.getString(columnIndex);
                            cursor.close();


                            setImageView();
                        }
                    }
                }
            });

    ActivityResultLauncher<Intent> soundActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        if (result.getResultCode() == RESULT_OK) {
                            Uri selectedSound = result.getData().getData();
                            String[] filePathColumn = {MediaStore.Audio.Media.DATA};

                            Cursor cursor = getActivity().getContentResolver()
                                    .query(selectedSound, filePathColumn, null, null, null);
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            soundPath = cursor.getString(columnIndex);
                            cursor.close();


                            playSoundButton.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
    private void setImageView() {
        File imageFile = new File(imagePath);
        Bitmap bm = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        imageView.setImageBitmap(bm);
    }
    private void setPlaySoundButton() {
        playSoundButton.setVisibility(View.INVISIBLE);
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
                    player.setDataSource(soundPath);
                    player.prepare();
                    player.start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    private boolean checkInputData(String inputPostTitle, String inputPostText) {

        boolean result=true;
        if(inputPostTitle==null || inputPostTitle.isEmpty()){
            postTitle.setError("Enter a post title");
            result=false;
        }
        if(inputPostText==null || inputPostText.isEmpty()){
            postText.setError("Enter a post text");
            result=false;
        }
        return result;
    }

    private void setBackButton(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               loadPublishFragment();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
        player.release();
    }
}