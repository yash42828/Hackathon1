package com.example.hp.hackathon;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Customer extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    public static TextView cbarcode;
    private Button logout;
    private Button checkout;
    ListView list;
    ListView list1;
    ListView list2;
    Button fetch;
    ImageView scan;
    final ArrayList<String> item = new ArrayList<String>();
    final ArrayList<String> price = new ArrayList<String>();
    final ArrayList<String> weight = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter1;
    private ArrayAdapter<String> adapter2;

    private String selectedItem;
    int itempos;
    private final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        cbarcode = findViewById(R.id.Barcode);
        scan = findViewById(R.id.cscan);
        logout = findViewById(R.id.logout);
        checkout = findViewById(R.id.checkout);
        fetch = findViewById(R.id.Fetch);
//        final ListView listView = findViewById(R.id.list);
//        final ArrayList<String> item = new ArrayList<String>();
//        ArrayList<String> price = new ArrayList<String>();
//        ArrayList<String> weight = new ArrayList<String>();

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, item);
        list = (ListView) findViewById(R.id.list);
//        list.setAdapter(adapter);
        final ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, price);
        list1 = (ListView) findViewById(R.id.list1);
//        list1.setAdapter(adapter1);

        final ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, weight);
        list2 = (ListView) findViewById(R.id.list2);


        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Customer.this, ScanCodeActivity.class).putExtra("calling-activity", 2));
            }
        });

        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DocumentReference documentReference = firebaseFirestore.collection("users").document(cbarcode.getText().toString().trim());
                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        item.add(documentSnapshot.getString("name"));
                        price.add(documentSnapshot.getString("price"));
                        weight.add(documentSnapshot.getString("weight"));
                        list.setAdapter(adapter);
                        list1.setAdapter(adapter1);
                        list2.setAdapter(adapter2);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Customer.this, "Error:Please scan barcode", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DB(item,price,weight,firebaseAuth.getUid());
                int total_weight = 0, total_price = 0;
                for (int z = 0; z < item.size(); z++) {
                    total_price += Integer.parseInt(price.get(z));
                    total_weight += Integer.parseInt(weight.get(z));
                }
                final int tp = total_price;
                final int tw = total_weight;
//                Toast.makeText(Customer.this,"tp:"+tp+" tw:"+tw,Toast.LENGTH_SHORT).show();
                CollectionReference dbcustomer = firebaseFirestore.collection("customer_details");

                customer_details customer_details = new customer_details(item, price, weight);
                dbcustomer.document(firebaseAuth.getUid()).set(customer_details)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Customer.this, "Items Added", Toast.LENGTH_LONG).show();
                                finish();
                                Intent i = new Intent(Customer.this, Checkout.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("tp", Integer.toString(tp));
                                bundle.putString("tw", Integer.toString(tw));
                                bundle.putString("tc", Integer.toString(item.size()));
                                i.putExtras(bundle);
                                startActivity(i);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Customer.this, "", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(Customer.this, ALogin.class));
            }
        });



    AdapterView.OnItemLongClickListener itemLongListener = new AdapterView.OnItemLongClickListener() {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long rowid) {

            // Store selected item in global variable
            selectedItem = parent.getItemAtPosition(position).toString();
            itempos = item.indexOf(selectedItem);
            //selectedItem = parent.getItemAtPosition(position).toString();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Do you want to remove " + selectedItem + "?");
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    adapter.remove(selectedItem);
                    adapter1.remove(price.get(itempos));

                    adapter2.remove(weight.get(itempos));

                    adapter.notifyDataSetChanged();

                    Toast.makeText(
                            getApplicationContext(),
                            selectedItem + " has been removed.",
                            Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            // Create and show the dialog
            builder.show();

            return true;
        }
    };
                list.setOnItemLongClickListener(itemLongListener);

}

}



