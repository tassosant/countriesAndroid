package com.unipi.tantoniou.ergasia3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;


public class MainActivityHome extends AppCompatActivity {

    SQLiteDatabase sqLiteDatabase;

//    Database database;

    String createTableSQLString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        initDB();
    }

    private void initDB(){
//        if(database==null) {
//            database = new Database();
//        }else{
//            database = (Database) getIntent().getSerializableExtra("SQLite");
//        }
        sqLiteDatabase = openOrCreateDatabase("CountryDB.db",MODE_PRIVATE,null);
//        database.setSqLiteDatabase(sqLiteDatabase);
        createCountriesTable();
    }

    private void createCountriesTable(){
        createCountriesTableSQL();
        sqLiteDatabase.execSQL(createTableSQLString);
    }

    private void createCountriesTableSQL(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE if not exists Country (");
        stringBuilder.append("Name TEXT NOT NULL,");
        stringBuilder.append("Capital TEXT NOT NULL,");
        stringBuilder.append("Population INTEGER NOT NULL,");
        stringBuilder.append("CountryId INTEGER PRIMARY KEY AUTOINCREMENT");
        stringBuilder.append(")");
        createTableSQLString = stringBuilder.toString();
    }

    public void navigateFromHomeToFind(View view){
        Intent intent = new Intent(this, MainActivityFind.class);
        //intent.putExtra("SQLite", database);
        startActivity(intent);
    }

    public void navigateFromHomeToInsert(View view){
//        try {

            Intent intent = new Intent(this, MainActivityInsert.class);
            //intent.putExtra("SQLite", database);
            startActivity(intent);
//        }catch (Exception e){
//            e.printStackTrace();
//            new AlertDialog.Builder(this)
//                    .setTitle("Error")
//                    .setMessage(e.getMessage())
//                    .setCancelable(true)
//                    .show();
//        }
    }
    //find by name
    //findAll
    //update by id
    //


}