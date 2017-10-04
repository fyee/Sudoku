package com.allen.sudoku.activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.allen.sudoku.R;

public class Graphics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GraphicsView(this));
    }


    static public class GraphicsView extends View {
        public GraphicsView(Context context) {
            super(context);
        }


        @Override
        protected void onDraw(Canvas canvas) {
            Path circle = new Path();
            circle.addCircle(350, 350, 200 , Path.Direction.CCW);
            int color = getResources().getColor(R.color.pink,getContext().getTheme());
            Paint paint  = new Paint();
            paint.setColor(color);
            canvas.drawPath(circle,paint);

            Paint tPaint  = new Paint();
            tPaint.setColor(Color.BLUE);
            String str = "Now is the time for all good men to come to the aid of their country.";
            canvas.drawTextOnPath(str, circle,0, 10,tPaint );
        }
    }
}
