package com.unipi.tantoniou.ergasia3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

public class MainActivityFind extends AppCompatActivity {

    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_find);

        sqLiteDatabase = openOrCreateDatabase("CountryDB.db",MODE_PRIVATE,null);
    }

    public void navigateFromFindToHome(View view){
        Intent intent = new Intent(this, MainActivityHome.class);

        startActivity(intent);
    }

    public void navigateFromFindToInsert(View view){
        Intent intent = new Intent(this, MainActivityInsert.class);

        startActivity(intent);
    }

    public void search(View view){
        try {

            String selectSQL = "SELECT * FROM Country";
            Cursor cursor = sqLiteDatabase.rawQuery(selectSQL,null);
            StringBuilder builder = new StringBuilder();
            while (cursor.moveToNext()){
                builder.append("Name: ").append(cursor.getString(cursor.getColumnIndex("Name"))).append("\n");
                builder.append("Capital: ").append(cursor.getString(cursor.getColumnIndex("Capital"))).append("\n");
                builder.append("Population: ").append(cursor.getInt(cursor.getColumnIndex("Population"))).append("\n");
                builder.append("-------------------------------------\n");
            }
                showMessage("Countries in DB",builder.toString());
        }catch (Exception e){
            showMessage("Error", e.getMessage());
        }
    }
    private void showMessage(String title, String message){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .show();
    }
}