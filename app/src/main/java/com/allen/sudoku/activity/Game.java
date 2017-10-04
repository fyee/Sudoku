package com.allen.sudoku.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.allen.sudoku.R;
import com.allen.sudoku.view.PuzzleView;

public class Game extends AppCompatActivity {

    private static final String TAG = "sudoku";

    public static final String KEY_DIFFICULTY = "com.allen.sudoku.difficulty";
    public static final int DIFFICULTY_EASY = 0;
    public static final int DIFFICULTY_MEDIUM = 1;
    public static final int DIFFICULTY_HARD = 2;

    private int puzzle[] = new int[9 * 9];
    private PuzzleView puzzleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate");

        int diff = getIntent().getIntExtra(KEY_DIFFICULTY, DIFFICULTY_EASY);

        // get puzzle
        // calculate used titles
        puzzleView = new PuzzleView(this);
        setContentView(puzzleView);
        puzzleView.requestFocus();
    }
}
