package swag.swag;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

//ref: http://android-coding.blogspot.com/2011/05/handle-ontouchevent-in-surfaceview.html
class MySurfaceView extends SurfaceView implements Runnable {

    Thread thread = null;
    SurfaceHolder surfaceHolder;
    volatile boolean running = false;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Word mWord = new Word();
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
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), mPaint);

                //draw frames
                final float w = canvas.getWidth();
                final float h = canvas.getHeight();
                mPaint.setColor(Color.RED);
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setStrokeWidth(10);
                canvas.drawRect(0, 0, w, h, mPaint);

                mPaint.setStrokeWidth(1);
                final float oneThrid = w/3;
                canvas.drawLine(oneThrid, 0, oneThrid, h, mPaint);
                canvas.drawLine(oneThrid*2, 0, oneThrid*2, h, mPaint);
                canvas.drawLine(0, oneThrid, w, oneThrid, mPaint);
                canvas.drawLine(0, oneThrid*2, w, oneThrid*2, mPaint);

                mWord.draw(canvas, mPaint);


                surfaceHolder.unlockCanvasAndPost(canvas);
                //Log.d(">>>>>>>>", "!");
            }
        }
    }

    public Word getStrokes() {
        return mWord;
    }

    public void setStrokes(Word strokes) {
        mWord = strokes;
    }

    public void refresh() {
        mWord = new Word();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mCurrentStroke = new Stroke();
                mCurrentStroke.add(new Point(event.getX(), event.getY()));
                mWord.add(mCurrentStroke);
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                mCurrentStroke.add(new Point(event.getX(), event.getY()));
                break;
            case MotionEvent.ACTION_UP:
                mCurrentStroke.add(new Point(event.getX(), event.getY()));
                mCurrentStroke.simplify();
                //Log.d(">>>>>>>", mWord.toString());
                break;

            default:
        }
        return true; //processed
    }

}