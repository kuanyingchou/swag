package swag.swag;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

class Stroke {
    private List<Point> points;

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

    public void draw(Canvas canvas, Paint paint) {
        if(points.size() == 1) {
            final Point p = points.get(0);
            p.draw(canvas, paint);
        } else if(points.size() > 1) {
            for(int i = 0; i<points.size()-1; i++) {
                final Point start = points.get(i);
                final Point stop = points.get(i+1);
                canvas.drawLine(start.getX(), start.getY(), stop.getX(), stop.getY(), paint);
            }
        } else {
            //ignore empty stroke
        }
    }

    public void safeDraw(Canvas canvas, Paint paint) {
        final Stroke copy = new Stroke(this); //copy
        copy.draw(canvas, paint);
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
