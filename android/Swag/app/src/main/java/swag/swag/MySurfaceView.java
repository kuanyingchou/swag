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
    private float mSize;

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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //int size = Math.min(getRootView().getMeasuredWidth(), getRootView().getMeasuredHeight());
        int size = Math.min(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(size, size);
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

                mPaint.setStrokeWidth(2);
                final float oneThird = w/3;
                canvas.drawLine(oneThird, 0, oneThird, h, mPaint);
                canvas.drawLine(oneThird*2, 0, oneThird*2, h, mPaint);
                canvas.drawLine(0, oneThird, w, oneThird, mPaint);
                canvas.drawLine(0, oneThird*2, w, oneThird*2, mPaint);

                mWord.draw(canvas, mPaint);


                surfaceHolder.unlockCanvasAndPost(canvas);
                //Log.d(">>>>>>>>", "!");
            }
        }
    }

    public Word getWord() {
        return mWord;
    }

    public void setWord(Word word) {
        mWord = word;
    }

    public void reset() {
        mWord = new Word(mWord.getSize());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWord.setSize(mSize = getWidth()); //>>>
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        //Log.d(">>>>>>>>>>>", String.valueOf(mSize));
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mCurrentStroke = new Stroke();
                mCurrentStroke.add(new Point(event.getX() / mSize, event.getY() / mSize));
                mWord.add(mCurrentStroke);
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                mCurrentStroke.add(new Point(event.getX() / mSize, event.getY() / mSize));
                break;
            case MotionEvent.ACTION_UP:
                mCurrentStroke.add(new Point(event.getX() / mSize, event.getY() / mSize));
                mCurrentStroke.simplify();
                //Log.d(">>>>>>>", mWord.toString());
                break;

            default:
        }
        return true; //processed
    }

}