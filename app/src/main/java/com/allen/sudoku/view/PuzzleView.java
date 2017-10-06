package com.allen.sudoku.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;

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
        //draw  board
        Paint background = new Paint();
        background.setColor(getResources().getColor(R.color.puzzle_background, getContext().getTheme()));

        canvas.drawRect(0, 0, getWidth(), getHeight(), background);

        Paint dark = new Paint();
        dark.setColor(getResources().getColor(R.color.puzzle_dark, getContext().getTheme()));

        Paint white = new Paint();
        white.setColor(getResources().getColor(R.color.puzzle_hilite, getContext().getTheme()));
        Paint light = new Paint();
        light.setColor(getResources().getColor(R.color.puzzle_light, getContext().getTheme()));

        //draw grid lines
        for (int i = 0; i < 9; i++) {
            canvas.drawLine(0, i * height, getWidth(), i * height, getPaint(dark, light, i));
            canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1, white);
            canvas.drawLine(i * width, 0, i * width, getHeight(), getPaint(dark, light, i));
            canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(), white);

        }

        //draw the numbers

        Paint foreground = new Paint(Paint.ANTI_ALIAS_FLAG);
        foreground.setColor(getResources().getColor(R.color.puzzle_foreground, getContext().getTheme()));
        foreground.setStyle(Paint.Style.FILL);
        foreground.setTextSize(height * 0.75f);
        foreground.setTextScaleX(width / height);
        foreground.setTextAlign(Paint.Align.CENTER);

        Paint.FontMetrics fm = foreground.getFontMetrics();

        float x = width / 2;
        float y = height / 2 - (fm.ascent + fm.descent) / 2;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                canvas.drawText(this.game.getTileString(i, j), i * width + x, j * height + y, foreground);
            }
        }

        //draw the selection
        Log.d(TAG, "selRect: 94" + selRect);
        Paint selectedPaint = new Paint();
        selectedPaint.setColor(getResources().getColor(R.color.puzzle_selected, getContext().getTheme()));
        canvas.drawRect(selRect, selectedPaint);

        //Draw the hints...
        Paint hint = new Paint();
        int c[] = {getResources().getColor(R.color.puzzle_hint_0, getContext().getTheme()),
                getResources().getColor(R.color.puzzle_hint_1, getContext().getTheme()),
                getResources().getColor(R.color.puzzle_hint_2, getContext().getTheme())};

        Rect r = new Rect();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Log.d(TAG, "onDraw: 109 " + i + " " + j + " " + this.game.getUsedTiles(i, j));
                int movesLeft = 9 - game.getUsedTiles(i, j).length;
                if (movesLeft < c.length) {
                    getRect(i, j, r);
                    hint.setColor(c[movesLeft]);
                    canvas.drawRect(r, hint);
                }
            }

        }

        // Draw the selection...
        Log.d(TAG, "selRect=" + selRect);
        Paint selected = new Paint();
        selected.setColor(getResources().getColor(
                R.color.puzzle_selected, getContext().getTheme()));
        canvas.drawRect(selRect, selected);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return super.onTouchEvent(event);
        }
        select((int) (event.getX() / width), (int) (event.getY() / height));
        game.showKeypadOrError(selX, selY);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyDown: keyCode= " + keyCode + ", event = " + event);
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                select(selX, selY - 1);
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                select(selX, selY + 1);
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                select(selX - 1, selY);
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                select(selX + 1, selY);
                break;

            case KeyEvent.KEYCODE_0:
            case KeyEvent.KEYCODE_SPACE:
                setSelectedTile(0);
                break;
            case KeyEvent.KEYCODE_1:
                setSelectedTile(1);
                break;
            case KeyEvent.KEYCODE_2:
                setSelectedTile(2);
                break;
            case KeyEvent.KEYCODE_3:
                setSelectedTile(3);
                break;
            case KeyEvent.KEYCODE_4:
                setSelectedTile(4);
                break;
            case KeyEvent.KEYCODE_5:
                setSelectedTile(5);
                break;
            case KeyEvent.KEYCODE_6:
                setSelectedTile(6);
                break;
            case KeyEvent.KEYCODE_7:
                setSelectedTile(7);
                break;
            case KeyEvent.KEYCODE_8:
                setSelectedTile(8);
                break;
            case KeyEvent.KEYCODE_9:
                setSelectedTile(9);
                break;
            case KeyEvent.KEYCODE_ENTER:
            case KeyEvent.KEYCODE_DPAD_CENTER:
                game.showKeypadOrError(selX, selY);
                break;
            default:
                return super.onKeyDown(keyCode, event);
        }

        return true;
    }

    public void setSelectedTile(int tile) {
        if (game.setTileIfValid(selX, selY, tile)) {
            invalidate(selRect);
        } else {
            Log.d(TAG, "setSelectedTile:  invalid = " + tile);
            startAnimation(AnimationUtils.loadAnimation(game, R.anim.shake));
        }
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        super.setOnTouchListener(l);
    }

    private void select(int x, int y) {
        invalidate(selRect);
        selX = Math.min(Math.max(x, 0), 8);
        selY = Math.min(Math.max(y, 0), 8);
        getRect(selX, selY, selRect);
        invalidate(selRect);
    }

    private Paint getPaint(Paint dark, Paint light, int i) {
        return (i % 3 != 0) ? light : dark;
    }


    private void getRect(int x, int y, Rect rect) {
        rect.set((int) (x * width), (int) (y * height), (int) (x * width + width), (int) (y * height + height));
    }
}
