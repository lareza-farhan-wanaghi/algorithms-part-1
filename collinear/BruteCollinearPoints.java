import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ResizingArrayBag;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private ResizingArrayBag<LineSegment> segmentList = new ResizingArrayBag<>();

    public BruteCollinearPoints(Point[] pointss) {
        if (pointss == null || pointss[0] == null) {
            throw new IllegalArgumentException();
        }
        Point[] points = pointss.clone();

        ResizingArrayBag<Double>[] lineEndpoint = new ResizingArrayBag[points.length < 4 ? 1 : points.length - 3];
        try {
            Arrays.sort(points);
            for (int i = 0; i < points.length; i++) {
                if (i < points.length - 1 && points[i].compareTo(points[i + 1]) == 0) {
                    throw new IllegalArgumentException();
                }

                if (i < lineEndpoint.length) {
                    lineEndpoint[i] = new ResizingArrayBag<>();
                }
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < points.length; i++) {
            if (points.length - i < 4) {
                break;
            }
            Point[] aux = Arrays.copyOfRange(points, i + 1, points.length);
            Arrays.sort(aux, points[i].slopeOrder());
            double latestSlope = points[i].slopeTo(aux[0]);
            int counter = 1;

            for (int j = 1; j < aux.length; j++) {
                double currentSlope = points[i].slopeTo(aux[j]);
                if (latestSlope == Double.NEGATIVE_INFINITY || currentSlope != latestSlope) {
                    latestSlope = currentSlope;
                    counter = 1;
                } else {
                    counter += 1;
                    if (counter >= 3) {
                        int endpointIndex = Arrays.binarySearch(points, aux[j]) - 3;
                        boolean isValid = true;
                        for (Double slope : lineEndpoint[endpointIndex]) {
                            if (slope == currentSlope) {
                                isValid = false;
                                break;
                            }
                        }
                        if (isValid) {
                            lineEndpoint[endpointIndex].add(currentSlope);
                            segmentList.add(new LineSegment(points[i], aux[j]));
                        }
                        latestSlope = Double.NEGATIVE_INFINITY;
                    }
                }
            }
        }
    }

    public int numberOfSegments() {// the number of line segments
        return segmentList.size();
    }

    public LineSegment[] segments() {// the line segments
        LineSegment[] result = new LineSegment[segmentList.size()];
        int i = 0;
        for (LineSegment lineSegment : segmentList) {
            result[i] = lineSegment;
            i += 1;
        }
        return result;
    }

    public static void main(String[] args) {
        // // read the n points from a file
        // In in = new In(args[0]);
        // int n = in.readInt();
        // Point[] points = new Point[n];
        // for (int i = 0; i < n; i++) {
        // int x = in.readInt();
        // int y = in.readInt();
        // points[i] = new Point(x, y);
        // }

        // // draw the points
        // StdDraw.enableDoubleBuffering();
        // StdDraw.setXscale(0, 32768);
        // StdDraw.setYscale(0, 32768);
        // for (Point p : points) {
        // p.draw();
        // }
        // StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(null);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        StdOut.print("Finish rendered");
    }
}
