package com.example.firebasecrudapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ArrayAdapter.ItemClickInterface {
    private RecyclerView recycleView;
    private FloatingActionButton addButton,logOut;
    private ConstraintLayout bottomSheet;
    private FirebaseDatabase fd;
    private FirebaseAuth fu;
    private DatabaseReference dr;
    private ArrayList<Model> itemsList;
    private ArrayAdapter ad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycleView = findViewById(R.id.recycleView);
        addButton = findViewById(R.id.addButton);
        logOut = findViewById(R.id.logOut);
        bottomSheet = findViewById(R.id.bottomSheet);
        fd = FirebaseDatabase.getInstance();
        dr = fd.getReference("Items");
        fu = FirebaseAuth.getInstance();
        itemsList = new ArrayList<>();
         ad = new ArrayAdapter(itemsList,this,MainActivity.this);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
         recycleView.setAdapter(ad);
         addButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(MainActivity.this,AddItemActivity.class));
             }
         });

         getData();
         logOut.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(fu.getCurrentUser()!=null){
                     fu.signOut();
                     startActivity(new Intent(MainActivity.this,LoginActivity.class));
                 }
             }
         });
    }

    public void getData(){
        itemsList.clear();
        dr.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                Object user = fu.getUid();
                if(snapshot.getValue(Model.class).getCurrectUser().equals(fu.getUid())) {
                    itemsList.add(snapshot.getValue(Model.class));
                    ad.notifyDataSetChanged();

                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ad.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                ad.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ad.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemClick(int position) {
      displayBottomSheet(itemsList.get(position));
    }
    private void displayBottomSheet(Model item){
        final BottomSheetDialog bsd = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_layout,bottomSheet);
        bsd.setContentView(view);
        bsd.setCancelable(false);
        bsd.setCanceledOnTouchOutside(true);
        bsd.show();

        TextView itemNameBottomText = view.findViewById(R.id.itemNameBottomSheet);
        TextView itemPriceBottomText = view.findViewById(R.id.itemPriceBottom);
        TextView itemDescBottomText = view.findViewById(R.id.itemDescBottom);
        TextView itemSuitableBottomText = view.findViewById(R.id.itemSuitableBottom);
        ImageView itemImageBottom = view.findViewById(R.id.itemImageBottom);
        Button editBottom = view.findViewById(R.id.EditItemBottom);
        Button viewItemBottom = view.findViewById(R.id.viewItemBottom);

        itemNameBottomText.setText(item.getItemName());
        itemDescBottomText.setText(item.getItemDesc());
        itemPriceBottomText.setText(item.getItemPrice());
        itemSuitableBottomText.setText(item.getSuitableFor());
        if(item.getImageLink()==null||item.getImageLink().length()==0){
            itemImageBottom.setImageResource(R.drawable.ic_launcher_foreground);
        }else{

            Picasso.get().load(item.getImageLink()).error(R.drawable.ic_launcher_foreground).into(itemImageBottom);
        }
        editBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,EditItemActivity.class);
                i.putExtra("item",item);
                startActivity(i);
            }
        });
        viewItemBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.getItemLink()==null||item.getItemLink().length()==0){
                    Toast.makeText(MainActivity.this, "Item Link Is Not Provided", Toast.LENGTH_SHORT).show();

                }else{
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(item.getItemLink()));
                    startActivity(intent);
                }
            }
        });



    }
}