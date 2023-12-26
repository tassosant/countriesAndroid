package com.unipi.tantoniou.ergasia3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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
}