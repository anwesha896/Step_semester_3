import java.util.Random;
import java.util.Scanner;

public class RockPaperScissors
{
    // Method to determine the winner
    static String playRound(String playerMove, String computerMove)
    {
        if (playerMove.equals(computerMove))
        {
            return "Draw";
        }
        else if ((playerMove.equals("Rock") && computerMove.equals("Scissors")) ||
                (playerMove.equals("Paper") && computerMove.equals("Rock")) ||
                (playerMove.equals("Scissors") && computerMove.equals("Paper")))
        {
            return "Player Wins";
        }
        else
        {
            return "Computer Wins";
        }
    }

    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        String[] moves = {"Rock", "Paper", "Scissors"};

        int wins = 0;
        int losses = 0;
        int draws = 0;

        String[] playerMoves = new String[5];
        String[] computerMoves = new String[5];
        String[] results = new String[5];

        // Play 5 rounds
        for (int i = 0; i < 5; i++)
        {
            System.out.print("Round " + (i + 1) + " - Enter your move (Rock/Paper/Scissors): ");
            String playerMove = sc.nextLine();

            int randomIndex = random.nextInt(3);
            String computerMove = moves[randomIndex];

            String result = playRound(playerMove, computerMove);

            playerMoves[i] = playerMove;
            computerMoves[i] = computerMove;
            results[i] = result;

            System.out.println("Computer Move: " + computerMove);
            System.out.println("Result: " + result);
            System.out.println();

            if (result.equals("Player Wins"))
            {
                wins++;
            }
            else if (result.equals("Computer Wins"))
            {
                losses++;
            }
            else
            {
                draws++;
            }
        }

        // Final Summary
        System.out.println("===== FINAL SUMMARY =====");
        System.out.println("Round\tPlayer Move\tComputer Move\tResult");

        for (int i = 0; i < 5; i++)
        {
            System.out.println((i + 1) + "\t" + playerMoves[i] + "\t\t"
                    + computerMoves[i] + "\t\t" + results[i]);
        }

        double winPercentage = (wins / 5.0) * 100;

        System.out.println();
        System.out.println("Wins: " + wins);
        System.out.println("Losses: " + losses);
        System.out.println("Draws: " + draws);
        System.out.println("Win Percentage: " + winPercentage + "%");

        sc.close();
    }
}