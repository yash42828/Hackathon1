package com.example.hp.hackathon;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static com.example.hp.hackathon.Add.barcode;
import static com.example.hp.hackathon.Customer.cbarcode;
import static com.example.hp.hackathon.Add.barcode;
import static com.example.hp.hackathon.Customer.cbarcode;

public class ScanCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView ScannerView;
    private String [] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.ACCESS_FINE_LOCATION", "android.permission.READ_PHONE_STATE", "android.permission.SYSTEM_ALERT_WINDOW","android.permission.CAMERA"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int requestCode = 200;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
        ScannerView = new ZXingScannerView(this);
        setContentView(ScannerView);

        //   startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
        //startActivityForResult(intent,);
    }

    @Override
    public void handleResult(Result result) {
        int callingActivity = getIntent().getIntExtra("calling-activity",0);

        switch (callingActivity){
            case 1:
                barcode.setText(result.getText());
                break;

            case 2:
                cbarcode.setText(result.getText());
                break;
        }
//        Toast.makeText(this,"Result:"+barcode,Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();

        ScannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();

        ScannerView.setResultHandler(this);
        ScannerView.startCamera();
    }
}
