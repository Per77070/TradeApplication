package com.example.tradeapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;


public class SellActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ArrayList<String> numberlist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        Spinner spinner = findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.companies,android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        get_json();


    }

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getInstance().getReference();


    public void get_json(){
        String json;
        try{

            InputStream is = getAssets().open("StockTrade.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json =new String (buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            for( int i=0; i<jsonArray.length();i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                if(obj.getString("Company").equals(numberlist.add(obj.getString("Value")))){

                    numberlist.add(obj.getString("Value"));

                }


            }
            //Toast.makeText(getApplicationContext(),numberlist.toString(), Toast.LENGTH_LONG).show();



        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }




    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String text = numberlist.toString();
        String[] separated = text.split(",");
        TextView textView = findViewById(R.id.textView_c);
        String stockValue = separated[position];
        //int result = Integer.parseInt(number);

        textView.setText(stockValue);

        HashMap<String,String> stockBuy = new HashMap<>();
        stockBuy.put("values",stockValue);

        myRef.child("values").setValue(stockBuy).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {

                    Toast.makeText(SellActivity.this, "Database updated", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    Toast.makeText(SellActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }


            }
        });

//Toast.makeText(getApplicationContext(),"Text"+separated[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void sellActivity(View view) {

        Intent buyStock = new Intent(getApplicationContext(), MainActivity.class);

startActivity(buyStock);
    }
}



