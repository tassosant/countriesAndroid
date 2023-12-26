package com.unipi.tantoniou.ergasia3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.unipi.tantoniou.ergasia3.models.Country;

public class MainActivityInsert extends AppCompatActivity {

    Country country;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_insert);
    }

    private void insertIntoCountry(){
        country = new Country();

    }

    private void constructInsertQuery(){
        String insertSQL = "Insert into Country(Name, Capital, Population) values(?,?,?)";
        String[] parameters = new String[5];
        parameters[0] = country.getName();
        parameters[1] = country.getCapital();
        parameters[2] = country.getPopulation();
    }

    public void navigateFromInsertToHome(View view){
        Intent intent = new Intent(this, MainActivityHome.class);

        startActivity(intent);
    }
    public void navigateFromInsertToFind(View view){
        Intent intent = new Intent(this, MainActivityFind.class);

        startActivity(intent);
    }

    public void addCountry(View view){

    }
}