package com.example.androidnotetakingproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class chooseDocuments extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_choose_doc);

        Button yourDocsBtn = (Button)findViewById(R.id.YourDocsBtn);
        Button yourSharedDocs= (Button)findViewById(R.id.SharedDocsBtn);
        Button AllDocsBtn = (Button)findViewById(R.id.AllDocsBtn);
        Intent intent = new Intent(chooseDocuments.this,DocumentListView.class);


        yourDocsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("scope",0);
                startActivity(intent);
            }
        });
        yourSharedDocs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("scope",1);
                startActivity(intent);
            }
        });
        AllDocsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                intent.putExtra("scope",2);
                startActivity(intent);
            }
        });

        }
    }

