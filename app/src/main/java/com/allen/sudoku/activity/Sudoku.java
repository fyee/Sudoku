package com.allen.sudoku.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.allen.sudoku.R;

public class Sudoku extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "sudoku";

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
        switch (view.getId()) {
            case R.id.about_button:
                Intent i = new Intent(this, About.class);
                startActivity(i);
                break;
            case R.id.new_game_button:
                openNewGameDialog();
                break;
            case R.id.continue_button:
                Intent in = new Intent(this, Graphics.class);
                startActivity(in);
                break;
            case R.id.exit_button:
                finish();
                break;

        }
    }

    private void openNewGameDialog() {
        new AlertDialog
                .Builder(this).setTitle(R.string.new_game_title)
                .setItems(R.array.difficulty, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d(TAG, "clicked on " + i);
                        startGame(i);
                    }
                }).show();
    }

    private void startGame(int i) {
        Intent intent = new Intent(this, Game.class);
        intent.putExtra(Game.KEY_DIFFICULTY, i);
        startActivity(intent);
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
