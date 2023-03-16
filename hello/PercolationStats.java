import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double meanResult;
    private double stddevResult;
    private double confidenceLoResult;
    private double confidenceHiResult;
    private final float CONFIDENCE_95 = 1.96f;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        double[] scores = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation mock = new Percolation(n);

            int[] rowColLeftArray = new int[n * n];
            int rowColLeftArrayCount = rowColLeftArray.length;
            for (int j = 0; j < rowColLeftArray.length; j++) {
                rowColLeftArray[j] = j;
            }

            while (!mock.percolates()) {
                int randIndex = StdRandom.uniformInt(rowColLeftArrayCount);
                mock.open(rowColLeftArray[randIndex] / n + 1, rowColLeftArray[randIndex] % n + 1);
                if (randIndex != rowColLeftArrayCount - 1) {
                    rowColLeftArray[randIndex] = rowColLeftArray[rowColLeftArrayCount - 1];
                }
                rowColLeftArrayCount -= 1;
            }
            scores[i] = (double) (rowColLeftArray.length - rowColLeftArrayCount) / (n * n);
        }
        meanResult = StdStats.mean(scores);
        stddevResult = StdStats.stddev(scores);
        confidenceLoResult = meanResult - CONFIDENCE_95 * stddevResult / squareRoot(trials);
        confidenceHiResult = meanResult + CONFIDENCE_95 * stddevResult / squareRoot(trials);
    }

    // Simple square root function
    private double squareRoot(int num) {
        double t;
        double sqrtroot = ((double) num) / 2;
        do {
            t = sqrtroot;
            sqrtroot = (t + (num / t)) / 2;
        } while ((t - sqrtroot) != 0);
        return sqrtroot;
    }

    // sample mean of percolation threshold
    public double mean() {
        return meanResult;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddevResult;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidenceLoResult;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidenceHiResult;
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats mock = new PercolationStats(
                Integer.parseInt(args[0]),
                Integer.parseInt(args[1]));
        StdOut.println(String.format("Mean = %s", mock.mean()));
        StdOut.println(String.format("Stddev = %s", mock.stddev()));
        StdOut.println(String.format("95%% confident interval = [%s, %s]", mock.confidenceLo(), mock.confidenceHi()));
    }
}
