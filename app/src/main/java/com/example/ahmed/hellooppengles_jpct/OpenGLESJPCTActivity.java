package com.example.ahmed.hellooppengles_jpct;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.threed.jpct.Logger;

import java.lang.reflect.Field;

public class OpenGLESJPCTActivity extends Activity{
    private static OpenGLESJPCTActivity master = null;

    private GLSurfaceView mGLView;
    private MyGLRenderer renderer = null;

    protected void onCreate(Bundle savedInstanceState) {

        Logger.log("onCreate");

        if (master != null) {
            copy(master);
        }
        super.onCreate(savedInstanceState);
        Logger.log("TEST");
        mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLView.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void copy(Object src) {
        try {
            Logger.log("Copying data from master Activity!");
            Field[] fs = src.getClass().getDeclaredFields();
            for (Field f : fs) {
                f.setAccessible(true);
                f.set(this, f.get(src));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
