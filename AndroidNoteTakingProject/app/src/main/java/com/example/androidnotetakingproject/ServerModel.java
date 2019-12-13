package com.example.androidnotetakingproject;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import com.example.androidnotetakingproject.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ServerModel {
    private final RequestQueue QUEUE;
//    final String MY_TOKEN = "c2FnYXJAa2FydHBheS5jb206cnMwM2UxQUp5RnQzNkQ5NDBxbjNmUDgzNVE3STAyNzI";


    public static final String URL = "http://192.168.56.1:12345"; //REPLACE WITH YOUR SERVER URL
    private static String token;

    public ServerModel(Context context) {
        // Instantiate the RequestQueue.
        QUEUE = Volley.newRequestQueue(context);
    }

    public void setToken(String token) {
        this.token = token;
    }


    public Request authenticate(final String email, final String password, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) throws JSONException {
        final String MY_TOKEN = "c2FnYXJAa2FydHBheS6jb206cnMwM2UxQUp5RnQzNkQ5NDBxbjNmUDgzNVE3STAyNzI";

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("method", "authenticate");
        jsonBody.put("password", password);
        jsonBody.put("email", email);
        jsonBody.put("time_unit", "MINUTES");
        jsonBody.put("time_span", 30);

        JsonObjectRequest jsonPostRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, successListener, errorListener) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                //Token
                headers.put("token", MY_TOKEN);//put your token here
                return headers;
            }
        };

        // Add the request to the RequestQueue.
        return QUEUE.add(jsonPostRequest);
    }

    public Request refresh(Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) throws JSONException {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("method", "refresh");
        jsonBody.put("time_unit", TimeUnit.MINUTES.name());
        jsonBody.put("time_span", 30);

        JsonObjectRequest jsonPostRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, successListener, errorListener) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                //Token
                headers.put("token", token);//put your token here
                return headers;
            }
        };

        return QUEUE.add(jsonPostRequest);
    }

    public Request saveDoc(JSONObject myDoc, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) throws JSONException {
        JSONObject jString = new JSONObject();
        jString.put("method", "setDocument");
        Log.d("savefunc", "My json save doc: " + myDoc);
        try {
            Log.d("savefunc", String.valueOf(myDoc));
            jString.put("document", myDoc);
        } catch (JSONException err) {
            System.out.println("Error: "+err);
        }

        Log.d("savefunc", "My json body save doc: " + jString);
        JsonObjectRequest jsnBody = new JsonObjectRequest(Request.Method.POST, URL, jString, successListener, errorListener) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                //Token
                headers.put("autho_token", token);//put your token here
                Log.d("savefuncheader",headers+"");
                return headers;
            }
        };

        return QUEUE.add(jsnBody);
    }

    protected Request getTheDocuments(int scope,Response.Listener<JSONArray> successListener, Response.ErrorListener errorListener) throws JSONException {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("method", "getDocuments");
            jsonBody.put("scope", scope);
            String jsonString = jsonBody.toString();

        JsonArrayRequest jsonPostRequest = new JsonArrayRequest(Request.Method.POST, URL, jsonString, successListener, errorListener) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                //Token
                headers.put("autho_token", token);//put your token here
                Log.d("getDocfuncheader",headers+"");
                return headers;
            }
        };

        return QUEUE.add(jsonPostRequest);
    }


    public Request signOut(Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) throws JSONException {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("method", "signOut");

        JsonObjectRequest jsonPostRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, successListener, errorListener) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                //Token
                headers.put("autho_token", token);//put your token here
                return headers;
            }

        };
        return QUEUE.add(jsonPostRequest);
    }

    public Request forgotPassword(String email, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) throws JSONException {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("method", "forgotPassword");
        jsonBody.put("email", email);

        JsonObjectRequest jsonPostRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, successListener, errorListener);

        return QUEUE.add(jsonPostRequest);
    }
    public Request setDocumentAccessors (String docID, String email, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) throws JSONException {
        JSONObject jsonBody = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(email);
        jsonBody.put("method", "setDocumentAccessors");
        jsonBody.put("document_id", docID);
        jsonBody.put("accessors", jsonArray);

        JsonObjectRequest jsonPostRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, successListener, errorListener) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                //Token
                headers.put("autho_token", token);//put your token here
                return headers;
            }
        };

        return QUEUE.add(jsonPostRequest);
    }
    public Request getDocumentAccessors (String docID, String email, Response.Listener<JSONArray> successListener, Response.ErrorListener errorListener) throws JSONException {
        JSONObject jsonBody = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(email);
        jsonBody.put("method", "setDocumentAccessors");
        jsonBody.put("document_id", docID);
        jsonBody.put("accessors", jsonArray);

        String jsonString = jsonBody.toString();
        JsonArrayRequest jsonPostRequest = new JsonArrayRequest(Request.Method.POST, URL, jsonString, successListener, errorListener) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                //Token
                headers.put("autho_token", token);//put your token here
                return headers;
            }
        };

        return QUEUE.add(jsonPostRequest);
    }
    public Request getAllAccounts(Response.Listener<JSONArray> successListener, Response.ErrorListener errorListener) throws JSONException {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("method", "getAllAccounts");
        String jsonString = jsonBody.toString();
        JsonArrayRequest jsonPostRequest = new JsonArrayRequest(Request.Method.POST, URL, jsonString, successListener, errorListener) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                //Token
                headers.put("autho_token", token);//put your token here
                return headers;
            }
        };

        return QUEUE.add(jsonPostRequest);
    }

    public Request createAccount(String email, String firstName, String lastName, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) throws JSONException {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("method", "createAccount");
        jsonBody.put("first_name", firstName);
        jsonBody.put("last_name", lastName);
        jsonBody.put("email", email);
        jsonBody.put("extra", "Hello");

        JsonObjectRequest jsonPostRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, successListener, errorListener);

        return QUEUE.add(jsonPostRequest);
    }

    public Request deleteAccount(Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) throws JSONException {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("method", "deleteAccount");


        JsonObjectRequest jsonPostRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, successListener, errorListener) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                //Token
                headers.put("autho_token", token);//put your token here
                return headers;
            }
        };

        return QUEUE.add(jsonPostRequest);
    }
    public Request deleteDoc(String docId, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) throws JSONException {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("method", "deleteDocument");
        jsonBody.put("document_id", docId);


        JsonObjectRequest jsonPostRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, successListener, errorListener) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                //Token
                headers.put("autho_token", token);//put your token here
                return headers;
            }
        };

        return QUEUE.add(jsonPostRequest);
    }

    public Request setAccount(String firstName, String lastName, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) throws JSONException {

        JSONObject jsonBody = new JSONObject();
        JSONObject jsonBody2 = new JSONObject();

       jsonBody2.put("first_name", firstName);
       jsonBody2.put("last_name", lastName);
       jsonBody.put("method", "setAccount");
       jsonBody.put("account",jsonBody2);
       Log.d("servermodel_setAcc",jsonBody.toString());

        JsonObjectRequest jsonPostRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, successListener, errorListener) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                //Token
                headers.put("autho_token", token);
                return headers;
            }
        };

        return QUEUE.add(jsonPostRequest);
    }

    public Request registerAccount(String email, String password, String temp_password, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) throws JSONException {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("method", "registerAccount");
        jsonBody.put("password", password);
        jsonBody.put("temp_password", temp_password);
        jsonBody.put("email", email);

        JsonObjectRequest jsonPostRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, successListener, errorListener);

        return QUEUE.add(jsonPostRequest);
    }
}



