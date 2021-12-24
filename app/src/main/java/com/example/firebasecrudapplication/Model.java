package com.example.firebasecrudapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class Model implements Parcelable{
    private String itemName;
    private String itemPrice;
    private String suitableFor;
    private String imageLink;
    private String itemLink;
    private String itemDesc;
    private String itemId;
    private String currectUser;

   public Model(){

    }

    Model(String itemName,String itemPrice,String suitableFor,String imageLink,String itemLink,String itemDesc,String itemId,String currentUser){
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.suitableFor = suitableFor;
        this.imageLink = imageLink;
        this.itemLink = itemLink;
        this.itemDesc = itemDesc;
        this.itemId = itemId;
        this.currectUser = currentUser;
    }


    protected Model(Parcel in) {
        itemName = in.readString();
        itemPrice = in.readString();
        suitableFor = in.readString();
        imageLink = in.readString();
        itemLink = in.readString();
        itemDesc = in.readString();
        itemId = in.readString();
        currectUser= in.readString();
    }

    public static final Creator<Model> CREATOR = new Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel in) {
            return new Model(in);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };

    public String getItemName() {
        return itemName;
    }


    public String getItemPrice() {
        return itemPrice;
    }


    public String getSuitableFor() {
        return suitableFor;
    }


    public String getImageLink() {
        return imageLink;
    }



    public String getItemLink() {
        return itemLink;
    }


    public String getItemDesc() {
        return itemDesc;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemName);
        dest.writeString(itemPrice);
        dest.writeString(suitableFor);
        dest.writeString(imageLink);
        dest.writeString(itemLink);
        dest.writeString(itemDesc);
        dest.writeString(itemId);
    }

    public String getItemId() {
        return itemId;
    }

    public String getCurrectUser() {
        return currectUser;
    }
}
