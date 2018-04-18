package com.example.awos.inclassassignment08_jingyueluo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText keyArea;
    EditText valueArea;
    FirebaseDatabase database;
    DatabaseReference myReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        keyArea = (EditText) findViewById(R.id.key_area);
        valueArea = (EditText) findViewById(R.id.value_area);
        database = FirebaseDatabase.getInstance();
    }

    public void writeToCloud(View view) {
        myReference=database.getReference(keyArea.getText().toString());
        myReference.setValue(valueArea.getText().toString());
    }

    public void readFromCloud(View view) {
        myReference=database.getReference(keyArea.getText().toString());
        myReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String loadedData = dataSnapshot.getValue(String.class);
                    valueArea.setText(loadedData);
                }else{
                    valueArea.setText(null);
                    Toast.makeText(MainActivity.this,"Cannot find the key",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,"Error loading",Toast.LENGTH_SHORT).show();

            }
        });
    }
}
