
import java.util.Scanner;

public class FireDrillExits {

    static int[][] exitDistance(char[][] plan) {

        int rows = plan.length;
        int cols = plan[0].length;

        int[][] dist = new int[rows][cols];

        // Initialize all distances to -1
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                dist[i][j] = -1;
            }
        }

        // Queue to store row and column positions
        int[] qr = new int[rows * cols];
        int[] qc = new int[rows * cols];

        int front = 0;
        int rear = 0;

        // Insert all exits into the queue
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                if (plan[i][j] == 'E') {

                    dist[i][j] = 0;

                    qr[rear] = i;
                    qc[rear] = j;
                    rear++;
                }
            }
        }

        // Four possible directions
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        // BFS
        while (front < rear) {

            int r = qr[front];
            int c = qc[front];
            front++;

            // Check four neighbours
            for (int k = 0; k < 4; k++) {

                int nr = r + dr[k];
                int nc = c + dc[k];

                // Check boundaries and walls
                if (nr >= 0 && nr < rows &&
                        nc >= 0 && nc < cols &&
                        plan[nr][nc] != '#' &&
                        dist[nr][nc] == -1) {

                    dist[nr][nc] = dist[r][c] + 1;

                    qr[rear] = nr;
                    qc[rear] = nc;
                    rear++;
                }
            }
        }

        return dist;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter rows and columns:");
        int rows = sc.nextInt();
        int cols = sc.nextInt();

        char[][] plan = new char[rows][cols];

        System.out.println("Enter the grid:");

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                plan[i][j] = sc.next().charAt(0);
            }
        }

        int[][] result = exitDistance(plan);

        System.out.println("Minimum exit distances:");

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(result[i][j] + " ");
            }

            System.out.println();
        }

        sc.close();
    }
}
