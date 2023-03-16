import edu.princeton.cs.algs4.StdOut;

public class HelloWorld {
    String a;

    public HelloWorld(String _a) {
        a = _a;
    }

    public void Print() {
        StdOut.print(a);
    }

    public static void main(String[] args) {
        StdOut.println(args[0]);
        StdOut.println(args[1]);
    }

    public static double squareRoot(int num) {
        // temporary variable
        double t;
        double sqrtroot = num / 2;
        do {
            t = sqrtroot;
            sqrtroot = (t + (num / t)) / 2;
        } while ((t - sqrtroot) != 0);
        return sqrtroot;
    }

}
