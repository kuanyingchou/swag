package swag.swag;

import android.graphics.Canvas;
import android.graphics.Paint;

class Point {
    private float x;
    private float y;
    private static float err = 0.01f;

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


    public void draw(Canvas canvas, Paint paint) {
        canvas.drawPoint(x, y, paint);
    }

    @Override
    public String toString() {
        return "("+x+", "+y+")";
    }
}
