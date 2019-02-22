package com.example.hp.hackathon;
public class Users{

    private String Barcode,Name,Price,Weight;


    public Users(String email, String username, String mobile, String password) {
        Barcode = email;
        Name = username;
        Price = mobile;
        Weight = password;
    }

    public String getBarcode() {
        return Barcode;
    }

    public String getName() {
        return Name;
    }

    public String getPrice() {
        return Price;
    }

    public String getWeight() {
        return Weight;
    }
}


