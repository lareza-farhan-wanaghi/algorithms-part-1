import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private WeightedQuickUnionUF siteUF;
  private byte[] openSiteArray;
  private int openSiteCount = 0;
  private boolean isPercolates = false;
  private int rowSize;

  // creates n-by-n grid, with all sites initially blocked
  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException();
    }
    siteUF = new WeightedQuickUnionUF(n * n);
    openSiteArray = new byte[n * n];
    openSiteCount = 0;
    isPercolates = false;
    rowSize = n;
  }

  // check if index is within row/col size
  private boolean isValidRowColIndex(int row, int col) {
    return row > 0 && row <= rowSize
        && col > 0 && col <= rowSize;
  }

  // turn row,column index to flatten index
  private int getFlattenIndex(int row, int col) {
    return rowSize * (row - 1) + (col - 1);
  }

  // Union neighbot and update state
  private void unionNeighbor(int selfRow, int selfCol, int neighborRow, int neighborCol) {
    if (!isValidRowColIndex(neighborRow, neighborCol)
        || !isOpen(neighborRow, neighborCol))
      return;

    int parentAIndex = siteUF.find(getFlattenIndex(selfRow, selfCol));
    int parentBIndex = siteUF.find(getFlattenIndex(neighborRow, neighborCol));
    siteUF.union(parentAIndex, parentBIndex);

    int parentABIndex = siteUF.find(getFlattenIndex(selfRow, selfCol));
    openSiteArray[parentABIndex] = (byte) (1
        + (openSiteArray[parentAIndex] % 2 == 0 || openSiteArray[parentBIndex] % 2 == 0 ? 1 : 0)
        + (openSiteArray[parentAIndex] > 2 || openSiteArray[parentBIndex] > 2 ? 2 : 0));
    if (!isPercolates && openSiteArray[parentABIndex] == 4) {
      isPercolates = true;
    }

  }

  // opens the site (row, col) if it is not open already
  public void open(int row, int col) {
    if (!isValidRowColIndex(row, col)) {
      throw new IllegalArgumentException();
    }

    if (!isOpen(row, col)) {
      openSiteArray[getFlattenIndex(row, col)] = (byte) (1
          + (row == 1 ? 1 : 0)
          + (row == rowSize ? 2 : 0));
      if (!isPercolates && openSiteArray[getFlattenIndex(row, col)] == 4) {
        isPercolates = true;
      }
      unionNeighbor(row, col, row - 1, col);
      unionNeighbor(row, col, row + 1, col);
      unionNeighbor(row, col, row, col - 1);
      unionNeighbor(row, col, row, col + 1);

      openSiteCount += 1;
    }
  }

  // is the site (row, col) open?
  public boolean isOpen(int row, int col) {
    if (!isValidRowColIndex(row, col)) {
      throw new IllegalArgumentException();
    }

    return openSiteArray[getFlattenIndex(row, col)] > 0;
  }

  // is the site (row, col) full?
  public boolean isFull(int row, int col) {
    if (!isValidRowColIndex(row, col)) {
      throw new IllegalArgumentException();
    }
    byte parentOpenSite = openSiteArray[siteUF.find(getFlattenIndex(row, col))];
    return parentOpenSite > 0 && parentOpenSite % 2 == 0;
  }

  // returns the number of open sites
  public int numberOfOpenSites() {
    return openSiteCount;
  }

  // does the system percolate?
  public boolean percolates() {
    return isPercolates;
  }

  // test client (optional)
  public static void main(String[] args) {
    // int n = 2;
    // Percolation mock = new Percolation(n);
    // int attempCount = 0;
    // int[] rowColLeftArray = new int[n * n];
    // for (int i = 0; i < rowColLeftArray.length; i++) {
    // rowColLeftArray[i] = i;
    // }

    // while (!mock.percolates()) {
    // attempCount += 1;
    // StdOut.println("attemp: " + attempCount);
    // int randIndex = StdRandom.uniformInt(rowColLeftArray.length);
    // mock.open(rowColLeftArray[randIndex] / n + 1, rowColLeftArray[randIndex] % n
    // + 1);

    // int[] newRowColLeftArray = new int[rowColLeftArray.length - 1];
    // for (int i = 0, k = 0; i < rowColLeftArray.length; i++) {
    // if (i == randIndex) {
    // continue;
    // }
    // newRowColLeftArray[k++] = rowColLeftArray[i];
    // }
    // rowColLeftArray = newRowColLeftArray;
    // }

    // StdOut.println("Success. attemp: " + mock.openSiteCount + ", total: " + (n *
    // n));
  }
}
