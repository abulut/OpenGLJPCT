package com.example.ahmed.hellooppengles_jpct;

import android.content.Context;
import android.graphics.PointF;
import android.opengl.GLSurfaceView;

import com.threed.jpct.Camera;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.GLSLShader;
import com.threed.jpct.Light;
import com.threed.jpct.Logger;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;
import com.threed.jpct.util.MemoryHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Ahmed on 06.07.2015.
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {
    //private static Context myContext;
    private boolean master = false;
    private FrameBuffer fb = null;
    private World world = null;
    private RGBColor back = new RGBColor(50, 50, 100);

    private float mAngleX;
    private float mAngleY;

    private Object3D cube = null;
    private int fps = 0;

    private Light sun = null;
    private PointF touchPoint = null;
    private GLSLShader shader = null;
    private long time = System.currentTimeMillis();

    public MyGLRenderer(Context context) {
    }

    public void onSurfaceChanged(GL10 gl, int w, int h) {
        if (fb != null) {
            fb.dispose();
        }
        fb = new FrameBuffer(gl, w, h);

        if (master == false) {

            world = new World();
            world.setAmbientLight(20, 20, 20);

            sun = new Light(world);
            sun.setIntensity(250, 250, 250);

            // Create a texture out of the icon...:-)
            //Texture texture = new Texture(BitmapHelper.rescale(BitmapHelper.convert(getResources().getDrawable(R.drawable.icon)), 64, 64));
            //TextureManager.getInstance().addTexture("texture", texture);

            cube = Primitives.getCube(10);
            cube.strip();
            cube.build();
            world.addObject(cube);
            cube.rotateY(mAngleY);

            Camera cam = world.getCamera();
            cam.moveCamera(Camera.CAMERA_MOVEOUT, 50);
            cam.lookAt(cube.getTransformedCenter());

            SimpleVector sv = new SimpleVector();
            sv.set(cube.getTransformedCenter());
            sv.y -= 100;
            sv.z -= 100;
            sun.setPosition(sv);
            MemoryHelper.compact();

            if (master == false) {
                Logger.log("Saving master Activity!");
                //master = OpenGLESJPCTActivity.this;
            }
        }
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    }

    public void onDrawFrame(GL10 gl) {
        if (mAngleY != 0) {
            cube.rotateY(mAngleY);
            mAngleY = 0;
        }

        if (mAngleX != 0) {
            cube.rotateX(mAngleX);
            mAngleX = 0;
        }

        fb.clear(back);
        world.renderScene(fb);
        world.draw(fb);
        fb.display();

        if (System.currentTimeMillis() - time >= 1000) {
            Logger.log(fps + "fps");
            fps = 0;
            time = System.currentTimeMillis();
        }
        fps++;
    }
    /**
     * Returns the rotation angle of the triangle shape (mTriangle).
     *
     * @return - A float representing the rotation angle.
     */
    public float getAngleX() { return mAngleX; }
    public float getAngleY() { return mAngleY; }
    public PointF getTouchPoint(){ return touchPoint; }

    /**
     * Sets the rotation angle of the triangle shape (mTriangle).
     */
    public void setAngleX(float angle) {
        mAngleX = angle;
    }
    public void setAngleY(float angle) {
        mAngleY = angle;
    }
    public void setTouchPoint(PointF point){ touchPoint = point; }
}

