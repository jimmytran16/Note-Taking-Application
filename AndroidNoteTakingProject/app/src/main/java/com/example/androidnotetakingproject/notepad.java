package com.example.androidnotetakingproject;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;


public class notepad extends AppCompatActivity{
    StringBuilder stringBuilder = new StringBuilder();
    Intent intent;
    EditText text;
    ImageButton backBtn,listbtn,saveBtn,deletebtn,shareBtn;
    int element = 0;
    EditText notepadEdit;
    JSONObject jsonDoc;
    String stringJson;


    boolean isClicked = false;
    boolean isDocSaved = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);
        text = (EditText) findViewById(R.id.noteEdit);
        backBtn = (ImageButton) findViewById(R.id.backBtn);
        listbtn = (ImageButton) findViewById(R.id.listBtn);
        saveBtn = (ImageButton) findViewById(R.id.saveBtn);
        deletebtn = (ImageButton) findViewById(R.id.deletebtn);
        shareBtn = (ImageButton) findViewById(R.id.sharedBtn);


        notepadEdit = (EditText) findViewById(R.id.noteEdit);
        Bundle transData = getIntent().getExtras();
//        Timer t = new java.util.Timer();
//        t.schedule(
//                new java.util.TimerTask() {
//                    @Override
//                    public void run() {
//                        ServerModel serverModel = new ServerModel(notepad.this);
//                        try{
//                            JsonObjectRequest requestInfo = (JsonObjectRequest) serverModel.refresh(new Response.Listener<JSONObject>() {
//                                        @Override
//                                        public void onResponse(JSONObject response) {
//                                            Log.d("Main_act",response+"");
//                                            //set the intent
//                                            try {
//                                               int ttl  = Integer.parseInt(response.getString("ttl"));
//                                                serverModel.setToken(response.getString("token"));
//                                            }catch(JSONException e){}
//                                        }
//                                    }
//
//                                    , new Response.ErrorListener() {
//                                        @Override
//                                        public void onErrorResponse(VolleyError error) {
//                                            Log.d("Main_act",error+"");
//                                        }
//                                    }
//                            );
//                        }catch(JSONException e){}
//
//                    }
//                },500000000
//        );
        if (transData == null) {
            Log.d("notepad_intent","TRANS IS NULL");

        } else if(!(transData==null)) {
            /*get element that was passed through the intent */
            element = transData.getInt("element");
            notepadEdit.setText(MainActivity.docsList.get(element).getText());
            isClicked = true;
        }


        ServerModel serverModel = new ServerModel(getApplicationContext());
        //new note
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent = new Intent(getApplicationContext(), DocumentListView.class);
                startActivity(intent);
            }
        });
        //go to the list
        listbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainActivity.checking= false;
                Log.d("notepad_checking",MainActivity.checking+"");
                intent = new Intent(getApplicationContext(), chooseDocuments.class);
                startActivity(intent);
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*IF TEXT IS IS NOT BEING MODIFIED THEN MAKE NEW DOC ELSE MODIFY CURRENT DOC */
                String currentText = String.valueOf(text.getText());
                //Create 30 character length name for the MainTitle of the list view
                if (currentText.length() > 25) {
                    for (int x = 0; x < 25; x++) {
                        stringBuilder.append(currentText.charAt(x));
                    }
                } else {
                    stringBuilder.append(currentText);
                }
//                if (!(text.getText().equals("")) && isClicked == false) {
//
////                    MainActivity.document = new Document(stringBuilder.toString()+"...");
////                    MainActivity.document.setText(String.valueOf(text.getText()));
////                    MainActivity.docsList.add(MainActivity.document);



                Gson jsonReader = new Gson();
                //convert the current doc to the json format

                ServerModel serverModel = new ServerModel(notepad.this);

                //check if we are updating a specific doc or we are creating a new one
                Log.d("check_if_isClicked",isClicked+"");
                if(isClicked==false) {

                    if(MainActivity.checking==false){
                        MainActivity.document = new Document(stringBuilder.toString() + ".....");
                        MainActivity.document.setText(String.valueOf(text.getText()));
                        stringJson =jsonReader.toJson(MainActivity.document);
                        Log.d("DOCCCCC",stringJson);
                        MainActivity.checking = true;
                    }
                    else if(MainActivity.checking==true){
                        MainActivity.document.setText(text.getText().toString());
                        MainActivity.document.setName(text.getText().toString());
                        stringJson = jsonReader.toJson(MainActivity.document);
                    }

                }

                else if(isClicked==true){
                    MainActivity.docsList.get(element).setName(stringBuilder.toString()+".....");
                    MainActivity.docsList.get(element).setText(String.valueOf(text.getText()));
                    stringJson = jsonReader.toJson(MainActivity.docsList.get(element));
                    Log.d("DOCCCCC",stringJson);


                }

                //send current document through server
                try {
                    jsonDoc = new JSONObject(stringJson);

                    JsonObjectRequest requestInfo = (JsonObjectRequest) serverModel.saveDoc(jsonDoc, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("Main_act", response + "");
                                    stringBuilder.setLength(0);
                                }
                            }
                            , new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("Main_act", error + "");
                                }
                            }
                    );
                } catch (JSONException e) {
                }

            }
        });
        //when you press share
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent;
                myIntent = new Intent(notepad.this,shareDocument.class);
                if(isClicked){
                    myIntent.putExtra("currentDocId",MainActivity.docsList.get(element).getID());
                    Log.d("document_idNOTEPAD",MainActivity.docsList.get(element).getID());

                }
                else{
                 myIntent.putExtra("currentDocId",MainActivity.document.getID());
                 Log.d("document_idNOTEPAD",MainActivity.docsList.get(element).getID());
                }
            startActivity(myIntent);
            }
        });
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String doctoDelete=MainActivity.docsList.get(element).getID();
                if (isClicked == true) {
                    try {

                        JsonObjectRequest requestInfo = (JsonObjectRequest) serverModel.deleteDoc(doctoDelete, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.d("Main_act", response + "");
                                        intent = new Intent(getApplicationContext(), DocumentListView.class);
                                        startActivity(intent);
                                    }
                                }
                                , new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("Main_act", error + "");
                                    }
                                }
                        );
                    } catch (JSONException e) {
                    }


                }


            }
        });


        //CLEAR BUTTON


    }
}


