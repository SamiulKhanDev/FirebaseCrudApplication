package com.example.firebasecrudapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

public class EditItemActivity extends AppCompatActivity {
    private TextInputEditText ItemEditedName,ItemEditedPrice,suitableEdited,urlEdited,itemEditedLink,descItemEdited;
    private Button addEditedItem,deleteItem;
    private FirebaseDatabase fd;
    private FirebaseAuth fu;
    private DatabaseReference dr;

    private String itemId;
    private Model itemModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        ItemEditedName = findViewById(R.id.ItemEditedName);
        ItemEditedPrice = findViewById(R.id.ItemEditedPrice);
        suitableEdited = findViewById(R.id.suitableEdited);
        urlEdited = findViewById(R.id.urlEdited);
        itemEditedLink = findViewById(R.id.itemEditedLink);
        descItemEdited = findViewById(R.id.descItemEdited);
        addEditedItem = findViewById(R.id.addEditedItem);
        deleteItem = findViewById(R.id.deleteItem);
        fd = FirebaseDatabase.getInstance();
        fu = FirebaseAuth.getInstance();
        itemModel = getIntent().getParcelableExtra("item");
        if(itemModel!=null){
            ItemEditedName.setText(itemModel.getItemName());
            ItemEditedPrice.setText(itemModel.getItemPrice());
            suitableEdited.setText(itemModel.getSuitableFor());
            urlEdited.setText(itemModel.getImageLink());
            itemEditedLink.setText(itemModel.getItemLink());
            descItemEdited.setText(itemModel.getItemDesc());
            itemId = itemModel.getItemId();
        }


        dr = fd.getReference("Items").child(itemId);

        addEditedItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemNameText = ItemEditedName.getText().toString();
                String itemPriceText = ItemEditedPrice.getText().toString();
                String suitableText = suitableEdited.getText().toString();
                String urlText = urlEdited.getText().toString();
                String descText = descItemEdited.getText().toString();
                String itemLinkText = itemEditedLink.getText().toString();
                Map<String,Object> map = new HashMap<>();
                map.put("itemName",itemNameText);
                map.put("itemPrice",itemPriceText);
                map.put("suitableFor",suitableText);
                map.put("imageLink",urlText);
                map.put("itemDesc",descText);
                map.put("itemLink",itemLinkText);
                map.put("itemId",itemId);
                map.put("currectUser",fu.getUid());
//                Model m = new Model(itemNameText,itemPriceText,suitableText,urlText,descText,itemLinkText,itemId,fu.);
                dr.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        dr.updateChildren(map);
                        Toast.makeText(EditItemActivity.this, "Item Has Been Successfully Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditItemActivity.this,MainActivity.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditItemActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              dr.removeValue();
                Toast.makeText(EditItemActivity.this, "Item Has Been Successfully Deleted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditItemActivity.this,MainActivity.class));
            }
        });

    }
}