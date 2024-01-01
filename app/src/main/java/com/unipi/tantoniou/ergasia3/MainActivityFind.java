package com.unipi.tantoniou.ergasia3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.unipi.tantoniou.ergasia3.models.Country;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class MainActivityFind extends AppCompatActivity {

    SQLiteDatabase sqLiteDatabase;
    ArrayList<EditText> editableFields;
    EditText countryName;

    Country country;

    HashMap<Integer,Boolean> hashMap;
    String[] countriesWithSameNameFoundString;
    boolean[] itemsChecked;
    ArrayList<Country> countriesWithSameNameFound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_find);
        initEditableFields();
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
            if(!countryName.getText().toString().equals("")){
                if(!validateNullEditableFields()){
                    showMessage("Error","You must have not text in other fields except the country name");
                    return;
                }
                searchByCountryName();

            }else {
                searchAll();
            }


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

    private void initEditableFields(){
        countryName = findViewById(R.id.editTextFindCountryName);
        editableFields = new ArrayList<>();
        editableFields.add(findViewById(R.id.editTextFindCountryCapital));
        editableFields.add(findViewById(R.id.editTextFindCountryPopulation));
    }

    private void hideEditableFields(){
        for(EditText view:editableFields){
            view.setVisibility(View.INVISIBLE);
        }
    }

    private void showEditableFields(){
        for(EditText view:editableFields){
            view.setVisibility(View.VISIBLE);
        }
    }



    private boolean validateNullEditableFields(){
        boolean isNull = true;
        for(EditText view:editableFields){
            if(!view.getText().toString().equals("")){
                return false;
            }
        }
        return isNull;
    }

    private void searchAll(){
        String selectSQL = "SELECT * FROM Country";
        Cursor cursor = sqLiteDatabase.rawQuery(selectSQL,null);
        StringBuilder builder = new StringBuilder();
        while (cursor.moveToNext()){
            builder.append("Name: ").append(cursor.getString(cursor.getColumnIndex("Name"))).append("\n");
            builder.append("Capital: ").append(cursor.getString(cursor.getColumnIndex("Capital"))).append("\n");
            builder.append("Population: ").append(cursor.getInt(cursor.getColumnIndex("Population"))).append("\n");
            builder.append("-------------------------------------\n");
        }
        cursor.close();
        showMessage("Countries in DB",builder.toString());
    }

    private void searchByCountryName(){
        StringBuilder selectSQL = new StringBuilder("SELECT * FROM Country WHERE Name LIKE ?;");
        String[] selectionArgs = {countryName.getText().toString()};
        try {

            Cursor cursor = sqLiteDatabase.rawQuery(selectSQL.toString(),selectionArgs);
        if(cursor.getCount()>1){
            handleMultipleChoiceCountries(cursor);
        }else {
            if(cursor.getCount()==0){
                showMessage("INFO","This country does not exist!");
            }
            if(cursor.moveToFirst()) {
                country = new Country();
                country.setId(cursor.getInt(cursor.getColumnIndex("CountryId")));
                fillTheFields(country.getId());
            }
        }
        cursor.close();
        }catch (Exception e){
            Log.d("Custom exception:",e.getLocalizedMessage());

        }
        EditText population = findViewById(R.id.editTextFindCountryPopulation);
        EditText capital = findViewById(R.id.editTextFindCountryCapital);

    }

    private void handleMultipleChoiceCountries(Cursor cursor){
        if(countriesWithSameNameFound!=null){
            countriesWithSameNameFound.clear();
            hashMap.clear();
        }
        hashMap = new HashMap<>();
        countriesWithSameNameFound = new ArrayList<>();
        while (cursor.moveToNext()){
            Country countryTemp = new Country();
            countryTemp.setName(cursor.getString(cursor.getColumnIndex("Name")));
            countryTemp.setCapital(cursor.getString(cursor.getColumnIndex("Capital")));
            countryTemp.setPopulation(cursor.getString(cursor.getColumnIndex("Population")));
            countryTemp.setId(cursor.getInt(cursor.getColumnIndex("CountryId")));
            countriesWithSameNameFound.add(countryTemp);
        }
        cursor.close();
        countriesWithSameNameFoundString = new String[countriesWithSameNameFound.size()];
        itemsChecked = new boolean[countriesWithSameNameFound.size()];
        int index = 0;
        for(Country country1:countriesWithSameNameFound){
            StringBuilder stringBuilder = new StringBuilder("Name:").append(country1.getName()).append("\n");
            stringBuilder.append("Capital:").append(country1.getCapital()).append("\n");
            stringBuilder.append("Population:").append(country1.getPopulation()).append("\n");
            countriesWithSameNameFoundString[index] = stringBuilder.toString();
            index++;
        }

        showMultipleChoiceBoxes();
    }

    private void showMultipleChoiceBoxes(){
        for(Country country1:countriesWithSameNameFound){
            hashMap.put(country1.getId(),false);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("This is a dialog with some simple text...");
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //boolean[] values =

                        Set<Integer> ids = hashMap.keySet();

                        Log.d("which button:",String.valueOf(whichButton));

                        int counter = 0;
                        for(int id:ids){
                            boolean value = hashMap.get(id);
                            if(value==true){
                                counter++;
                            }
                            if(counter>1){
                                break;
                            }
                        }
                        if(counter>1 || counter==0) {
                            Toast.makeText(getBaseContext(),
                                    "You must select only one country", Toast.LENGTH_SHORT).show();
                        }else{
                            int selectedId = 0;
                            for(int id:ids){
                                if(hashMap.get(id)==true){
                                    selectedId = id;
                                }
                            }

                            fillTheFields(selectedId);
                        }

                    }
                }
        );

        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(getBaseContext(),
                                "Cancel clicked!", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        builder.setMultiChoiceItems(countriesWithSameNameFoundString, itemsChecked,
                new DialogInterface.OnMultiChoiceClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton, boolean isChecked) {
//                        Toast.makeText(getBaseContext(),
//                                countriesWithSameNameFoundString[whichButton] + (isChecked ? " checked!" : " unchecked!"),
//                                Toast.LENGTH_SHORT).show();
                        int id = countriesWithSameNameFound.get(whichButton).getId();
                        hashMap.replace(id,isChecked);
                    }
                }
        );
        builder.create().show();
    }

    private void fillTheFields(int id){

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Country WHERE CountryId="+id,null);
        if(cursor.moveToFirst()) {
            this.country.setName(cursor.getString(cursor.getColumnIndex("Name")));
            this.country.setCapital(cursor.getString(cursor.getColumnIndex("Capital")));
            this.country.setPopulation(cursor.getString(cursor.getColumnIndex("Population")));
            this.country.setId(cursor.getInt(cursor.getColumnIndex("CountryId")));
            cursor.close();
        }

        EditText name = findViewById(R.id.editTextFindCountryName);
        EditText capital = findViewById(R.id.editTextFindCountryCapital);
        EditText population = findViewById(R.id.editTextFindCountryPopulation);
        name.setText(this.country.getName());
        capital.setText(this.country.getCapital());
        population.setText(this.country.getPopulation());

    }

    public void updateCountry(View view){
        prepareAndExecuteSqlUpdate();
        this.country = null;
    }

    private void prepareAndExecuteSqlUpdate(){
        try {


            int id = this.country.getId();
            EditText name = findViewById(R.id.editTextFindCountryName);
            EditText capital = findViewById(R.id.editTextFindCountryCapital);
            EditText population = findViewById(R.id.editTextFindCountryPopulation);
            if(
                    name.getText().toString().equals("") ||
                    capital.getText().toString().equals("") ||
                    population.getText().toString().equals("")

            ){
                showMessage("Error","Fields must not be empty!!");
                return;
            }
            String updateQuery = "UPDATE Country SET Name=?, Capital=?, Population=? WHERE CountryId=?";
            String[] updateArgs = {
                    name.getText().toString(),
                    capital.getText().toString(),
                    population.getText().toString(),
                    String.valueOf(id)
            };
            sqLiteDatabase.execSQL(updateQuery, updateArgs);

            showMessage("Info","Country updated successfully");
            name.setText("");
            capital.setText("");
            population.setText("");
        }catch (Exception e){
            Log.d("Error",e.getLocalizedMessage());
        }
    }

    public void deleteCountry(View view){
        editableFields.clear();
        initEditableFields();
        if(countryName.equals("") || validateNullEditableFields()){
            showMessage("Error","You must select a country");
            return;
        }
        if(country!=null){
            String deleteSQL = "DELETE FROM Country WHERE CountryId = ?";
            String[] selectedArgs ={String.valueOf(country.getId())};
            sqLiteDatabase.execSQL(deleteSQL,selectedArgs);
            showMessage("INFO","Country deleted succesfully!");
        }

    }



}