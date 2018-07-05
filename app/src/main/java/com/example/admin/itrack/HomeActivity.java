package com.example.admin.itrack;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    private TextView tv1;
    private static final String location = "fonts/fontawesome-webfont.ttf";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Typeface iconFont = FontCache.get(location, getApplicationContext());
        tv1 = findViewById(R.id.firstIcon);
        tv1.setTypeface(iconFont);
//        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME);
//
//        tv1 = findViewById(R.id.firstIcon);
//        tv1.setTypeface(iconFont);
    }
}
