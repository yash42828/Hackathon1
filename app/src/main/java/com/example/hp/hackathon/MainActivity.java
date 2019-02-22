package com.example.hp.hackathon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.craftman.cardform.Card;
import com.craftman.cardform.CardForm;
import com.craftman.cardform.OnPayBtnClickListner;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardForm cardForm = (CardForm)findViewById(R.id.cardform);
        TextView textView = (TextView)findViewById(R.id.payment_amount);
        Button btn = (Button)findViewById(R.id.btn_pay);
        firebaseAuth = FirebaseAuth.getInstance();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            textView.setText("Rs "+bundle.getString("tp"));
        }


        btn.setText(String.format("Payer is ", textView.getText()));

        cardForm.setPayBtnClickListner(new OnPayBtnClickListner() {
            @Override
            public void onClick(Card card) {
                Toast.makeText(MainActivity.this,"Name: "+ card.getName() + "Last 4 digits " + card.getLast4(), Toast.LENGTH_SHORT).show();
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(MainActivity.this,ALogin.class));
            }
        });
    }
}
