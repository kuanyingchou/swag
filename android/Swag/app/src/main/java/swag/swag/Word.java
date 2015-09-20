package swag.swag;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Word {
    private float width;
    private float height;
    private float strokeWidth;
    private List<Stroke> mStrokes;

    public Word() {
        mStrokes = new ArrayList<>();
    }

    public void add(Stroke stroke) {
        mStrokes.add(stroke);
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(50);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);

        //draw old mStrokes
        if(mStrokes.size() > 1) {
            for (int i = 0; i < mStrokes.size() - 1; i++) {
                final Stroke stroke = mStrokes.get(i);
                stroke.draw(canvas, paint);
            }
        }

        //draw last stroke
        if(mStrokes.size() > 0) {
            mStrokes.get(mStrokes.size()-1).safeDraw(canvas, paint);
        }
    }

}
