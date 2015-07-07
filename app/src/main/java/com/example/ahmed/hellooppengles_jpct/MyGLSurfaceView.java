package com.example.ahmed.hellooppengles_jpct;

import android.content.Context;
import android.graphics.PointF;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Created by Ahmed on 06.07.2015.
 */
public class MyGLSurfaceView extends GLSurfaceView {
    private final MyGLRenderer mRenderer;
    private PointF touchPoint = null;

    public MyGLSurfaceView(Context context) {
        super(context);
        // Set the Renderer for drawing on the GLSurfaceView
        //intouchHandle = new InputTouchHandler();
        mRenderer = new MyGLRenderer(context);
        setRenderer(mRenderer);

        // Render the view only when there is a change in the drawing data
        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }





    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float xpos = -1;
    private float ypos = -1;

    private float touchTurn = 0;
    private float touchTurnUp = 0;

    public boolean onTouchEvent(MotionEvent me) {

        if (me.getAction() == MotionEvent.ACTION_DOWN) {
            xpos = me.getX();
            ypos = me.getY();
            return true;
        }

        if (me.getAction() == MotionEvent.ACTION_UP) {
            xpos = -1;
            ypos = -1;
            touchTurn = 0;
            touchTurnUp = 0;
            return true;
        }

        if (me.getAction() == MotionEvent.ACTION_MOVE) {
            float xd = me.getX() - xpos;
            float yd = me.getY() - ypos;

            xpos = me.getX();
            ypos = me.getY();


            //touchTurn = xd / -100f;
            //touchTurnUp = yd / -100f;
            // reverse direction of rotation above the mid-line
            if (xpos > getHeight() / 2) {
                xd = xd * -1 ;
            }

            // reverse direction of rotation to left of the mid-line
            if (ypos < getWidth() / 2) {
                yd = yd * -1 ;
            }
            // set Y postion from cube
            mRenderer.setAngleY(
                    mRenderer.getAngleY() +
                            ((yd / -100f)));
            mRenderer.setAngleX(
                    mRenderer.getAngleX() +
                            ((xd / 100f)));
            requestRender();
        }

        try {
            Thread.sleep(15);
        } catch (Exception e) {
            // No need for this...
        }

        return super.onTouchEvent(me);
    }
}
