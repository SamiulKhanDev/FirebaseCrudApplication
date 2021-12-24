package com.example.firebasecrudapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddItemActivity extends AppCompatActivity {
    private TextInputEditText itemName,itemPrice,suitable,url,descItem,itemLink;
    private Button addItem;
    private FirebaseDatabase fd;
    private FirebaseAuth fu;
    private DatabaseReference dr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        itemName = findViewById(R.id.ItemName);
        itemPrice = findViewById(R.id.ItemPrice);
        suitable = findViewById(R.id.suitable);
        url = findViewById(R.id.url);
        descItem = findViewById(R.id.descItem);
        addItem = findViewById(R.id.addItem);
        itemLink = findViewById(R.id.itemLink);
        fd = FirebaseDatabase.getInstance();
        fu = FirebaseAuth.getInstance();

        dr = fd.getReference("Items");

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemNameText = itemName.getText().toString();
                String itemPriceText = itemPrice.getText().toString();
                String suitableText = suitable.getText().toString();
                String urlText = url.getText().toString();
                String descText = descItem.getText().toString();
                String itemLinkText = itemLink.getText().toString();
                String courseId = itemName.getText().toString();

                if(TextUtils.isEmpty(itemNameText) || TextUtils.isEmpty(itemPriceText)||TextUtils.isEmpty(suitableText)||TextUtils.isEmpty(descText)){
                    Toast.makeText(AddItemActivity.this, "Please Fill The Name,Price,Suitable For,Description", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(urlText)){
                    urlText = null;
                }
                if(TextUtils.isEmpty(itemLinkText)){
                    itemLinkText = null;
                }


                Model m = new Model(itemNameText,itemPriceText,suitableText,urlText,itemLinkText,descText,courseId,fu.getUid());
                dr.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        dr.child(courseId).setValue(m);
                        Toast.makeText(AddItemActivity.this, "ItemAdded", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddItemActivity.this,MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddItemActivity.this, "Network Error,Item not added "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });




    }
}