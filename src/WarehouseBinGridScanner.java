import java.util.Scanner;

public class WarehouseBinGridScanner {

    public static String warehouseSummary(int[][] grid) {
        int total = 0;
        int max = grid[0][0];
        int maxRow = 0;
        int maxColumn = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {

                total += grid[i][j];

                if (grid[i][j] > max) {
                    max = grid[i][j];
                    maxRow = i;
                    maxColumn = j;
                }
            }
        }

        return "total = " + total +
                ", maxCoordinate = (" + maxRow + ", " + maxColumn + ")";
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of rows: ");
        int rows = sc.nextInt();

        System.out.print("Enter number of columns: ");
        int columns = sc.nextInt();

        int[][] grid = new int[rows][columns];

        System.out.println("Enter grid values:");

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid[i][j] = sc.nextInt();
            }
        }

        System.out.println(warehouseSummary(grid));

        sc.close();
    }
}