package com.example.hp.hackathon;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private EditText email;
    private EditText password;
    private EditText reenter;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        reenter = (EditText) findViewById(R.id.reenter);

        findViewById(R.id.register).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        final String Email = email.getText().toString().trim();
        final String Password = password.getText().toString().trim();
        String Renter = reenter.getText().toString().trim();

        if (Validate(Email, Password, Renter)) {
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.createUserWithEmailAndPassword(Email,Password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                //Successflly registered
                                Toast.makeText(Register.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Register.this,Customer.class));
                            }
                            else{
                                Toast.makeText(Register.this, "Error:Not Registered", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private boolean Validate(String e, String p, String rp) {

        //Validating entries
        if (TextUtils.isEmpty(e) || TextUtils.isEmpty(p) || TextUtils.isEmpty(rp)) {
            Toast.makeText(Register.this, "Error:Please enter all details", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!p.equals(rp)) {
            Toast.makeText(Register.this, "Error:Password doesn't match", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}

