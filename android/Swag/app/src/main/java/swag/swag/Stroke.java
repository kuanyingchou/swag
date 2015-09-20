package swag.swag;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Stroke {
    List<Point> points;
    float strokeWidth = .05f;

    public Stroke() {
        points = new ArrayList<>();
    }

    public Stroke(Stroke that) {
        this.points = new ArrayList<>(that.points);
    }

    public void add(Point p) {
//// seems slower:
//        if (!points.isEmpty()) {
//            final Point last = points.get(points.size() - 1);
//            if (!last.equals(p)) {
//                points.add(p);
//            }
//        } else {
//            points.add(p);
//        }
        points.add(p);
    }

    public void draw(Canvas canvas, Paint paint, float size) {
        if(points.size() == 1) {
            final Point p = points.get(0);
            p.draw(canvas, paint, size);
        } else if(points.size() > 1) {
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(strokeWidth * size);
            for(int i = 0; i<points.size()-1; i++) {
                final Point start = points.get(i);
                final Point stop = points.get(i+1);
                canvas.drawLine(
                        start.getX() * size, start.getY() * size,
                        stop.getX() * size, stop.getY() * size, paint);

            }
            paint.setColor(Color.GREEN);
            paint.setStrokeWidth(1);
            for(int i = 0; i<points.size()-1; i++) {
                final Point start = points.get(i);
                final Point stop = points.get(i+1);
                canvas.drawLine(
                        start.getX() * size, start.getY() * size,
                        stop.getX() * size, stop.getY() * size, paint);

            }

        } else {
            //ignore empty stroke
        }
    }

    public void safeDraw(Canvas canvas, Paint paint, float size) {
        final Stroke copy = new Stroke(this); //copy
        copy.draw(canvas, paint, size);
    }

    public void simplify() {
        if(points.size() <= 1) return;
        List<Point> newPoints = new ArrayList<>();
        newPoints.add(points.get(0));
        for(int i=1; i<points.size(); i++) {
            if(!points.get(i).equals(points.get(i-1))) {
                newPoints.add(points.get(i));
            }
        }
        points = newPoints;
    }

    @Override
    public String toString() {
        return points.toString();
    }
}
