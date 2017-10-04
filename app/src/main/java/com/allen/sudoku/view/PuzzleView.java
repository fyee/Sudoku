package com.allen.sudoku.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import com.allen.sudoku.R;
import com.allen.sudoku.activity.Game;

/**
 * Created by allen on 04/10/2017.
 */

public class PuzzleView extends View {

    private static final String TAG = "Sudoku";

    private float width;
    private float height;
    private int selX;
    private int selY;
    private final Rect selRect = new Rect();

    private final Game game;

    public PuzzleView(Context context) {
        super(context);
        this.game = (Game) context;
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w / 9f;
        height = h / 9f;
        getRect(selX, selY, selRect);
        Log.d(TAG, "onSizeChanged: " + width + ", height" + height);
        super.onSizeChanged(w, h, oldw, oldw);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);

        Paint background = new Paint();
        background.setColor(getResources().getColor(R.color.puzzle_background, getContext().getTheme()));

        canvas.drawRect(0, 0, getWidth(), getHeight(), background);

        Paint dark = new Paint();
        dark.setColor(getResources().getColor(R.color.puzzle_dark, getContext().getTheme()));

        Paint white = new Paint();
        white.setColor(getResources().getColor(R.color.puzzle_hilite, getContext().getTheme()));
        Paint light = new Paint();
        light.setColor(getResources().getColor(R.color.puzzle_light, getContext().getTheme()));

        for (int i = 0; i < 9; i++) {
            canvas.drawLine(0, i * height, getWidth(), i * height, getPaint(dark, light, i));
            canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1, white);
            canvas.drawLine(i * width, 0, i * width, getHeight(), getPaint(dark, light, i));
            canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(), white);

        }

    }

    private Paint getPaint(Paint dark, Paint light, int i) {
        return (i % 3 != 0) ? light : dark;
    }


    private void getRect(int x, int y, Rect rect) {
        rect.set((int) (x * width), (int) (y * height), (int) (x * width + width), (int) (y * height + height));
    }
}
