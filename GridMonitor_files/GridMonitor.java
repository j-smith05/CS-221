import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/*
 * 
 * GridMonitor reads a 2D grid of doubles from a file and computes various grids:
 * Surrounding Sum Grid, Surrounding Average Grid, Delta Grid, Danger Grid.
 * It also implements the GridMonitorInterface.
 * 
 * @author Jacob Smith
 */
public class GridMonitor implements GridMonitorInterface
{
    private double[][] baseGrid;
    private double[][] surroundingSumGrid;
    private double[][] surroundingAvgGrid;
    private double[][] deltaGrid;
    private boolean[][] dangerGrid;

    /**
     * Constructs a GridMonitor by loading a grid from a file
     * and computing all intermediate and final grids.
     * 
     * @param filename the path to the input file containing the grid
     * @throws FileNotFoundException if the input file does not exist
     */
	public GridMonitor(String filename) throws FileNotFoundException 
    {
        try (Scanner sc = new Scanner(new File(filename))) 
            {
                int rows = sc.nextInt();
                int cols = sc.nextInt();
                baseGrid = new double[rows][cols];
                for (int i = 0; i < rows; i++) 
                {
                    for (int j = 0; j < cols; j++) 
                    {
                        baseGrid[i][j] = sc.nextDouble();
                    }
                }
            }

        getSurroundingSumGrid();
        getSurroundingAvgGrid();
        getDeltaGrid();
        getDangerGrid();
    
	}
        /**
        * Loads the base grid from a text file.
        * The file should have the number of rows and columns
        * followed by all grid values.
        * 
        * @param filename path to the input file
        * @throws FileNotFoundException if the file cannot be opened
        */
        @Override
        public double[][] getBaseGrid() 
        {
            
            double[][] copyBaseGrid = new double[baseGrid.length][baseGrid[0].length];
                for (int i = 0; i < baseGrid.length; i++) 
                {
                    for (int j = 0; j < baseGrid[0].length; j++) 
                    {
                        copyBaseGrid[i][j] = baseGrid[i][j];
                    }
                }
            return copyBaseGrid;
        }

        /**
        * Computes the surrounding sum grid.
        * Each element is the sum of its four neighbors.
        * Border elements use their own value as a mirror for out-of-bounds neighbors.
        */

        @Override
        public double[][] getSurroundingSumGrid() 
        {
            int rows = baseGrid.length;
            int cols = baseGrid[0].length;
            surroundingSumGrid = new double[rows][cols];
            for (int i = 0; i < rows; i++)
            {
                for (int j = 0; j < cols; j++)
                {
                    double up = (i > 0) ? baseGrid[i - 1][j] : baseGrid[i][j];
                    double down = (i < rows - 1) ? baseGrid[i + 1][j] : baseGrid[i][j];
                    double left = (j > 0) ? baseGrid[i][j - 1] : baseGrid[i][j];
                    double right = (j < cols - 1) ? baseGrid[i][j + 1] : baseGrid[i][j];
                    surroundingSumGrid[i][j] = up + down + left + right;
                }
            }    
            return surroundingSumGrid;
        }

        /**
        * Computes the surrounding average grid by dividing
        * the surrounding sum by 4.0.
        */

        @Override
        public double[][] getSurroundingAvgGrid() 
        {
            int rows = baseGrid.length;
            int cols = baseGrid[0].length;
            surroundingAvgGrid = new double[rows][cols];

            for (int i = 0; i < rows; i++) 
            {
                for (int j = 0; j < cols; j++) 
                {
                    surroundingAvgGrid[i][j] = surroundingSumGrid[i][j] / 4.0;
                }
            }
            return surroundingAvgGrid;
        }

        /**
        * Computes the delta grid.
        * Each delta value is 50% of the absolute surrounding average.
        * Delta represents the allowable deviation for danger detection.
        */
        @Override
        public double[][] getDeltaGrid() 
        {
            int rows = baseGrid.length;
            int cols = baseGrid[0].length;
            deltaGrid = new double[rows][cols];
            for (int i = 0; i < rows; i++)
            {
                for (int j = 0; j < cols; j++)
                {
                    deltaGrid[i][j] = 0.5 * Math.abs(surroundingAvgGrid[i][j]);
                }
            }
            return deltaGrid;
        }
        
        /**
         * Computes the danger grid.
         * A cell is marked as true if it is less than (average - delta)
         * or greater than (average + delta), otherwise false.
         */
        @Override
        public boolean[][] getDangerGrid() 
        {
            int rows = baseGrid.length;
            int cols = baseGrid[0].length;
            dangerGrid = new boolean[rows][cols];
            for (int i = 0; i < rows; i++)
            {
                for (int j = 0; j < cols; j++)
                {
                    double lowerBound = surroundingAvgGrid[i][j] - deltaGrid[i][j];
                    double upperBound = surroundingAvgGrid[i][j] + deltaGrid[i][j];
                    dangerGrid[i][j] = (baseGrid[i][j] < lowerBound || baseGrid[i][j] > upperBound);
                }
            }
            return dangerGrid;
        }

        @Override
        public String toString() 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Base Grid:\n");
            appendGrid(sb, baseGrid);
            sb.append("\nSurrounding Sum Grid:\n");
            appendGrid(sb, surroundingSumGrid);
            sb.append("\nSurrounding Avg Grid:\n");
            appendGrid(sb, surroundingAvgGrid);
            sb.append("\nDelta Grid:\n");
            appendGrid(sb, deltaGrid);
            sb.append("\nDanger Grid:\n");
            appendGrid(sb, dangerGrid);
            return sb.toString();
        }

        /**
        * Appends a 2D double grid to a StringBuilder.
        * @param sb the StringBuilder to append to
        * @param grid the grid to format
        */
        private void appendGrid(StringBuilder sb, double[][] grid) 
        {
            for (double[] row : grid) 
            {
                for (double val : row) 
                {
                sb.append(String.format("%8.2f ", val));
                }
                sb.append("\n");
            }
        }

        /**
        * Appends a 2D boolean grid to a StringBuilder.
        * @param sb the StringBuilder to append to
        * @param grid the boolean grid to format
        */
        private void appendGrid(StringBuilder sb, boolean[][] grid) 
        {
            for (boolean[] row : grid) 
            {
                for (boolean val : row) 
                {
                    sb.append(val ? "  true " : " false ");
                }
                sb.append("\n");
            }
        }
}