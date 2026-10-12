import java.util.Scanner;

public class StudentMarksGrid {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of students: ");
        int n = sc.nextInt();

        System.out.print("Enter number of subjects: ");
        int m = sc.nextInt();

        String[] subjects = new String[m];
        String[] students = new String[n];
        int[][] marks = new int[n][m];

        System.out.println("Enter subject names:");
        for (int j = 0; j < m; j++) {
            subjects[j] = sc.next();
        }

        for (int i = 0; i < n; i++) {
            System.out.print("Enter student name: ");
            students[i] = sc.next();

            System.out.println("Enter marks (-1 if missing):");
            for (int j = 0; j < m; j++) {
                marks[i][j] = sc.nextInt();
            }
        }

        System.out.print("Totals ");
        for (int i = 0; i < n; i++) {
            int total = 0;

            for (int j = 0; j < m; j++) {
                if (marks[i][j] != -1) {
                    total += marks[i][j];
                }
            }

            System.out.print(students[i] + " " + total);
            if (i < n - 1) {
                System.out.print(", ");
            }
        }

        System.out.println();
        System.out.print("Toppers ");

        for (int j = 0; j < m; j++) {
            int highest = -1;
            int topperIndex = -1;

            for (int i = 0; i < n; i++) {
                if (marks[i][j] > highest) {
                    highest = marks[i][j];
                    topperIndex = i;
                }
            }

            if (topperIndex != -1) {
                System.out.print(subjects[j] + " "
                        + students[topperIndex] + " " + highest);
            } else {
                System.out.print(subjects[j] + " No marks available");
            }

            if (j < m - 1) {
                System.out.print(", ");
            }
        }

        System.out.println();
        sc.close();
    }
}