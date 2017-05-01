package com.example.daniel.bikesharing;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.Locale;

public class LanguageActivity extends AppCompatActivity {

    @Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_language);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolLanguage);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(R.string.language);
        setSupportActionBar(toolbar);}

//Button pour passer en FR

    public void changeToFR(View v)
    {
        String languageToLoad  = "fr";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config,v.getResources().getDisplayMetrics());

        Intent intent = new Intent(this, LanguageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    //Button pour passer en ALL

    public void changeToALL(View v)
    {
        String languageToLoad  = "de";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config,v.getResources().getDisplayMetrics());

        Intent intent = new Intent(this, LanguageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    //Button pour passer en ENG

    public void changeToEN(View v)
    {
        String languageToLoad  = "en"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config,v.getResources().getDisplayMetrics());

        Intent intent = new Intent(this, LanguageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
