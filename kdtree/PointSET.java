import java.util.TreeSet;
import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public class PointSET {
    private TreeSet<Point2D> BST;

    public PointSET() {
        // construct an empty set of points
        BST = new TreeSet<>();
    }

    public boolean isEmpty() {
        // is the set empty?
        return BST.isEmpty();
    }

    public int size() {
        // number of points in the set
        return BST.size();
    }

    public void insert(Point2D p) {
        // add the point to the set (if it is not already in the set)
        if (p == null) {
            throw new IllegalArgumentException();
        }

        BST.add(p);
    }

    public boolean contains(Point2D p) {
        // does the set contain point p?
        if (p == null) {
            throw new IllegalArgumentException();
        }

        return BST.contains(p);
    }

    public void draw() {
        // draw all points to standard draw
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);

        for (Point2D point2d : BST) {
            point2d.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        // all points that are inside the rectangle (or on the boundary)
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        LinkedQueue<Point2D> result = new LinkedQueue<>();
        for (Point2D point2d : BST) {
            if (rect.contains(point2d)) {
                result.enqueue(point2d);
            }
        }
        return result;
    }

    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null) {
            throw new IllegalArgumentException();
        }

        Point2D result = null;
        double d = 0;
        for (Point2D point2d : BST) {
            double dd = p.distanceSquaredTo(point2d);
            if (result == null || dd < d) {
                result = point2d;
                d = dd;
            }
        }
        return result;
    }

    public static void main(String[] args) { // unit testing of the methods (optional)
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.02);
        RectHV r = new RectHV(0.2, 0.2, 0.6, 0.6);
        PointSET test = new PointSET();
        for (int i = 0; i < 100; i++) {
            test.insert(new Point2D(StdRandom.uniformDouble(0, 1), StdRandom.uniformDouble(0, 1)));
        }
        test.draw();
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.setPenRadius(0.005);
        r.draw();
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.01);
        for (Point2D p : test.range(r)) {
            p.draw();
        }
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setPenRadius(0.0075);
        Point2D p = new Point2D(StdRandom.uniformDouble(0, 1), StdRandom.uniformDouble(0, 1));
        p.draw();
        test.nearest(p).draw();
    }
}