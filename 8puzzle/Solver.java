import edu.princeton.cs.algs4.LinkedStack;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private ProcessedBoard idealPB;

    private class ProcessedBoard implements Comparable<ProcessedBoard> {
        public Board board;
        public int moves;
        public int manhattan;
        private ProcessedBoard prev;

        public ProcessedBoard(Board board, int moves, ProcessedBoard prev) {
            this.board = board;
            this.moves = moves;
            this.manhattan = board.manhattan();
            this.prev = prev;
        }

        public boolean isValid() {
            return prev == null || prev.prev == null || !prev.prev.board.equals(board);
        }

        public Iterable<Board> getBoardsStack() {
            LinkedStack<Board> result = new LinkedStack<>();
            ProcessedBoard current = this;
            while (current != null) {
                result.push(current.board);
                current = current.prev;
            }
            return result;
        }

        @Override
        public int compareTo(Solver.ProcessedBoard other) {
            if (moves + manhattan < other.moves + other.manhattan)
                return -1;
            else if (moves + manhattan > other.moves + other.manhattan)
                return +1;
            else if (manhattan < other.manhattan) {
                return -1;

            } else if (manhattan > other.manhattan) {
                return +1;
            }
            return 0;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        MinPQ<ProcessedBoard> pq = new MinPQ<>();
        pq.insert(new ProcessedBoard(initial, 0, null));
        Board twin = initial.twin();
        MinPQ<ProcessedBoard> pq2 = new MinPQ<>();
        pq2.insert(new ProcessedBoard(twin, 0, null));

        ProcessedBoard currentPB = null;
        ProcessedBoard twinPb = null;
        while (pq.size() > 0) {
            currentPB = pq.delMin();
            if (currentPB.board.isGoal()) {
                idealPB = currentPB;
                pq2 = null;
                twinPb = null;
            } else {
                for (Board neighborBoard : currentPB.board.neighbors()) {
                    ProcessedBoard newPB = new ProcessedBoard(neighborBoard, currentPB.moves + 1, currentPB);
                    if ((idealPB == null || newPB.moves + newPB.manhattan < idealPB.moves) && newPB.isValid()) {
                        pq.insert(newPB);
                    }
                }
            }

            if (pq2 == null)
                continue;

            twinPb = pq2.delMin();
            if (twinPb.board.isGoal()) {
                break;
            } else {
                for (Board neighborBoard : twinPb.board.neighbors()) {
                    ProcessedBoard newPB = new ProcessedBoard(neighborBoard, twinPb.moves + 1, twinPb);
                    if (newPB.isValid()) {
                        pq2.insert(newPB);
                    }
                }
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return idealPB != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return idealPB == null ? -1 : idealPB.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return idealPB == null ? null : idealPB.getBoardsStack();
    }

    // test client (see below)
    public static void main(String[] args) {
        int[][] bb = new int[][] { new int[] { 0, 1 }, new int[] { 2, 3 } };
        int[][] cc = new int[][] { new int[] { 1, 0 }, new int[] { 2, 3 } };
        Board c = new Board(cc);
        Board b = new Board(bb);
        StdOut.println(b.equals(c));

    }
}