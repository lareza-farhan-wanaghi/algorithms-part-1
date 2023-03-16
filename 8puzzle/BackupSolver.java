// import java.util.HashSet;
// import java.util.Hashtable;

// import edu.princeton.cs.algs4.LinkedStack;
// import edu.princeton.cs.algs4.MinPQ;
// import edu.princeton.cs.algs4.Queue;

// public class Solver {
// private static MinPQ<ProcessedBoard> pq = new MinPQ<>();
// private Hashtable<String, Iterable<Board>> boardNeighborsCache = new
// Hashtable<>();
// private ProcessedBoard idealPB;

// private class ProcessedBoard implements Comparable<ProcessedBoard> {
// private Board board;
// private int moves;
// private HashSet<String> hashSet;
// private ProcessedBoard prevProcessedBoard;

// public ProcessedBoard(Board board, int moves, HashSet<String> prevHash,
// ProcessedBoard prevProcessedBoard) {
// this.board = board;
// this.moves = moves;
// this.hashSet = new HashSet<String>(prevHash);
// this.hashSet.add(this.board.toString());
// this.prevProcessedBoard = prevProcessedBoard;
// }

// public Board Board() {
// return board;
// }

// public int Priority() {
// return moves + board.manhattan();
// }

// public int Moves() {
// return moves;
// }

// public HashSet<String> HastSet() {
// return hashSet;
// }

// public Iterable<Board> GetBoardsStack() {
// LinkedStack<Board> result = new LinkedStack<>();
// ProcessedBoard current = this;
// while (current != null) {
// result.push(current.board);
// current = current.prevProcessedBoard;
// }
// return result;
// }

// @Override
// public int compareTo(Solver.ProcessedBoard o) {
// if (this.Priority() < o.Priority())
// return -1;
// else if (this.Priority() > o.Priority())
// return +1;
// else if (Board().manhattan() < o.Board().manhattan()) {
// return -1;

// } else if (Board().manhattan() > o.Board().manhattan()) {
// return +1;
// }
// return 0;
// }
// }

// // find a solution to the initial board (using the A* algorithm)
// public Solver(Board initial) {
// if (initial == null) {
// throw new IllegalArgumentException();
// }
// if (initial.dimension() == 3) {
// String lines[] = initial.toString().split("\\r?\\n");
// Queue<Integer> tiles = new Queue<>();
// for (int i = 1; i < lines.length; i++) {
// for (String tile : lines[i].split("\\s+")) {
// tiles.enqueue(Integer.parseInt(tile));
// }
// }

// int inv_count = 0;
// while (tiles.size() > 1) {
// int tile = tiles.dequeue();
// for (int nextTile : tiles) {
// if (tile > 0 && nextTile > 0 && tile > nextTile) {
// inv_count++;
// }
// }
// }
// if (inv_count % 2 != 0) {
// return;
// }
// }

// ProcessedBoard initialPB = new ProcessedBoard(initial, 0, new HashSet<>(),
// null);
// pq.insert(initialPB);
// ProcessedBoard currentPB = null;
// while (pq.size() > 0) {
// currentPB = pq.delMin();
// if (idealPB != null && currentPB.Moves() >= idealPB.Moves()) {
// continue;
// }
// if (currentPB.Board().manhattan() == 0) {
// if (idealPB == null || currentPB.Moves() < idealPB.Moves()) {
// idealPB = currentPB;
// }
// continue;
// }
// Iterable<Board> neighborBoards = null;
// if (boardNeighborsCache.contains(currentPB.Board().toString())) {
// neighborBoards = boardNeighborsCache.get(currentPB.Board().toString());
// } else {
// neighborBoards = currentPB.Board().neighbors();
// boardNeighborsCache.put(currentPB.Board().toString(), neighborBoards);
// }

// for (Board neighborBoard : neighborBoards) {
// if (!currentPB.HastSet().contains(neighborBoard.toString())) {
// pq.insert(new ProcessedBoard(neighborBoard, currentPB.Moves() + 1,
// currentPB.HastSet(), currentPB));
// }
// }
// }
// }

// // is the initial board solvable? (see below)
// public boolean isSolvable() {
// return idealPB != null;
// }

// // min number of moves to solve initial board; -1 if unsolvable
// public int moves() {
// return idealPB == null ? -1 : idealPB.Moves();
// }

// // sequence of boards in a shortest solution; null if unsolvable
// public Iterable<Board> solution() {
// return idealPB == null ? null : idealPB.GetBoardsStack();
// }

// // test client (see below)
// public static void main(String[] args) {

// }
// }