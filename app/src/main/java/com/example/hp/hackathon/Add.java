package com.example.hp.hackathon;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Add extends AppCompatActivity implements View.OnClickListener{

    public static TextView barcode;
    private FirebaseAuth firebaseAuth;
    private EditText pro_name;
    private EditText price;
    private EditText weight;
    private Button Alogout;

    ImageView scan;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        db = FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        barcode = (TextView) findViewById(R.id.Barcode);
        pro_name = (EditText) findViewById(R.id.Product_name);
        price = (EditText) findViewById(R.id.price);
        weight = (EditText) findViewById(R.id.weight);

        scan = findViewById(R.id.scan);
        Alogout = findViewById(R.id.Adminlogout);

        scan.setOnClickListener(this);
        Alogout.setOnClickListener(this);

        findViewById(R.id.add).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.scan){
//            Toast.makeText(this,"Scan",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Add.this,ScanCodeActivity.class).putExtra("calling-activity",1));
        }
        if(v.getId()==R.id.Adminlogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(Add.this,ALogin.class));
        }
        else {
            final String Barcode = barcode.getText().toString().trim();
            final String Name = pro_name.getText().toString().trim();
            final String Price = price.getText().toString().trim();
            final String Weight = weight.getText().toString().trim();

            if (TextUtils.isEmpty(Barcode) || TextUtils.isEmpty(Name) || TextUtils.isEmpty(Price) || TextUtils.isEmpty(Weight)) {
                Toast.makeText(Add.this, "Error:Please enter all details", Toast.LENGTH_SHORT).show();
            } else {
                CollectionReference dbUsers = db.collection("users");

                Users user = new Users(Barcode, Name, Price, Weight);
                dbUsers.document(Barcode).set(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                finish();
                                Toast.makeText(Add.this,"Product Added",Toast.LENGTH_SHORT);
                                Intent i = new Intent(Add.this, Add.class);
                                startActivity(i);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Add.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });

            }

        }
    }
}
