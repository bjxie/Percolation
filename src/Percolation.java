                    /* *****************************************************************************
                     *  Name: Brian Xie
                     *  Date: 5/22/20
                     *  Description: Percolation
                     **************************************************************************** */

                    import edu.princeton.cs.algs4.WeightedQuickUnionUF;

                    public class Percolation {
                        private static final byte OPEN = 0x1; // hexadecimal value 1
                        private static final byte TOP = 0x2; // hexadecimal value 2
                        private static final byte BOT = 0x4; // hexadecimal value 4
                        private byte[] grid; // the 1D-grid of bytes where we store our tiles

                        private final int gridSize; // length of one side of the grid
                        private final WeightedQuickUnionUF wquf;
                        private int numberOpen; // number of sites that are opened
                        private boolean isPercolated = false;


                        // creates n-by-n grid, with all sites initially blocked
                        // initializes a 1D array of bytes where values are stored
                        public Percolation(int n) {
                            if (n <= 0) {
                                throw new IllegalArgumentException("grid size must be at least 1");
                            }
                            gridSize = n;
                            grid = new byte[n * n];
                            wquf = new WeightedQuickUnionUF(n * n);
                            numberOpen = 0;

                        }

                        // converts 2D coordinates into 1D integer
                        private int xyTo1D(int row, int col) {
                            checkIndices(row, col);
                            return (row - 1) * gridSize + col - 1;

                        }

                        // checks to make sure the inputs are not outside
                        // the grid array range
                        private void checkIndices(int row, int col) {
                            if (row < 1 || row > gridSize)
                                throw new IllegalArgumentException("row index i out of bounds");
                            if (col < 1 || col > gridSize)
                                throw new IllegalArgumentException("row index i out of bounds");

                        }

                        // checks the indices
                        // opens the site (row, col) if it is not open already
                        // checks that neighbors are not beyond edge of grid
                        // unions the open site to neighboring open sites,

                        public void open(int row, int col) {
                            byte connectedTop = 0;
                            byte connectedBot = 0;
                            checkIndices(row, col);
                            if (isOpen(row, col)) {
                                return; // do nothing if tile is already open
                            }
                            numberOpen++;
                            int current = xyTo1D(row, col);

                            this.grid[current] = (byte) (this.grid[current] | OPEN); // opens a tile if it is not
                                                                                     // already open.

                            if (row == 1) {
                                connectedTop = TOP;
                            }

                            if (row == gridSize) {
                                connectedBot = BOT;
                            }

                            // connects the open tile to available tiles one row below
                            if (row < gridSize && isOpen(row + 1, col)) {
                                int next = xyTo1D(row + 1, col);
                                if ((grid[wquf.find(next)] & TOP) > 0) {
                                    connectedTop = TOP;
                                }
                                if ((grid[wquf.find(next)] & BOT) > 0) {
                                    connectedBot = BOT;
                                }
                                wquf.union(current, next); // DOWN

                            }

                            // connects the open tile to available tiles one row above
                            if (row > 1 && isOpen(row - 1, col)) {
                                int next = xyTo1D(row - 1, col);
                                if ((grid[wquf.find(next)] & TOP) > 0) {
                                    connectedTop = TOP;
                                }
                                if ((grid[wquf.find(next)] & BOT) > 0) {
                                    connectedBot = BOT;
                                }
                                wquf.union(current, next);


                            }
                            // connects the open tile to available tiles one row to the right
                            if (col < gridSize && isOpen(row, col + 1)) {
                                int next = xyTo1D(row, col + 1);
                                if ((grid[wquf.find(next)] & TOP) > 0) {
                                    connectedTop = TOP;
                                }
                                if ((grid[wquf.find(next)] & BOT) > 0) {
                                    connectedBot = BOT;
                                }
                                wquf.union(current, next); // DOWN


                            }
                            // connects the open tile to available tiles one row to the left
                            if (col > 1 && isOpen(row, col - 1)) {
                                int next = xyTo1D(row, col - 1);
                                if ((grid[wquf.find(next)] & TOP) > 0) {
                                    connectedTop = TOP;
                                }
                                if ((grid[wquf.find(next)] & BOT) > 0) {
                                    connectedBot = BOT;
                                }
                                wquf.union(current, next); // DOWN

                            }

                            int index = wquf.find(current);
                            grid[index] = (byte) (grid[index] | connectedTop);
                            grid[index] = (byte) (grid[index] | connectedBot);
                            if (((grid[index] & TOP) > 0) && ((grid[index] & BOT) > 0)) {
                                isPercolated = true; // checks if the grid percolates after opening the tile
                            }

                        }


                        // returns if the site (row, col) is open
                        public boolean isOpen(int row, int col) {
                            checkIndices(row, col);
                            return (grid[xyTo1D(row, col)] & OPEN) > 0;
                        }

                        // returns if the site (row, col) is full
                        public boolean isFull(int row, int col) {
                            checkIndices(row, col);
                            int current = xyTo1D(row, col);
                            return (grid[wquf.find(current)] & TOP) > 0;
                        }


                        // returns the number of open sites
                        public int numberOfOpenSites() {
                            return numberOpen;
                        }

                        // does the system percolate?
                        public boolean percolates() {
                            return isPercolated;
                        }

                        // main test
                        public static void main(String[] args) {
                            /* Percolation test = new Percolation(5);
                            test.open(1, 1);
                            test.open(1, 2);
                            int first = test.xyTo1D(1, 1);
                            int second = test.xyTo1D(1, 2);
                            System.out.println(test.connected(first, second)); */
                        }
                    }
