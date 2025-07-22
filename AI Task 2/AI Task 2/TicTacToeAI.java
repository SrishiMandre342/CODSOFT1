import java.util.Scanner;

public class TicTacToeAI {
    static char[] board = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Tic-Tac-Toe!");
        System.out.println("You are X, AI is O.");
        printBoard();

        while (true) {
            playerMove();
            printBoard();
            if (isWinner('X')) {
                System.out.println("Congratulations! You win!");
                break;
            }
            if (isDraw()) {
                System.out.println("It's a draw!");
                break;
            }

            System.out.println("AI is making a move...");
            aiMove();
            printBoard();
            if (isWinner('O')) {
                System.out.println("AI wins! Better luck next time.");
                break;
            }
            if (isDraw()) {
                System.out.println("It's a draw!");
                break;
            }
        }
    }

    static void printBoard() {
        System.out.println();
        System.out.println(" " + board[0] + " | " + board[1] + " | " + board[2]);
        System.out.println("---+---+---");
        System.out.println(" " + board[3] + " | " + board[4] + " | " + board[5]);
        System.out.println("---+---+---");
        System.out.println(" " + board[6] + " | " + board[7] + " | " + board[8]);
        System.out.println();
    }

    static void playerMove() {
        int move;
        while (true) {
            System.out.print("Enter your move (1-9): ");
            move = scanner.nextInt() - 1;
            if (move >= 0 && move < 9 && board[move] == ' ') {
                board[move] = 'X';
                break;
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }
    }

    static void aiMove() {
        int bestScore = Integer.MIN_VALUE;
        int move = 0;

        for (int i = 0; i < 9; i++) {
            if (board[i] == ' ') {
                board[i] = 'O';
                int score = minimax(board, 0, false);
                board[i] = ' ';
                if (score > bestScore) {
                    bestScore = score;
                    move = i;
                }
            }
        }
        board[move] = 'O';
    }

    static int minimax(char[] board, int depth, boolean isMaximizing) {
        if (isWinner('O')) return 1;
        if (isWinner('X')) return -1;
        if (isDraw()) return 0;

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {
                if (board[i] == ' ') {
                    board[i] = 'O';
                    int score = minimax(board, depth + 1, false);
                    board[i] = ' ';
                    bestScore = Math.max(score, bestScore);
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 9; i++) {
                if (board[i] == ' ') {
                    board[i] = 'X';
                    int score = minimax(board, depth + 1, true);
                    board[i] = ' ';
                    bestScore = Math.min(score, bestScore);
                }
            }
            return bestScore;
        }
    }

    static boolean isWinner(char player) {
        int[][] winConditions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // columns
            {0, 4, 8}, {2, 4, 6}             // diagonals
        };
        for (int[] condition : winConditions) {
            if (board[condition[0]] == player &&
                board[condition[1]] == player &&
                board[condition[2]] == player) {
                return true;
            }
        }
        return false;
    }

    static boolean isDraw() {
        for (char c : board) {
            if (c == ' ') return false;
        }
        return true;
    }
}