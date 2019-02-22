package com.example.hp.hackathon;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ALogin extends AppCompatActivity implements View.OnClickListener{
    private EditText email;
    private EditText password;
    private Button login;
    private TextView reg;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alogin);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            //start profile activity
            if(firebaseAuth.getUid().equals("Gu4a36PaXib8F9KuOF6TNQbOjlh2")){
                finish();
                Intent i = new Intent(ALogin.this,Add.class);
                startActivity(i);
            }
            else {
                finish();
                Intent i = new Intent(ALogin.this, Customer.class);
                startActivity(i);
            }
        }
        email = (EditText) findViewById(R.id.email_login);
        password = (EditText) findViewById(R.id.password_login);
        login = (Button) findViewById(R.id.login);
        reg = (TextView) findViewById(R.id.register_login);
        reg.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == login){
            userLogin();
        }
        if (v == reg){
            finish();
            Intent i = new Intent(ALogin.this,Register.class);
            startActivity(i);
        }
    }

    private void userLogin() {
        final String Email = email.getText().toString().trim();
        final String Password = password.getText().toString().trim();

        if(Email.isEmpty() || Password.isEmpty()){
            Toast.makeText(ALogin.this,"Error:Enter all Details",Toast.LENGTH_LONG).show();
        }
        else{
            firebaseAuth.signInWithEmailAndPassword(Email,Password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                if(firebaseAuth.getUid().equals("Gu4a36PaXib8F9KuOF6TNQbOjlh2")){
                                    Toast.makeText(ALogin.this, "Logged in", Toast.LENGTH_SHORT).show();
                                    finish();
                                    startActivity(new Intent(ALogin.this,Add.class));
                                }
                                else{
                                    Toast.makeText(ALogin.this,"Logged in",Toast.LENGTH_SHORT).show();
                                    finish();
                                    Intent i = new Intent(ALogin.this,Customer.class);
                                    startActivity(i);
                                }
                            }
                            else{
                                Toast.makeText(ALogin.this,"Error:Unable to log in",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }
}

