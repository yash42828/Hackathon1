package com.example.hp.hackathon;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

class MyListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> Product;
    private final ArrayList<String> Price;
    private final ArrayList<String> Weight;
    // private final Integer[] imgid;

    public MyListAdapter(Activity context, ArrayList<String> Product,ArrayList<String> Price,ArrayList<String> Weight) {
        super(context, R.layout.activity_mylist, Product);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.Product=Product;
        this.Price=Price;
        this.Weight=Weight;
        // this.imgid=imgid;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.activity_mylist, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.product);
        //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.price);
        TextView subtitleText1 = (TextView) rowView.findViewById(R.id.weight);


        titleText.setText(Product.get(position));
        // imageView.setImageResource(imgid[position]);
        subtitleText.setText(Price.get(position));
        subtitleText1.setText(Weight.get(position));

//        Toast.makeText(this, "1:"+Product.get(position)+" 2:"+subtitleText+" 3:"+subtitleText1, Toast.LENGTH_SHORT).show();

        return rowView;

    };
}
