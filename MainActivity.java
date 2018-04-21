/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.marry.miwok_starter_code;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        //set the UP activity


        mediaPlayer = new MediaPlayer();
        //find the view that shows the numbers category
        TextView numbers = findViewById(R.id.numbers);

        //Apelam setOnClickListener de numbers - id-ul view-ului pe care o sa dam click - pt un obiect nou creat al lui OnClickListener
        numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cream un nou intent pentru a ne deschide o alta fereastra cand dam click pe view-ul care are id-ul "numbers"
                Intent i = new Intent(MainActivity.this, NumbersActivity.class);
                startActivity(i);
            }
        });

        // find the view that shows the family category
        TextView family = findViewById(R.id.family);

        //facem un obiect al lui OnClickListeners inline, pe care il punem ca parametru pt a apela setOnClickListener pt family
        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cream un nou intent pentru a ne deschide o alta fereastra cand dam click pe view-ul care are id-ul "family"
                Intent i = new Intent(MainActivity.this, FamilyActivity.class);
                startActivity(i);
            }
        });

        // find the view that shows the colors category
        TextView colors = findViewById(R.id.colors);

        //facem un obiect al lui OnClickListeners inline, pe care il punem ca parametru pt a apela setOnClickListener pt colors
        colors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cream un nou intent pentru a ne deschide o alta fereastra cand dam click pe view-ul care are id-ul "colors"
                Intent i = new Intent(MainActivity.this, ColorsActivity.class);
                startActivity(i);
            }
        });

        // find the view that shows the phrases category
        TextView phrases = findViewById(R.id.phrases);

        //facem un obiect al lui OnClickListeners inline, pe care il punem ca parametru pt a apela setOnClickListener pt phrases
        phrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cream un nou intent pentru a ne deschide o alta fereastra cand dam click pe view-ul care are id-ul "phrases"
                Intent i = new Intent(MainActivity.this, PhrasesActivity.class);
                startActivity(i);
            }
        });
    }
}
