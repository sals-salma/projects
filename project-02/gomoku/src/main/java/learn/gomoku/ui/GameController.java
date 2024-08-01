package learn.gomoku.ui;

import learn.gomoku.game.Gomoku;
import learn.gomoku.game.Result;
import learn.gomoku.game.Stone;
import learn.gomoku.players.HumanPlayer;
import learn.gomoku.players.Player;
import learn.gomoku.players.RandomPlayer;

import java.util.List;
import java.util.Scanner;


public class GameController {
    Scanner console = new Scanner(System.in);
    Gomoku game;

    public void run() {
        // note: repeat steps 1-6 in a loop until user select exit
        // 1. Display welcome message (Done)
        // 2. Set up 2 players (Done)
        // 3. Instantiate Gomoku with 2 players
        // 4. Use method in Gomoku to display randomizing
        // 5. Use method in Gomoku to display who goes first
        // 6. Play game
        setup();
    }

    private void setup() {
        do {
            System.out.println("Welcome to Gomoku");
            System.out.println("=================");

            String userInput;
            boolean exit = false;

            Player p1 = getPlayer(1);
            Player p2 = getPlayer(2);
            //Instantiate gomoku and pass in player
            game = new Gomoku(p1, p2);
            String firstPlayer = game.getCurrent().getName();
            System.out.println(firstPlayer + " goes first");

            play(game);

        } while (playAgain());

        System.out.println("Goodbye!");
    }
    private boolean playAgain() {
        System.out.println("Do you want to play again? [y/n]");
        return console.nextLine().equalsIgnoreCase("y");
    }
    // how the game goes not details
    private void play(Gomoku game) {
        // if players are tied
        Result result = null;
        // current player
        while (!game.isOver()) {
            Player currentPlayer = game.getCurrent();
            result = getPlayerMove(game, currentPlayer);
            printBoard(game.getStones());
        }
        if (result.getMessage().equals("Game ends in a draw")) {
            System.out.println("Game ends in a draw");
        } else {
            Player winner = game.getWinner();
            System.out.printf("%s wins! %n%n", winner.getName());
        }
    }
    private void printBoard(List<Stone> stones) {
        //printing top row
        // we want to leave some space
        System.out.print(" ");
        for (int col = 0; col < Gomoku.WIDTH; col++) {
            // kept messing up spacing here but now fixed
            System.out.printf(" %02d ", col + 1);
        }
        System.out.println();

        //print actual board
        for (int row = 0; row < Gomoku.WIDTH; row++) {
            System.out.printf("%02d", row + 1);
            for (int col = 0; col < Gomoku.WIDTH; col++) {
                boolean stonePlaced = false;
                for (Stone s : stones) {
                    if (s.getRow() == row && s.getColumn() == col) {
                        stonePlaced = true;
                        // enter space after B and W so that it doesn't move over board
                        System.out.printf("  %s", s.isBlack() ? "B " : "W ");
                    }
                }
                if (!stonePlaced) {
                    System.out.print("  - ");
                }
            }
            System.out.println();

        }
    }

    private Player getPlayer(int num) {
        System.out.println("Player " + num + " is: ");
        System.out.println("1. Human");
        System.out.println("2. Random");

        int userChoice = readInt("Select [1-2]: ", 1, 2);
        Player p = null;
        if (userChoice == 1) {
            System.out.println("Player " + num + " enter your name: ");
            String name = console.nextLine();
            p = new HumanPlayer(name);
        } else {
            p = new RandomPlayer();
        }
        System.out.println(p.getName());
        return p;
    }

    private String readRequired(String message) {
        System.out.println(message);
        String userInput = console.nextLine();
        while (userInput.isBlank()) {
            System.out.println(message);
            userInput = console.nextLine();
        }
        return userInput;
    }

    private int readInt(String message, int min, int max) {
        String userInput = readRequired(message);
        int num = Integer.parseInt(userInput);
        while (num < min || num > max) {
            userInput = readRequired(message);
            num = Integer.parseInt(userInput);
        }
        return num;
    }

    private Result getPlayerMove(Gomoku game, Player currentPlayer) {
        Result result = null;
        Stone stone;

        do {
            System.out.println(currentPlayer.getName() + "'s turn");
            stone = currentPlayer.generateMove(game.getStones());

            if (stone == null) {
                System.out.println("Enter a row: ");
                int row = Integer.parseInt(console.nextLine()) - 1;
                System.out.println("Enter a column: ");
                int column = Integer.parseInt(console.nextLine()) - 1;
                stone = new Stone(row, column, game.isBlacksTurn());
            }
            System.out.printf("Stone placed at row %s and column %s%n ", stone.getRow() + 1, stone.getColumn() + 1 );

            //Successful! (forgot to print out result earlier
            //so I kept getting a nullPointerException
            result = game.place(stone);

            //validating
            // same thing here; instead of { I used ; for my if condition and kept
            //getting the sout message even if my result was successful
            if (!result.isSuccess()) {
                System.out.println("[Err] " + result.getMessage());
            }
        } while (!result.isSuccess());

        return result;
    }


}


