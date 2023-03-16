
/******************************************************************************
 *  Compilation:  javac-algs4 PuzzleChecker.java
 *  Execution:    java-algs4 PuzzleChecker filename1.txt filename2.txt ...
 *  Dependencies: Board.java Solver.java
 *
 *  This program creates an initial board from each filename specified
 *  on the command line and finds the minimum number of moves to
 *  reach the goal state.
 *
 *  % java-algs4 PuzzleChecker puzzle*.txt
 *  puzzle00.txt: 0
 *  puzzle01.txt: 1
 *  puzzle02.txt: 2
 *  puzzle03.txt: 3
 *  puzzle04.txt: 4
 *  puzzle05.txt: 5
 *  puzzle06.txt: 6
 *  ...
 *  puzzle3x3-impossible: -1
 *  ...
 *  puzzle42.txt: 42
 *  puzzle43.txt: 43
 *  puzzle44.txt: 44
 *  puzzle45.txt: 45
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class PuzzleChecker {
    static String[] filenamesOf2x2s = new String[] {
            "puzzle2x2-00.txt",
            "puzzle2x2-01.txt",
            "puzzle2x2-02.txt",
            "puzzle2x2-03.txt",
            "puzzle2x2-04.txt",
            "puzzle2x2-05.txt",
            "puzzle2x2-06.txt"
    };
    static String[] filenamesOf4x4s = new String[] {
            "puzzle4x4-00.txt",
            "puzzle4x4-01.txt",
            "puzzle4x4-02.txt",
            "puzzle4x4-03.txt",
            "puzzle4x4-04.txt",
            "puzzle4x4-05.txt",
            "puzzle4x4-06.txt",
            "puzzle4x4-07.txt",
            "puzzle4x4-08.txt",
            "puzzle4x4-09.txt",
            "puzzle4x4-10.txt",
            "puzzle4x4-11.txt",
            "puzzle4x4-12.txt",
            "puzzle4x4-13.txt",
            "puzzle4x4-14.txt",
            "puzzle4x4-15.txt",
            "puzzle4x4-16.txt",
            "puzzle4x4-17.txt",
            "puzzle4x4-18.txt",
            "puzzle4x4-19.txt",
            "puzzle4x4-20.txt",
            "puzzle4x4-21.txt",
            "puzzle4x4-22.txt",
            "puzzle4x4-23.txt",
            "puzzle4x4-24.txt",
            "puzzle4x4-25.txt",
            "puzzle4x4-26.txt",
            "puzzle4x4-27.txt",
            "puzzle4x4-28.txt",
            "puzzle4x4-29.txt",
            "puzzle4x4-30.txt",
            "puzzle4x4-31.txt",
            "puzzle4x4-32.txt",
            "puzzle4x4-33.txt",
            "puzzle4x4-34.txt",
            "puzzle4x4-35.txt",
            "puzzle4x4-36.txt",
            "puzzle4x4-37.txt",
            "puzzle4x4-38.txt",
            "puzzle4x4-39.txt",
            "puzzle4x4-40.txt",
            "puzzle4x4-41.txt",
            "puzzle4x4-42.txt",
            "puzzle4x4-43.txt",
            "puzzle4x4-44.txt",
            "puzzle4x4-45.txt",
            "puzzle4x4-46.txt",
            "puzzle4x4-47.txt",
            "puzzle4x4-48.txt",
            "puzzle4x4-49.txt",
            "puzzle4x4-50.txt",
            "puzzle4x4-78.txt",
            "puzzle4x4-80.txt"
    };
    static String[] filenamesOf3x3s = new String[] {
            "puzzle3x3-00.txt",
            "puzzle3x3-01.txt",
            "puzzle3x3-02.txt",
            "puzzle3x3-03.txt",
            "puzzle3x3-04.txt",
            "puzzle3x3-05.txt",
            "puzzle3x3-06.txt",
            "puzzle3x3-07.txt",
            "puzzle3x3-08.txt",
            "puzzle3x3-09.txt",
            "puzzle3x3-10.txt",
            "puzzle3x3-11.txt",
            "puzzle3x3-12.txt",
            "puzzle3x3-13.txt",
            "puzzle3x3-14.txt",
            "puzzle3x3-15.txt",
            "puzzle3x3-16.txt",
            "puzzle3x3-17.txt",
            "puzzle3x3-18.txt",
            "puzzle3x3-19.txt",
            "puzzle3x3-20.txt",
            "puzzle3x3-21.txt",
            "puzzle3x3-22.txt",
            "puzzle3x3-23.txt",
            "puzzle3x3-24.txt",
            "puzzle3x3-25.txt",
            "puzzle3x3-26.txt",
            "puzzle3x3-27.txt",
            "puzzle3x3-28.txt",
            "puzzle3x3-29.txt",
            "puzzle3x3-30.txt",
            "puzzle3x3-31.txt"
    };

    static String[] filenamesOfCommons = new String[] {
            "puzzle00.txt",
            "puzzle01.txt",
            "puzzle02.txt",
            "puzzle03.txt",
            "puzzle04.txt",
            "puzzle05.txt",
            "puzzle06.txt",
            "puzzle07.txt",
            "puzzle08.txt",
            "puzzle09.txt",
            "puzzle10.txt",
            "puzzle11.txt",
            "puzzle12.txt",
            "puzzle13.txt",
            "puzzle14.txt",
            "puzzle15.txt",
            "puzzle16.txt",
            "puzzle17.txt",
            "puzzle18.txt",
            "puzzle19.txt",
            "puzzle20.txt",
            "puzzle21.txt",
            "puzzle22.txt",
            "puzzle23.txt",
            "puzzle24.txt",
            "puzzle25.txt",
            "puzzle26.txt",
            "puzzle27.txt",
            "puzzle28.txt",
            "puzzle29.txt",
            "puzzle30.txt",
            "puzzle31.txt",
            "puzzle32.txt",
            "puzzle33.txt",
            "puzzle34.txt",
            "puzzle35.txt",
            "puzzle36.txt",
            "puzzle37.txt",
            "puzzle38.txt",
            "puzzle39.txt",
            "puzzle40.txt",
            "puzzle41.txt",
            "puzzle42.txt",
            "puzzle43.txt",
            "puzzle44.txt",
            "puzzle45.txt",
            "puzzle46.txt",
            "puzzle47.txt",
            "puzzle48.txt",
            "puzzle49.txt",
            "puzzle50.txt"
    };
    static String[] filenamesOf2x2Unsolveable = new String[] {
            "puzzle2x2-unsolvable1.txt",
            "puzzle2x2-unsolvable2.txt",
            "puzzle2x2-unsolvable3.txt"
    };
    static String[] filenamesOf3x3Unsolveable = new String[] {
            "puzzle3x3-unsolvable.txt",
            "puzzle3x3-unsolvable1.txt",
            "puzzle3x3-unsolvable2.txt",
            "puzzle4x4-unsolvable.txt"
    };
    static String[] filenamesOf4x4Unsolveable = new String[] {
            "puzzle4x4-unsolvable.txt",
    };
    static String[][] allFilenames = new String[][] {
            filenamesOfCommons,
            filenamesOf2x2s,
            filenamesOf3x3s,
            filenamesOf4x4s,
            filenamesOf2x2Unsolveable,
            filenamesOf3x3Unsolveable,
            filenamesOf4x4Unsolveable,
    };

    public static void main(String[] args) {
        // solveFileName("puzzle17.txt");
        solve2(args);
    }

    public static void solve2(String[] args) {
        // for each command-line argument
        for (String filename : allFilenames[Integer.parseInt(args[0])]) {
            // read in the board specified in the filename
            solveFileName(filename);
        }
    }

    public static void solve1(String[] args) {
        Board initial = new Board(new int[][] {
                new int[] { 1, 2, 3, 4, 5, 6, 7, 8, },
                new int[] { 9, 10, 11, 12, 13, 14, 15, 16 },
                new int[] { 17, 18, 19, 20, 21, 22, 23, 24 },
                new int[] { 0, 25, 27, 28, 29, 30, 31, 32 },
                new int[] { 34, 26, 35, 36, 37, 38, 39, 40 },
                new int[] { 33, 41, 42, 43, 45, 46, 47, 48 },
                new int[] { 49, 50, 51, 44, 61, 53, 54, 56 },
                new int[] { 57, 58, 59, 52, 60, 62, 55, 63 }
        });

        Solver solver = new Solver(initial);
        StdOut.println("Successfully processed the board!");
        if (solver.isSolvable()) {
            for (Board solutionBoard : solver.solution()) {
                StdOut.println(solutionBoard.toString());
            }
        }
        StdOut.println(String.format("%d", solver.moves()));
    }

    public static void solveFileName(String filename) {
        In in = new In(filename);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        // if (n != 3)
        // continue;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        // solve the slider puzzle
        Board initial = new Board(tiles);
        StdOut.println("Processing the board below!");
        StdOut.println(initial.toString());
        Solver solver = new Solver(initial);
        StdOut.println("Successfully processed the board!");
        if (solver.isSolvable()) {
            for (Board solutionBoard : solver.solution()) {
                StdOut.println(solutionBoard.toString());
            }
        }
        StdOut.println(String.format("%s is %d", filename, solver.moves()));
    }
}
