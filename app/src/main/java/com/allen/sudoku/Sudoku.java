package com.allen.sudoku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class Sudoku extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //使用数组方式处理
        View continueButton = findViewById(R.id.continue_button);
        View newButton = findViewById(R.id.new_game_button);
        View aboutButton = findViewById(R.id.about_button);
        View exitButton = findViewById(R.id.exit_button);

        continueButton.setOnClickListener(this);
        newButton.setOnClickListener(this);
        aboutButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        System.out.println(view.getId());
        System.out.println(R.id.about_button);
        switch (view.getId()) {
            case R.id.about_button:
                Intent i = new Intent(this, About.class);
                startActivity(i);
                break;
            case R.id.exit_button:
                Intent intent = new Intent(this, About.class);
                break;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(this, Prefs.class));
                return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
