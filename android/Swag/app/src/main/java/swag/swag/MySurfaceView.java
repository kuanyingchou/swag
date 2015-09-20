package swag.swag;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

//ref: http://android-coding.blogspot.com/2011/05/handle-ontouchevent-in-surfaceview.html
class MySurfaceView extends SurfaceView implements Runnable {

    Thread thread = null;
    SurfaceHolder surfaceHolder;
    volatile boolean running = false;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private List<Stroke> mStrokes = new ArrayList<>();
    private Stroke mCurrentStroke;

    public MySurfaceView(Context context) {
        super(context);
        surfaceHolder = getHolder();
    }

    public void onResumeMySurfaceView() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void onPauseMySurfaceView() {
        boolean retry = true;
        running = false;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    @Override
    public void run() {
        // TODO Auto-generated method stub
        //Log.d(">>>>>>>>", "start running");

        while (running) {
            if (surfaceHolder.getSurface().isValid()) {
                Canvas canvas = surfaceHolder.lockCanvas();

                //clear background
                mPaint.setColor(Color.WHITE);
                canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), mPaint);

                mPaint.setColor(Color.BLACK);
                mPaint.setStrokeWidth(50);
                mPaint.setStrokeCap(Paint.Cap.ROUND);
                mPaint.setStrokeJoin(Paint.Join.ROUND);

                //draw old mStrokes
                if(mStrokes.size() > 1) {
                    for (int i = 0; i < mStrokes.size() - 1; i++) {
                        final Stroke stroke = mStrokes.get(i);
                        stroke.draw(canvas, mPaint);
                    }
                }

                //draw last stroke
                if(mStrokes.size() > 0) {
                    mCurrentStroke.safeDraw(canvas, mPaint);
                }

                surfaceHolder.unlockCanvasAndPost(canvas);
                //Log.d(">>>>>>>>", "!");
            }
        }
    }

    public void refresh() {
        mStrokes = new ArrayList<>();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mCurrentStroke = new Stroke();
                mCurrentStroke.add(new Point(event.getX(), event.getY()));
                mStrokes.add(mCurrentStroke);
                break;
            case MotionEvent.ACTION_MOVE:
                mCurrentStroke.add(new Point(event.getX(), event.getY()));
                break;
            case MotionEvent.ACTION_UP:
                mCurrentStroke.add(new Point(event.getX(), event.getY()));
                mCurrentStroke.simplify();
                Log.d(">>>>>>>", mStrokes.toString());
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_OUTSIDE:
                break;
            default:
        }
        return true; //processed
    }

}