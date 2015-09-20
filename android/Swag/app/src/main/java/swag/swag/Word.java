package swag.swag;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

public class Word {
    private List<Stroke> mStrokes;

    public Word() {
        mStrokes = new ArrayList<>();
    }

    public void add(Stroke stroke) {
        mStrokes.add(stroke);
    }

    public void draw(Canvas canvas, Paint paint) {
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
