package com.example.androidnotetakingproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.androidnotetakingproject.MainActivity;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class DocumentListView extends AppCompatActivity {
    DocumentListAdapter adapter ;
    Button  logoutBtn;
    ImageButton newBtn, settingBtn;
    ListView listView;
    ArrayList<JSONObject> json_doc_list = new ArrayList<>();
    Intent intent;
    JSONArray record;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_list_view);
        int scope;
        ServerModel  serverModel   = new ServerModel(DocumentListView.this);
        serverModel.setToken(MainActivity.token);
        listView = (ListView) findViewById(R.id.listView);
        newBtn = (ImageButton) findViewById(R.id.newbtn);
        settingBtn = (ImageButton)findViewById(R.id.settingbtn);
        Bundle scopeIntent = getIntent().getExtras();
        if(scopeIntent!=null){scope = scopeIntent.getInt("scope");}
        else{scope = 0;}

        try {

            serverModel.setToken(MainActivity.token);
            Log.d("NOTEPAD_SETTOKEN",MainActivity.token+"");

            //request to get document and load into docList
            JsonArrayRequest requestInfo = (JsonArrayRequest) serverModel.getTheDocuments(scope,new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        //clear list before we populate docs
                        MainActivity.docsList.clear();
                        for (int x = 0; x < response.length(); x++) {
                            //get the objects from the JSONArray and extract information to Doc Objects --->add to list
                            JSONObject object = (JSONObject) response.get(x);
                            MainActivity.document = new Document();
                            MainActivity.document.setName(object.getString("title"));
                            MainActivity.document.setID(object.getString("id"));
                            MainActivity.document.setText(object.getString("text"));
                            MainActivity.document.setCreationDate(Long.parseLong(object.getString("creation_date")));
                            Log.d("LIST_VIEW_DOC_REQUEST", "JSONARRAY-"+response + "");
                            MainActivity.docsList.add(MainActivity.document);
                        }
                        adapter = new DocumentListAdapter(getApplicationContext(),R.layout.list_layout, MainActivity.docsList);
                        listView.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                }
                    , new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("LIST_VIEW_DOC_REQUEST", error + "");
                        }
                    }

            );
        } catch (JSONException e) {
        }


        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(),UserSetting.class);
                startActivity(intent);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            /*when item is clicked pass in the postion element into the intent*/
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                intent = new Intent(getApplicationContext(), notepad.class);
                intent.putExtra("element",i);
                startActivity(intent);
            }
        });


        /*Redirect to notepad when click new btn*/
        newBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(),notepad.class);
                startActivity(intent);
            }
        });

        logoutBtn = (Button)findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //signout
                    Request request = (Request) serverModel.signOut(new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("signoutReq_listview", "response: " + response);
                            intent = new Intent(DocumentListView.this, MainActivity.class);
                            startActivity(intent);

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("signoutReq_listview", error + "");
                        }
                    });


                } catch (JSONException e) {
                }
            }
        });

    }
}
