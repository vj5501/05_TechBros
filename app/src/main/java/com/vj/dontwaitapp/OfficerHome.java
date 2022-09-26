package com.vj.dontwaitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class OfficerHome extends AppCompatActivity {

    EditText etSource,etDesti, etBusno;
    EditText etTime;
    FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_officer_home);
        etSource=findViewById(R.id.etSource);
        etDesti=findViewById(R.id.etDest);
        etBusno=findViewById(R.id.etbusn);
        etTime=findViewById(R.id.etTime);
        etTime.setText(Timestamp.now().toDate().toString());

        firestore = FirebaseFirestore.getInstance ( ) ;

    }

    public void addEntry(View view) {

        Editable busno= etBusno.getText();
        String source= etSource.getText().toString();
        String destination= etDesti.getText().toString();
        String time= (Timestamp.now().toDate().toString());

        Map<String,Object > route = new HashMap<>();
        route.put("BusNo",busno);
        route.put("Source",source);
        route.put("Destination",destination);
        route.put("LeavingTime",time);
        firestore.collection("Routes").add(route).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(OfficerHome.this, "Successfully Added Route to Database", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(OfficerHome.this,OfficerHome.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(OfficerHome.this, "Error:Cannot Route to Database", Toast.LENGTH_SHORT).show();

            }
        });

    }
}