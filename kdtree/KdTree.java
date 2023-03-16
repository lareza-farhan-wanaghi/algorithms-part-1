
import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.LinkedStack;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class KdTree {
    private Node root;
    private int n;

    public KdTree() {// construct an empty set of points
        root = null;
        n = 0;
    }

    private static class Node {
        private Point2D p[]; // the point
        private Node lb; // the left/bottom subtree
        private Node rt; // the right/top subtree
        private boolean isSplitX;

        public Node(Point2D cur, Point2D minLine, Point2D maxLine, boolean isSplitX) {
            this.p = new Point2D[] {
                    cur,
                    minLine,
                    maxLine
            };
            this.isSplitX = isSplitX;
        }

        public boolean isPointRT(Point2D p) {
            if (isSplitX) {
                return p.x() > this.p[0].x();
            } else {
                return p.y() > this.p[0].y();
            }
        }
    }

    private static class ScoreNode {
        Node node;
        double score;

        public ScoreNode(Node node, double score) {
            this.node = node;
            this.score = score;
        }
    }

    public boolean isEmpty() {
        // is the set empty?
        return root == null;
    }

    public int size() {
        // number of points in the set
        return n;
    }

    public void insert(Point2D p) {
        // add the point to the set (if it is not already in the set)
        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (root == null) {
            root = new Node(p, new Point2D(p.x(), 0), new Point2D(p.x(), 1), true);
        } else {
            Node prev = null;
            Node node = root;
            boolean isLeft = false;
            while (node != null) {
                prev = node;
                if (p.equals(node.p[0]))
                    return;

                if (node.isSplitX) {
                    if (node.isPointRT(p)) {
                        node = node.rt;
                        isLeft = false;
                    } else {
                        node = node.lb;
                        isLeft = true;
                    }
                } else {
                    if (node.isPointRT(p)) {
                        node = node.rt;
                        isLeft = false;
                    } else {
                        node = node.lb;
                        isLeft = true;
                    }
                }
            }

            if (prev.isSplitX) {
                if (isLeft) {
                    prev.lb = new Node(p, new Point2D(0, p.y()), new Point2D(prev.p[0].x(), p.y()), !prev.isSplitX);
                } else {
                    prev.rt = new Node(p, new Point2D(prev.p[0].x(), p.y()), new Point2D(1, p.y()), !prev.isSplitX);
                }
            } else {
                if (isLeft) {
                    prev.lb = new Node(p, new Point2D(p.x(), 0), new Point2D(p.x(), prev.p[0].y()), !prev.isSplitX);
                } else {
                    prev.rt = new Node(p, new Point2D(p.x(), prev.p[0].y()), new Point2D(p.x(), 1), !prev.isSplitX);
                }
            }
        }
        n += 1;
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        Node node = root;
        while (node != null) {
            if (p.equals(node.p[0]))
                return true;

            if (node.isPointRT(p)) {
                node = node.rt;
            } else {
                node = node.lb;
            }
        }

        return false;
    }

    public void draw() {
        // draw all points to standard draw
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);

        draw(root, true);
    }

    private void draw(Node node, boolean isCompX) {
        if (node == null)
            return;

        node.p[0].draw();

        StdDraw.setPenRadius();
        if (isCompX) {
            StdDraw.setPenColor(StdDraw.RED);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
        }
        StdDraw.line(node.p[1].x(), node.p[1].y(), node.p[2].x(), node.p[2].y());
        draw(node.lb, !isCompX);
        draw(node.rt, !isCompX);
    }

    public Iterable<Point2D> range(RectHV rect) {
        // all points that are insidethe rectangle (or on the boundary)
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        LinkedQueue<Point2D> result = new LinkedQueue<>();
        range(root, rect, result);
        return result;
    }

    private void range(Node node, RectHV rect, LinkedQueue<Point2D> iter) {
        if (node == null)
            return;

        if (rect.contains(node.p[0])) {
            iter.enqueue(node.p[0]);
        }

        boolean isIntersactingLine = node.isSplitX ? rect.xmin() <= node.p[0].x() && rect.xmax() >= node.p[0].x()
                && rect.ymin() <= node.p[2].y() && rect.ymax() >= node.p[1].y()
                : rect.ymin() <= node.p[0].y() && rect.ymax() >= node.p[0].y()
                        && rect.xmin() <= node.p[2].x() && rect.xmax() >= node.p[1].x();

        if (isIntersactingLine) {
            range(node.rt, rect, iter);
            range(node.lb, rect, iter);
        } else {
            boolean isGoRight = node.isSplitX ? rect.xmax() > node.p[0].x() : rect.ymax() > node.p[0].y();
            if (isGoRight) {
                range(node.rt, rect, iter);
            } else {
                range(node.lb, rect, iter);
            }
        }

    }

    public Point2D nearest(Point2D p) { // a nearest neighbor in the set to pointp; null if the set is empty
        if (p == null) {
            throw new IllegalArgumentException();
        }

        LinkedStack<ScoreNode> dfs = new LinkedStack<>();
        dfs.push(new ScoreNode(root, 0));

        Node nearest = null;
        ScoreNode cur = null;
        double closestD = Double.POSITIVE_INFINITY;
        while (!dfs.isEmpty()) {
            cur = dfs.pop();
            if (cur.node == null || cur.score > closestD)
                continue;
            double d = p.distanceSquaredTo(cur.node.p[0]);
            if (d < closestD) {
                nearest = cur.node;
                closestD = d;
            }
            Point2D idealPoint = cur.node.isSplitX
                    ? new Point2D(cur.node.p[0].x(),
                            Math.max(cur.node.p[1].y(), Math.min(cur.node.p[2].y(), p.y())))
                    : new Point2D(
                            Math.max(cur.node.p[1].x(), Math.min(cur.node.p[2].x(), p.x())),
                            cur.node.p[0].y());
            double minScore = p.distanceSquaredTo(idealPoint);
            if (cur.node.isPointRT(p)) {
                dfs.push(new ScoreNode(cur.node.lb, minScore));
                dfs.push(new ScoreNode(cur.node.rt, 0));
            } else {
                dfs.push(new ScoreNode(cur.node.rt, minScore));
                dfs.push(new ScoreNode(cur.node.lb, 0));
            }
        }
        return nearest != null ? nearest.p[0] : null;
    }

    public static void main(String[] args) { // unit testing of the methods (optional) }
        // Thread t = new Thread() {
        // @Override
        // public void run() {
        // try {
        // for (int i = 0; i < 5; i++) {
        // test();
        // sleep(2000);
        // }

        // } catch (InterruptedException ex) {
        // StdOut.println("interrupted");
        // }
        // }
        // };
        // t.start();
        test2();
    }

    private static void test2() {
        KdTree test = new KdTree();
        test.nearest(new Point2D(0, 0));
    }

    private static void test() {
        StdDraw.clear();

        KdTree test = new KdTree();
        Point2D a = new Point2D(0.555500, 0.555500);
        Point2D b = new Point2D(0.5554, 0.5555);
        Point2D c = new Point2D(0.5, 0.5);
        RectHV r = new RectHV(0.3, 0.3, 0.7, 0.7);

        // insert
        for (int i = 0; i < 50; i++) {
            test.insert(new Point2D(StdRandom.uniformDouble(0, 1),
                    StdRandom.uniformDouble(0, 1)));
        }
        test.insert(a);
        test.insert(a);
        for (int i = 0; i < 50; i++) {
            test.insert(new Point2D(StdRandom.uniformDouble(0, 1),
                    StdRandom.uniformDouble(0, 1)));
        }
        StdOut.println(test.size());

        // contains
        test.draw();
        // StdOut.println(test.contains(a));
        StdOut.println(test.contains(b));

        StdDraw.setPenColor(StdDraw.YELLOW);
        StdDraw.setPenRadius(0.01);
        // range
        for (Point2D p : test.range(r)) {
            p.draw();
        }
        r.draw();

        // nearest
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.setPenRadius(0.01);
        c.draw();

        StdDraw.setPenRadius(0.005);
        Point2D d = test.nearest(c);
        d.draw();
    }
}
