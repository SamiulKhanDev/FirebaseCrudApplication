package com.example.firebasecrudapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ArrayAdapter extends RecyclerView.Adapter<ArrayAdapter.ViewHolder> {
   private ArrayList<Model> items;
   private Context context;
   private ItemClickInterface itemClickInterface;

   ArrayAdapter(ArrayList<Model>items,Context context,ItemClickInterface itemClickInterface){
       this.items = items;
       this.context = context;
       this.itemClickInterface = itemClickInterface;
   }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.single_item_layout,parent,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
       Model cm = items.get(position);
       holder.getItemName().setText(cm.getItemName());
       holder.getItemPrice().setText("RS: "+cm.getItemPrice());
       if(cm.getImageLink()==null||cm.getImageLink().length()==0){
           holder.getItemImage().setImageResource(R.drawable.ic_launcher_foreground);
       }else{
           Picasso.get().load(cm.getImageLink()).error(R.drawable.ic_launcher_foreground).into(holder.getItemImage());
       }

       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
//               Toast.makeText(context, "Hello Sam", Toast.LENGTH_SHORT).show();//1:37
               itemClickInterface.onItemClick(position);
           }
       });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public interface ItemClickInterface{
       void onItemClick(int position);
    }

    public static class  ViewHolder extends RecyclerView.ViewHolder{
        private ImageView itemImage;
        private TextView itemName,itemPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemName = itemView.findViewById(R.id.itemName);
            itemPrice = itemView.findViewById(R.id.itemPrice);
        }

        public ImageView getItemImage() {
            return itemImage;
        }

        public TextView getItemName() {
            return itemName;
        }

        public TextView getItemPrice() {
            return itemPrice;
        }
    }


}
