package com.example.android.salesmanager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by raj garg on 12-05-2019.
 */




public class GraphView extends View {

    int m_graphArray[] = null;
    int m_maxY = 0;

    Paint m_paint;


    public GraphView(Context context) {
        super(context);
        init();
    }

    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GraphView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        m_paint = new Paint();
        m_paint.setColor(Color.BLUE);
        m_paint.setStrokeWidth(10);
    }

    public void setGraphArray(int Xi_graphArray[], int Xi_maxY)
    {
        m_graphArray = Xi_graphArray;
        m_maxY = Xi_maxY;
    }

    public void setGraphArray(int Xi_graphArray[])
    {
        int maxY = 0;
        for(int i = 0; i < Xi_graphArray.length; ++i)
        {
            if(Xi_graphArray[i] > maxY)
            {
                maxY = Xi_graphArray[i];
            }
        }
        setGraphArray(Xi_graphArray, maxY);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(m_graphArray == null)
        {
            return;
        }

        int maxX = m_graphArray.length;

        float factorX = getWidth() / (float)maxX;
        float factorY = getHeight() / (float)m_maxY;

        for(int i = 1; i < m_graphArray.length; ++i) {
            int x0 = i - 1;
            int y0 = m_graphArray[i-1];
            int x1 = i;
            int y1 = m_graphArray[i];

            int sx = (int)(x0 * factorX);
            int sy = getHeight() - (int)(y0* factorY);
            int ex = (int)(x1*factorX);
            int ey = getHeight() - (int)(y1* factorY);
            canvas.drawLine(sx, sy, ex, ey, m_paint);
        }
    }
}