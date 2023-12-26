package com.unipi.tantoniou.ergasia3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.unipi.tantoniou.ergasia3.models.Country;

public class MainActivityInsert extends AppCompatActivity {

    Country country;
    EditText countryName, population, capital;

    SQLiteDatabase sqLiteDatabase;

//    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_insert);
        sqLiteDatabase = openOrCreateDatabase("CountryDB.db",MODE_PRIVATE,null);
       // mapInputs();
//        Intent intent = getIntent();
//        try{
//            database = (Database) intent.getSerializableExtra("SQLite");
//        }catch (NullPointerException e){
//            e.printStackTrace();
//        }
    }



    private void constructInsertQuery(){
        String insertSQL = "Insert into Country(Name, Capital, Population) values(?,?,?)";
        String[] parameters = new String[5];
        parameters[0] = country.getName();
        parameters[1] = country.getCapital();
        parameters[2] = country.getPopulation();
        sqLiteDatabase.execSQL(insertSQL,parameters);
        showMessage("Info","Country inserted!");
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
        country = new Country();
        parseFromInputToCountry();
        constructInsertQuery();


    }
    private void parseFromInputToCountry(){
        country.setName(countryName.getText().toString());
        country.setCapital(capital.getText().toString());
        country.setPopulation(population.getText().toString());
    }

//    private void mapInputs(){
//        countryName = findViewById(R.id.editTextCountryName);
//        capital = findViewById(R.id.editTextCountryCapital);
//        population = findViewById(R.id.editTextCountryPopulation);
//    }

    private void showMessage(String title, String message){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .show();
    }
}
