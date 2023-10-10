package com.example.gallery;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gallery.Fragments.LoginFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, LoginFragment.class, null)
                    .commit();

        }

    }
}