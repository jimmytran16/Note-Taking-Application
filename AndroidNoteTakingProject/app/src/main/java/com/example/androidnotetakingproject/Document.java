package com.example.androidnotetakingproject;

//import org.apache.commons.lang3.builder.HashCodeBuilder;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class Document {
    @SerializedName("id")
    @Expose
    private String ID;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("creation_date")
    @Expose
    private long creationDate;
    @SerializedName("title")
    @Expose
    private String name;

    UUID uuid = UUID.randomUUID();

    public Document(){

    }
    public Document(String name) {
        this.name = name;
        this.creationDate = System.currentTimeMillis();
        this.ID = uuid.toString();
    }
    public void setID(String ID){
        this.ID=ID;
    }

    public String getID(){
        return ID;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }




    /*
     * We will use this to test if the value of the file has changed. We do this by hashing the
     * text in the doc and if there is a change it will be reflected in the returned hash value.
     */
//    @Override
//    public int hashCode() {
//        return HashCodeBuilder.reflectionHashCode(this, false);
//    }

    /*
     * We consider to documents the same if they have the same ID
     */
//    @Override
//    public boolean equals(Object obj) {
//        return obj instanceof Document ? ((Document) obj).ID.equals(ID) : false;
//   }

}
