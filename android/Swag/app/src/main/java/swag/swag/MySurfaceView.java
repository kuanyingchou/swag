package swag.swag;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//ref: http://android-coding.blogspot.com/2011/05/handle-ontouchevent-in-surfaceview.html
class MySurfaceView extends SurfaceView implements Runnable {
    private class Point {
        private float x;
        private float y;
        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
        public float getX() { return x; }
        public float getY() { return y; }

    }
    Thread thread = null;
    SurfaceHolder surfaceHolder;
    volatile boolean running = false;

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Random random;

    volatile boolean touched = false;
    //volatile float touched_x, touched_y;
    private List<List<Point>> strokes = new ArrayList<>();
    private List<Point> mCurrentStroke;


    public MySurfaceView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        surfaceHolder = getHolder();
        random = new Random();
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
                paint.setColor(Color.WHITE);
                canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);

                paint.setStrokeWidth(50);
                paint.setStrokeCap(Paint.Cap.ROUND);
                paint.setColor(Color.BLACK);

                //draw old strokes
                if(strokes.size() > 1) {
                    for (int i = 0; i < strokes.size() - 1; i++) {
                        final List<Point> stroke = strokes.get(i);
                        drawStroke(stroke, canvas);
                    }
                }
                if(strokes.size() > 0) {
                    final List<Point> copy = new ArrayList<>(mCurrentStroke); //copy
                    drawStroke(copy, canvas);
                }



                surfaceHolder.unlockCanvasAndPost(canvas);
                //Log.d(">>>>>>>>", "!");
            }
        }
    }

    private void drawStroke(List<Point> stroke, Canvas canvas) {
        if(stroke.size() == 1) {
            final Point p = stroke.get(0);
            canvas.drawPoint(p.getX(), p.getY(), paint);
        } else if(stroke.size() > 1) {
            for(int i = 0; i<stroke.size()-1; i++) {
                final Point start = stroke.get(i);
                final Point stop = stroke.get(i+1);
                canvas.drawLine(start.getX(), start.getY(), stop.getX(), stop.getY(), paint);
            }
        } else {
            //empty stroke???
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                touched = true;
                mCurrentStroke = new ArrayList<Point>();
                mCurrentStroke.add(new Point(event.getX(), event.getY()));
                strokes.add(mCurrentStroke);
                break;
            case MotionEvent.ACTION_MOVE:
                touched = true;
                mCurrentStroke.add(new Point(event.getX(), event.getY()));
                break;
            case MotionEvent.ACTION_UP:
                touched = false;
                mCurrentStroke.add(new Point(event.getX(), event.getY()));
                break;
            case MotionEvent.ACTION_CANCEL:
                touched = false;
                break;
            case MotionEvent.ACTION_OUTSIDE:
                touched = false;
                break;
            default:
        }
        return true; //processed
    }

}