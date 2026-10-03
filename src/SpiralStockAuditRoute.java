import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SpiralStockAuditRoute {

    public static List<Integer> auditRoute(int[][] grid) {
        List<Integer> route = new ArrayList<>();

        int top = 0;
        int bottom = grid.length - 1;
        int left = 0;
        int right = grid[0].length - 1;

        while (top <= bottom && left <= right) {

            // Traverse top row
            for (int column = left; column <= right; column++) {
                route.add(grid[top][column]);
            }
            top++;

            // Traverse right column
            for (int row = top; row <= bottom; row++) {
                route.add(grid[row][right]);
            }
            right--;

            // Traverse bottom row
            if (top <= bottom) {
                for (int column = right; column >= left; column--) {
                    route.add(grid[bottom][column]);
                }
                bottom--;
            }

            // Traverse left column
            if (left <= right) {
                for (int row = bottom; row >= top; row--) {
                    route.add(grid[row][left]);
                }
                left++;
            }
        }

        return route;
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

        System.out.println("Spiral audit route = " + auditRoute(grid));

        sc.close();
    }
}
