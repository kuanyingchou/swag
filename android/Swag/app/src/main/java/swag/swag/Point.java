package swag.swag;

import android.graphics.Canvas;
import android.graphics.Paint;

import org.parceler.Parcel;

@Parcel
public class Point {
    float x;
    float y;
    static float err = 0.00001f;

    public Point() {
        this(0, 0);
    }

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        Point that = (Point) o;
        final float distanceSquare =
                (that.x - this.x) * (that.x - this.x) +
                        (that.y - this.y) * (that.y - this.y);
        if (distanceSquare < err) return true;
        else return false;
    }


    public void draw(Canvas canvas, Paint paint, float size) {
        canvas.drawPoint(x * size, y * size, paint);
    }

    @Override
    public String toString() {
        return "("+x+", "+y+")";
    }
}
