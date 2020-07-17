    /* *****************************************************************************
     *  Name: Brian Xie
     *  Date: 5/22/20
     *  Description: PercolationStats
     **************************************************************************** */

    import edu.princeton.cs.algs4.StdRandom;
    import edu.princeton.cs.algs4.StdStats;

    public class PercolationStats {
        private static final double CONFICONST = 1.96;
        private final double[] fracOpen;
        private final int numTrials;
        private double resMean = 0;
        private double resStddev = 0;


        // create a new Percolation of size n x n for each int trials
        // Open randomly generated tiles and check if the system Percolates
        // Add the resulting Percolation threshold to array fracOpen[]
        public PercolationStats(int n, int trials) {
            if (n <= 0 || trials <= 0) {
                throw new IllegalArgumentException();
            }

            numTrials = trials;
            fracOpen = new double[trials];
            for (int i = 0; i < trials; i++) {
                Percolation perc = new Percolation(n);
                while (!perc.percolates()) {
                    int ranRow = StdRandom.uniform(1, n + 1);
                    int ranCol = StdRandom.uniform(1, n + 1);
                    perc.open(ranRow, ranCol);
                }
                double numOpen = perc.numberOfOpenSites();
                fracOpen[i] = numOpen / (n * n);
            }
        }

        // return sample mean of percolation threshold across all fracOpen[]
        public double mean() {
            if (resMean == 0) {
                resMean = StdStats.mean(fracOpen);
            }
            return resMean;
        }


        // return sample std dev of percolation threshold across all fracOpen[]
        public double stddev() {
            if (resStddev == 0) {
                resStddev = StdStats.stddev(fracOpen);
            }
            return resStddev;

        }

        // low endpoint of 95% confidence interval
        public double confidenceLo() {
            double confidenceLo = mean() - (CONFICONST * stddev() / Math.sqrt(numTrials));
            return confidenceLo;
        }

        // high endpoint of 95% confidence interval
        public double confidenceHi() {
            double confidenceHi = mean() + (CONFICONST * stddev() / Math.sqrt(numTrials));
            return confidenceHi;
        }

        // test client (see below)
        public static void main(String[] args) {
            int gridWidth = Integer.parseInt(args[0]);
            int numTrials = Integer.parseInt(args[1]);
       /*     System.out.println("num of trials:   " + numTrials);
            System.out.println("grid width:   " + gridWidth);
    */
            PercolationStats getStats = new PercolationStats(gridWidth, numTrials);

            System.out.println("mean =    " + getStats.mean());
            System.out.println("stdDev =    " + getStats.stddev());
            System.out.println("95% confidence interval =    " +
                    "(" + getStats.confidenceLo() + ", " +
                    getStats.confidenceHi() + ")");
        }

    }
