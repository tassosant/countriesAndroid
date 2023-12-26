package com.unipi.tantoniou.ergasia3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivityFind extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_find);
    }

    public void navigateFromFindToHome(View view){
        Intent intent = new Intent(this, MainActivityHome.class);

        startActivity(intent);
    }

    public void navigateFromFindToInsert(View view){
        Intent intent = new Intent(this, MainActivityInsert.class);

        startActivity(intent);
    }

    public void search(){

    }
}