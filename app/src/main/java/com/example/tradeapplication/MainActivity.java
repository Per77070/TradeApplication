package com.example.tradeapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
ArrayList<String> numberlist = new ArrayList<>();
    ArrayList<String> companylist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner spinner = findViewById(R.id.spinner);
EditText volume = findViewById(R.id.volume);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.companies,android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
spinner.setAdapter(adapter);
         spinner.setOnItemSelectedListener(this);
get_json();
onStart();

        }
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    //FirebaseDatabase database = FirebaseDatabase.getInstance();
     CollectionReference noteBookRef = db.collection("Stock");
    //DatabaseReference myRef = database.getReference();


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
       if(obj.getString("Company").equals(numberlist.add(obj.getString("Value"))))


            {

           numberlist.add(obj.getString("Value"));

       }
           else if(obj.getString("Company").equals(companylist.add(obj.getString("Company"))))


            {

                companylist.add(obj.getString("Company"));

            }

            }



        //Toast.makeText(getApplicationContext(),numberlist.toString(), Toast.LENGTH_LONG).show();



    } catch (IOException | JSONException e) {
            e.printStackTrace();
        }




    }
/*
    protected void onStart() {


        super.onStart();
    noteBookRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
        @Override
        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

            if(e!=null){

                Toast.makeText(MainActivity.this, "Error :"+e, Toast.LENGTH_SHORT).show();

            }

            String data ="";
            for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){


                Note note = documentSnapshot.toObject(Note.class);

                String value = note.getStockValue();
                String company = note.getStockCompany();
                data += "   \n " + (value + " " + company);



            }
        }
    });


    }
    */

    @Override

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Button export = findViewById(R.id.exportButton);
        String text_v = numberlist.toString();
        String text_c = companylist.toString();
        String[] separated_v = text_v.split(",");
        String[] separated_c = text_c.split(",");
        TextView textView_v = findViewById(R.id.textView_v);
        TextView textView_c = findViewById(R.id.textView_c);
EditText editText_volume = findViewById(R.id.volume);
        final String volume = editText_volume.getText().toString();
        final String stockCompany = separated_c[position];
        final String stockValue = separated_v[position];

editText_volume.setText(volume);
        textView_v.setText(stockValue);
        textView_c.setText(stockCompany);
        final HashMap<String,String> stockBuy = new HashMap<>();
stockBuy.put("volume",volume);
        stockBuy.put("values",stockValue);
stockBuy.put("companies",stockCompany);

export.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ///db.collection("users").document("stockBuy").set(stockBuy);


    Note note = new Note();

        noteBookRef.add(stockBuy);
    }
});







    };

//Toast.makeText(getApplicationContext(),"Text"+separated[position], Toast.LENGTH_LONG).show();


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }




    public void sellActivity(View view) {

        Intent sellStock = new Intent(MainActivity.this, SellActivity.class);

startActivity(sellStock);
    }

    public void viewNotes(View view) {
        final TextView textView = findViewById(R.id.textView);

noteBookRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
    @Override
    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

        String data ="";
        for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){


            Note note = documentSnapshot.toObject(Note.class);
String volume = documentSnapshot.getString("volume");
            String value = documentSnapshot.getString("values");
            String company = documentSnapshot.getString("companies");

            data += "   \n " +"Volume: "+volume+" " + ("Company: "+company + "   Value: " + value );

            textView.setText("Data: "+data);

        }
    }
});

    }

    public void delete(View view) {

        db.collection("Stock").document("values")
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();

                        final HashMap<String, FieldValue> stockBuy = new HashMap<>();

                        stockBuy.put("values", FieldValue.delete());
                        stockBuy.put("companies",FieldValue.delete());


                    }
                });

                    }
                }






