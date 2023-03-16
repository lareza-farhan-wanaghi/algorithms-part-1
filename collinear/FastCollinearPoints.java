import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ResizingArrayBag;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private ResizingArrayBag<LineSegment> segmentList = new ResizingArrayBag<>();

    public FastCollinearPoints(Point[] pointss) {
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
                if (currentSlope == latestSlope) {
                    counter += 1;
                    if (j + 1 == aux.length && counter >= 3) {
                        int endpointIndex = Arrays.binarySearch(points, aux[j], isPointEqual()) - 3;
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
                    }
                } else {
                    if (counter >= 3) {
                        int endpointIndex = Arrays.binarySearch(points, aux[j - 1]) - 3;
                        boolean isValid = true;
                        for (Double slope : lineEndpoint[endpointIndex]) {
                            if (slope == currentSlope) {
                                isValid = false;
                                break;
                            }
                        }
                        if (isValid) {
                            lineEndpoint[endpointIndex].add(latestSlope);
                            segmentList.add(new LineSegment(points[i], aux[j - 1]));
                        }
                    }
                    latestSlope = currentSlope;
                    counter = 1;
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

    private Comparator<Point> isPointEqual() {
        /* YOUR CODE HERE */
        return new CustomComparator();
    }

    private class CustomComparator implements Comparator<Point> {
        @Override
        public int compare(Point o1, Point o2) {
            return o1.compareTo(o2);
        }
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        StdOut.print("Finish rendered");
    }
}