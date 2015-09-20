package swag.swag;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Word {
    //float size;

    List<Stroke> mStrokes;

    public Word() {
        mStrokes = new ArrayList<>();;
    }

//    public void setSize(float s) { size = s; }
//    public float getSize() { return size; }

    public void add(Stroke stroke) {
        mStrokes.add(stroke);
    }

    public void draw(Canvas canvas, Paint paint, float size) {
        paint.setColor(Color.BLACK);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);

        //draw old mStrokes
        if(mStrokes.size() > 1) {
            for (int i = 0; i < mStrokes.size() - 1; i++) {
                final Stroke stroke = mStrokes.get(i);
                stroke.draw(canvas, paint, size);
            }
        }

        //draw last stroke
        if(mStrokes.size() > 0) {
            mStrokes.get(mStrokes.size()-1).safeDraw(canvas, paint, size);
        }
    }

    public int getPointCount() {
        int sum = 0;
        for(int i=0; i<mStrokes.size(); i++) {
            sum += mStrokes.get(i).points.size();
        }
        return sum;
    }

    public String toString() {
        return mStrokes.toString();
    }
}
