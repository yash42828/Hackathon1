package com.example.hp.hackathon;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.firebase.auth.FirebaseAuth;

public class Checkout extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    private TextView total_price;
   private String total_weight;
    private TextView total_count;
    private Button cash;
    private String p;
    private static final int CAMERA_REQUEST = 1888;
    private String[] per = {"android.permission.CAMERA"};
   // private Button card;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cash = findViewById(R.id.by_cash);
       // card = findViewById(R.id.card);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        int requestCode = 200;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            requestPermissions(per,requestCode);
        }
        firebaseAuth = FirebaseAuth.getInstance();
        total_price = findViewById(R.id.price);
        p=new String();
       // total_weight = findViewById(R.id.weight);
        total_count = findViewById(R.id.count);
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            total_price.setText(bundle.getString("tp"));
            total_weight=bundle.getString("tw");
            total_count.setText(bundle.getString("tc"));
            p=bundle.getString("tp");
        }

//        cash.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(Checkout.this, "Pay At Cash Counter", Toast.LENGTH_SHORT).show();
////                firebaseAuth.signOut();
////                finish();
////                startActivity(new Intent(Checkout.this,ALogin.class));
//            }
//        });

    }
    public void onclick(View v){
        if(v.getId() == R.id.by_cash){
            Toast.makeText(Checkout.this, "Pay At Cash Counter", Toast.LENGTH_SHORT).show();
               firebaseAuth.signOut();
               finish();
               startActivity(new Intent(Checkout.this,ALogin.class));
        }

        if(v.getId() == R.id.card){
            Toast.makeText(Checkout.this, "Pay Via Card", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,CAMERA_REQUEST);

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == CAMERA_REQUEST){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
            Frame imageFrame = new Frame.Builder().setBitmap(photo).build();
            String imageText = "";


            SparseArray<TextBlock> textBlocks = textRecognizer.detect(imageFrame);

            for (int i = 0; i < textBlocks.size(); i++) {
                TextBlock textBlock = textBlocks.get(textBlocks.keyAt(i));
                imageText = textBlock.getValue();                   // return string
            }

            if(imageText.equals(total_weight)){
                Intent i = new Intent(Checkout.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("tp", p);
                i.putExtras(bundle);
                startActivity(i);
            }
            else{
                Toast.makeText(getApplicationContext(),"Sorry you cant proceed..",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
