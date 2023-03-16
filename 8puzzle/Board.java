import edu.princeton.cs.algs4.LinkedStack;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private int[] flattenTiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        // hash + dimension + indexOfzero + manhattan + hamming
        flattenTiles = new int[tiles.length * tiles.length + 5];
        flattenTiles[flattenTiles.length - 5] = 7;
        flattenTiles[flattenTiles.length - 4] = tiles.length;
        for (int i = 0; i < tiles.length; i++) {
            int rowIndex = i * tiles.length;
            for (int j = 0; j < tiles.length; j++) {
                flattenTiles[rowIndex + j] = tiles[i][j];
                flattenTiles[flattenTiles.length - 5] = 31 * flattenTiles[flattenTiles.length - 5] + tiles[i][j];
                if (tiles[i][j] == 0) {
                    flattenTiles[flattenTiles.length - 3] = rowIndex + j;
                } else {
                    int goalDistance = Math.abs((tiles[i][j] - 1) / tiles.length - i)
                            + Math.abs((tiles[i][j] - 1) % tiles.length - j);
                    flattenTiles[flattenTiles.length - 2] += goalDistance;
                    flattenTiles[flattenTiles.length - 1] += goalDistance > 0 ? 1 : 0;
                }
            }
        }
    }

    private void swapTiles(int index1, int index2) {
        int tmp = flattenTiles[index1];
        flattenTiles[index1] = flattenTiles[index2];
        flattenTiles[index2] = tmp;
    }

    private int[][] create2DTiles() {
        int n = dimension();
        int[][] result = new int[n][n];
        for (int i = 0; i < n; i++) {
            result[i] = new int[n];
            System.arraycopy(flattenTiles, i * n, result[i], 0, n);
        }
        return result;
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        int n = dimension();
        s.append(n);
        for (int i = 0; i < n * n; i++) {
            if (i % n == 0) {
                s.append(System.lineSeparator());
            } else {
                s.append(" ");
            }
            s.append(flattenTiles[i]);
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return flattenTiles[flattenTiles.length - 4];
    }

    // number of tiles out of place
    public int hamming() {
        return flattenTiles[flattenTiles.length - 1];
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return flattenTiles[flattenTiles.length - 2];
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object other) {
        if (other == this)
            return true;
        if (other == null)
            return false;
        if (other.getClass() != this.getClass())
            return false;
        Board that = (Board) other;
        if (dimension() != that.dimension())
            return false;

        return that.flattenTiles[that.flattenTiles.length - 5] == this.flattenTiles[that.flattenTiles.length - 5];
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        LinkedStack<Board> boardBag = new LinkedStack<Board>();
        int indexOfZero = flattenTiles[flattenTiles.length - 3];
        int n = dimension();
        for (int i = -1; i <= 1; i += 2) {
            int nextIndex = indexOfZero + i;
            if (nextIndex >= 0 && indexOfZero / n == nextIndex / n) {
                swapTiles(indexOfZero, nextIndex);
                boardBag.push(new Board(create2DTiles()));
                swapTiles(indexOfZero, nextIndex);
            }
            nextIndex = indexOfZero + i * n;
            if (nextIndex >= 0 && nextIndex < n * n) {
                swapTiles(indexOfZero, nextIndex);
                boardBag.push(new Board(create2DTiles()));
                swapTiles(indexOfZero, nextIndex);
            }
        }
        return boardBag;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int index1 = (flattenTiles[flattenTiles.length - 3] + 1) % (flattenTiles.length - 5);
        int index2 = (flattenTiles[flattenTiles.length - 3] + 2) % (flattenTiles.length - 5);
        swapTiles(index1, index2);
        Board result = new Board(create2DTiles());
        swapTiles(index1, index2);
        return result;
    }

    // un testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = new int[][] {
                new int[] { 3, 8, 1 },
                new int[] { 7, 5, 4 },
                new int[] { 6, 0, 2 }
        };
        StdOut.println("initial");
        Board a = new Board(tiles);
        StdOut.println(a.toString());
        StdOut.println(a.hamming());
        StdOut.println(a.manhattan());
        StdOut.println();

        StdOut.println("initial twin");
        Board b = a.twin();
        StdOut.println(b.toString());
        StdOut.println(b.hamming());
        StdOut.println(b.manhattan());
        StdOut.println();

        StdOut.println("initial neighbor");
        for (Board c : a.neighbors()) {
            StdOut.println(c.toString());
            StdOut.println(c.hamming());
            StdOut.println(c.manhattan());
            StdOut.println();
        }
    }

}