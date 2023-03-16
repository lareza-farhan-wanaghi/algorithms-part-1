
// import edu.princeton.cs.algs4.Bag;
// import edu.princeton.cs.algs4.StdOut;
// import edu.princeton.cs.algs4.StdRandom;

// public class Board {
// private int[][] tiles;

// // create a board from an n-by-n array of tiles,
// // where tiles[row][col] = tile at (row, col)
// public Board(int[][] tiles) {
// this.tiles = tiles;
// }

// private int indexOfFlattenZero() {
// for (int i = 0; i < dimension(); i++) {
// for (int j = 0; j < dimension(); j++) {
// if (tiles[i][j] == 0) {
// return i + dimension() + j;
// }
// }
// }
// return -1;
// }

// private void swapTiles(int index1, int index2) {
// int x1 = index1 / dimension();
// int y1 = index1 % dimension();
// int x2 = index2 / dimension();
// int y2 = index2 % dimension();

// int tmp = tiles[x1][y1];
// tiles[x1][y1] = tiles[x2][y2];
// tiles[x2][y2] = tmp;
// }

// // string representation of this board
// public String toString() {
// StringBuilder s = new StringBuilder();
// s.append(dimension() + "\n");
// for (int i = 0; i < dimension(); i++) {
// for (int j = 0; j < dimension(); j++) {
// s.append(String.format("%2d ", tiles[i][j]));
// }
// s.append("\n");
// }
// return s.toString();
// }

// // board dimension n
// public int dimension() {
// return (int) Math.sqrt(tiles.length);
// }

// // number of tiles out of place
// public int hamming() {
// int result = 0;
// for (int i = 0; i < dimension(); i++) {
// for (int j = 0; j < dimension(); j++) {
// if (i != dimension() - 1 || j != dimension() - 1) {
// // import java.util.Arrays;

// // import edu.princeton.cs.algs4.Bag;
// // import edu.princeton.cs.algs4.StdOut;
// // import edu.princeton.cs.algs4.StdRandom;

// // public class Board {
// // private char[] tiles;
// // private int indexOfZero;
// // private int hamming;
// // private int manhattan;
// // private String computedString;

// // // create a board from an n-by-n array of tiles,
// // // where tiles[row][col] = tile at (row, col)
// // public Board(int[][] tiles) {
// // this.tiles = new char[tiles.length * tiles.length];

// // StringBuilder stringBuilder = new StringBuilder();
// // stringBuilder.append(dimension());
// // for (int i = 0; i < dimension() * dimension(); i++) {
// // int tileInt = tiles[i / dimension()][i % dimension()];
// // this.tiles[i] = (char) tileInt;
// // if (tileInt == 0) {
// // this.indexOfZero = i;
// // } else {
// // int goalDistance = Math.abs((tileInt - 1) / dimension() - i / dimension())
// // + Math.abs((tileInt - 1) % dimension() - i % dimension());
// // this.manhattan += goalDistance;
// // this.hamming += goalDistance > 0 ? 1 : 0;
// // }
// // if (i % dimension() == 0) {
// // stringBuilder.append(System.lineSeparator());
// // } else if (i % dimension() > 0) {
// // stringBuilder.append(" ");
// // }
// // stringBuilder.append(tileInt);
// // }
// // computedString = stringBuilder.toString();
// // }

// // private void swapTiles(int index1, int index2) {
// // char tmp = tiles[index1];
// // tiles[index1] = tiles[index2];
// // tiles[index2] = tmp;
// // }

// // private int[][] getTilesInts() {
// // int[][] result = new int[dimension()][dimension()];
// // for (int i = 0; i < dimension() * dimension(); i++) {
// // result[i / dimension()][i % dimension()] = tiles[i];
// // }
// // return result;
// // }

// // // string representation of this board
// // public String toString() {
// // return computedString;
// // }

// // // board dimension n
// // public int dimension() {
// // return (int) Math.sqrt(tiles.length);
// // }

// // // number of tiles out of place
// // public int hamming() {
// // return this.hamming;
// // }

// // // sum of Manhattan distances between tiles and goal
// // public int manhattan() {
// // return this.manhattan;
// // }

// // // is this board the goal board?
// // public boolean isGoal() {
// // return this.hamming == 0;
// // }

// // // does this board equal y?
// // public boolean equals(Object y) {
// // int[][] z = (int[][]) y;
// // boolean result = true;
// // if (z.length != dimension()) {
// // result = false;
// // } else {
// // for (int i = 0; i < dimension() * dimension(); i++) {
// // if (this.tiles[i] != (char) z[i / dimension()][i % dimension()]) {
// // result = false;
// // break;
// // }
// // }
// // }
// // return result;
// // }

// // // all neighboring boards
// // public Iterable<Board> neighbors() {
// // Bag<Board> boardBag = new Bag<Board>();
// // for (int i = -1; i <= 1; i += 2) {
// // int nextIndex = indexOfZero + i;
// // if (nextIndex >= 0 && nextIndex < dimension() * dimension()) {
// // swapTiles(indexOfZero, nextIndex);
// // boardBag.add(new Board(getTilesInts()));
// // swapTiles(indexOfZero, nextIndex);
// // }
// // nextIndex = indexOfZero + i * dimension();
// // if (nextIndex >= 0 && nextIndex < dimension() * dimension()) {
// // swapTiles(indexOfZero, nextIndex);
// // boardBag.add(new Board(getTilesInts()));
// // swapTiles(indexOfZero, nextIndex);
// // }
// // }
// // return boardBag;
// // }

// // // a board that is obtained by exchanging any pair of tiles
// // public Board twin() {
// // int randomInt = StdRandom.uniformInt(dimension() * dimension());
// // if (randomInt == indexOfZero) {
// // randomInt = (randomInt + 1) % (dimension() * dimension());
// // }
// // int secondRandomInt = StdRandom.uniformInt(dimension() * dimension());
// // while (secondRandomInt == randomInt || secondRandomInt == indexOfZero) {
// // secondRandomInt = (secondRandomInt + 1) % (dimension() * dimension());
// // }

// // swapTiles(randomInt, secondRandomInt);
// // Board result = new Board(getTilesInts());
// // swapTiles(randomInt, secondRandomInt);
// // return result;
// // }

// // // un testing (not graded)
// // public static void main(String[] args) {
// // int[][] tiles = new int[][] {
// // new int[] { 8, 1, 0 },
// // new int[] { 4, 3, 2 },
// // new int[] { 7, 6, 5 }
// // };
// // StdOut.println("initial");
// // Board a = new Board(tiles);
// // StdOut.println(a.toString());
// // StdOut.println(a.hamming());
// // StdOut.println(a.manhattan());
// // StdOut.println();

// // StdOut.println("initial twin");
// // Board b = a.twin();
// // StdOut.println(b.toString());
// // StdOut.println(b.hamming());
// // StdOut.println(b.manhattan());
// // StdOut.println();

// // StdOut.println("initial neighbor");
// // for (Board c : a.neighbors()) {
// // StdOut.println(c.toString());
// // StdOut.println(c.hamming());
// // StdOut.println(c.manhattan());
// // StdOut.println();
// // }
// // }

// // } result += i * dimension() + j;
// }
// }
// }
// return result;
// }

// // sum of Manhattan distances between tiles and goal
// public int manhattan() {
// int result = 0;
// for (int i = 0; i < dimension(); i++) {
// for (int j = 0; j < dimension(); j++) {
// if (tiles[i][j] == 0)
// continue;
// result += Math.abs((tiles[i][j] - 1) / dimension() - i / dimension())
// + Math.abs((tiles[i][j] - 1) % dimension() - j % dimension());
// }
// }

// return result;
// }

// // is this board the goal board?
// public boolean isGoal() {
// return this.hamming() == 0;
// }

// // does this board equal y?
// public boolean equals(Object other) {
// if (other == this)
// return true;
// if (other == null)
// return false;
// if (other.getClass() != this.getClass())
// return false;
// Board that = (Board) other;
// if (dimension() != that.dimension())
// return false;

// swapTiles(twinIndeces[0], twinIndeces[1]);
// return (that.twin().deepe);
// }

// // all neighboring boards
// public Iterable<Board> neighbors() {
// Bag<Board> boardBag = new Bag<Board>();
// int flattenZeroIndex = indexOfFlattenZero();
// for (int i = -1; i <= 1; i += 2) {
// int nextIndex = flattenZeroIndex + i;
// if (nextIndex >= 0 && flattenZeroIndex / dimension() == nextIndex /
// dimension()) {
// swapTiles(flattenZeroIndex, nextIndex);
// boardBag.add(new Board(cloneTiles()));
// swapTiles(flattenZeroIndex, nextIndex);
// }
// nextIndex = flattenZeroIndex + i * dimension();
// if (nextIndex >= 0 && nextIndex < dimension() * dimension()) {
// swapTiles(flattenZeroIndex, nextIndex);
// boardBag.add(new Board(getTilesInts()));
// swapTiles(flattenZeroIndex, nextIndex);
// }
// }

// int[][] result = new int[dimension()][dimension()];
// int[] k = null;
// int[] l = null;
// for (int i = 0; i < dimension(); i++) {
// for (int j = 0; j < dimension(); j++) {
// result[i][j] = tiles[i][j];
// if (result[i][j] != 0) {
// if (k == null) {
// k = new int[] { i, j };
// } else {
// l = new int[] { i, j };
// }
// }
// }
// }
// result[k[0]][k[1]] = tiles[k[0]][k[1]];
// result[l[0]][l[1]] = tiles[l[0]][l[1]];
// return boardBag;
// }

// // a board that is obtained by exchanging any pair of tiles
// public Board twin() {
// int[][] result = new int[dimension()][dimension()];
// int[] k = null;
// int[] l = null;
// for (int i = 0; i < dimension(); i++) {
// for (int j = 0; j < dimension(); j++) {
// result[i][j] = tiles[i][j];
// if (result[i][j] != 0) {
// if (k == null) {
// k = new int[] { i, j };
// } else {
// l = new int[] { i, j };
// }
// }
// }
// }
// result[k[0]][k[1]] = tiles[k[0]][k[1]];
// result[l[0]][l[1]] = tiles[l[0]][l[1]];
// return new Board(result);
// }

// // un testing (not graded)
// public static void main(String[] args) {
// int[][] tiles = new int[][] {
// new int[] { 8, 1, 0 },
// new int[] { 4, 3, 2 },
// new int[] { 7, 6, 5 }
// };
// StdOut.println("initial");
// Board a = new Board(tiles);
// StdOut.println(a.toString());
// StdOut.println(a.hamming());
// StdOut.println(a.manhattan());
// StdOut.println();

// StdOut.println("initial twin");
// Board b = a.twin();
// StdOut.println(b.toString());
// StdOut.println(b.hamming());
// StdOut.println(b.manhattan());
// StdOut.println();

// StdOut.println("initial neighbor");
// for (Board c : a.neighbors()) {
// StdOut.println(c.toString());
// StdOut.println(c.hamming());
// StdOut.println(c.manhattan());
// StdOut.println();
// }
// }

// }
