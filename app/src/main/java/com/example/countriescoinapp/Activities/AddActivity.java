package com.example.countriescoinapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.countriescoinapp.R;
import com.google.android.material.textview.MaterialTextView;

public class AddActivity extends AppCompatActivity {

    private MaterialTextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
    }
}