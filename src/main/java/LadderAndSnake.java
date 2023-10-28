import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class LadderAndSnake {
   //class attributes
	//class attributes
    private static Player[] players = new Player[2];
    private static Player firstplayer, secondplayer;
    private static boolean gameover = false;

    //constant variables: board size, number of snakes and ladders
    private final int ROWS = 10;
    private final int COLS = 10;
    private final int NUM_SNAKES = 8;
    private final int NUM_LADDERS = 9;

    //board variables
    private final Tiles[][] gameboard = new Tiles[ROWS][COLS];
    private int[][] snakes;
    private int[][] ladders;

    public LadderAndSnake(int numofPlayers) {

        // This is checking if the number of players is greater than 2. If it is, it will print out a message and set the
        // number of players to 2.
        if(numofPlayers > 2) {
            System.out.println("Initialization was attempted for " + numofPlayers + " member of players; however, this is only expected for an extended "
                    + "version of the game. Value will be set to 2.\n");

            players = new Player[2];
        }

        // This is checking if the number of players is less than 2. If it is, it will print out a message and exit the
        // program.
        else if(numofPlayers < 2) {
            System.out.print("Error: Cannot execute the game with less than 2 players! Will exit");
            System.exit(0);
        }

        //creating a new player object for each player in the players array
        int i = 1;
        for(Player p : players) {
            players[i - 1] = new Player("P" + i);
            i++;
        }

        // This is creating the board and showing the board.
        createBoard();
        showBoard();
    }

    /**
     * It returns a random number between 1 and 6.
     *
     * @return A random number between 1 and 6.
     */
    public static int flipDice() {
        return new Random().nextInt(6) + 1;
    }

    public void play() {

        // This is setting the dice value for each player in the players array.
        for(Player p : players) {
            p.setDiceValue(LadderAndSnake.flipDice());
        }

        System.out.println("\nNow deciding which player will be starting;");
        // The above code is checking if the dice values of the two players are equal. If they are equal, it will print out
        // the dice values of the two players and then attempt to break the tie. If the dice values are not equal, it will
        // print out the dice values of the two players and then break out of the loop.
        int i = 1;
        do {
            // Checking if the dice values of the two players are equal. If they are, it prints out the dice values of the
            // two players and then attempts to break the tie by flipping the dice again.
            if(players[0].getDiceValue() == players[1].getDiceValue()) {
                System.out.println(players[0] + " got a dice value of " + players[0].getDiceValue());
                System.out.println(players[1] + " got a dice value of " + players[1].getDiceValue());
                System.out.println("A tie was achieved between " + players[0] + " and " + players[1] + ". Attempting to break the tie\n");
                i++;

                for(Player p : players) {
                    p.setDiceValue(LadderAndSnake.flipDice());
                }
            }

            // Checking if the dice values of the two players are not equal. If they are not equal, it prints out the dice
            // values of the two players and breaks out of the loop.
            if(players[0].getDiceValue() != players[1].getDiceValue()) {
                System.out.println(players[0] + " got a dice value of " + players[0].getDiceValue());
                System.out.println(players[1] + " got a dice value of " + players[1].getDiceValue());
                break;
            }
        } while(players[0].getDiceValue() == players[1].getDiceValue());


        // The above code is checking to see which player has the higher dice value. If player 1 has the higher dice value,
        // then player 1 will be the first player. If player 2 has the higher dice value, then player 2 will be the first
        // player.
        if(players[0].getDiceValue() > players[1].getDiceValue()) {
            firstplayer = players[0];
            secondplayer = players[1];
            System.out.println("Reached final decision on order of playing: " + players[0] + " then " + players[1] + ". It took " + i + " attempts before a decision could be made.\n");
        }


        else if(players[0].getDiceValue() < players[1].getDiceValue()) {
            firstplayer = players[1];
            secondplayer = players[0];
            System.out.println("Reached final decision on order of playing: " + players[1] + " then " + players[0] + ". It took " + i + " attempts before a decision could be made.\n");
        }


        // The Bellow code is a while loop that is checking if the game is over. If the game is not over, it will flip the
        // dice for the first player and move the player on the board. It will then check if the player has landed on a
        // ladder or snake. If the player has landed on the same square as the second player, the second player will be
        // moved back to square 0. The second player will then flip the dice and move on the board. It will then check if
        // the second player has landed on a ladder or snake. If the second player has landed on the same square it will move the first player back to square 0.
        while(!gameover) {
            firstplayer.setDiceValue(LadderAndSnake.flipDice());
            movePlayerOnBoard(firstplayer, firstplayer.getDiceValue());
            System.out.println(firstplayer + " got a dice value of " + firstplayer.getDiceValue() + "; now in square " + firstplayer.getPosition());
            checkLaddersandSnakes();

            if(firstplayer.getPosition() == secondplayer.getPosition()) {
                secondplayer.setPosition(0);
                System.out.println(firstplayer + " has caught up to " + secondplayer + " at square " + firstplayer.getPosition());
                System.out.println(secondplayer + " has been moved to square " + secondplayer.getPosition());
            }

            secondplayer.setDiceValue(LadderAndSnake.flipDice());
            movePlayerOnBoard(secondplayer, secondplayer.getDiceValue());
            System.out.println(secondplayer + " got a dice value of " + secondplayer.getDiceValue() + "; now in square " + secondplayer.getPosition());
            checkLaddersandSnakes();

            if(secondplayer.getPosition() == firstplayer.getPosition()) {
                firstplayer.setPosition(0);
                System.out.println(secondplayer + " has caught up to " + firstplayer + " at square " + secondplayer.getPosition());
                System.out.println(firstplayer + " has been moved to square " + firstplayer.getPosition());
            }

            updateBoard(players);

            if(!gameover) {
                System.out.println("Game not over; flipping again!");
                System.out.println("-----------------------------------------------------------------------------------------------------");
            }

            System.out.println();
        }

    }

    /**
     * This function creates new tile objects and sets the tiles to the GameBoard
     */
    private void createBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {

                //this is creating a new tile object and setting the tile number to the tile number.
                int tileNumber = (i * ROWS) + j + 1;
                gameboard[i][j] = new Tiles(tileNumber);
            }
        }
        setSnakes();
        setLadders();
    }

    /**
     * It prints the gameboard in a way that makes it look like a snake and ladder board
     */
    private void showBoard() {

        for (int j = 9; j >= 0; j--) {
            if (j % 2 == 0) {
                for (int x = 0; x < 10; x++) {

                    System.out.print(gameboard[j][x] + "\t");
                }
            } else {
                for (int x = 9; x >= 0; x--) {
                    System.out.print(gameboard[j][x] + "\t");
                }
            }
            System.out.println();
        }
    }

    /**
     * This function is used to update the gameboard with the new player positions.
     *
     * @param players This is an array of players that are playing the game.
     */
    private void updateBoard(Player[] players) {

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {

                //this is creating a new tile object and setting the tile number to the tile number.
                int tileNumber = (i * ROWS) + j + 1;
                gameboard[i][j] = new Tiles(tileNumber, players);
            }
        }

        showBoard();
    }

    /**
     * This function is used to move the player on the board
     *
     * @param p is the player object
     * @param diceRolled This is the value that the player rolled on the dice.
     */
    public void movePlayerOnBoard(Player p, int diceRolled){

        //this is adding the player's current position to the dice value that was rolled.
        int newposition = p.getPosition() + diceRolled;
        int i = 0;
        for (Tiles[] t : gameboard) {
            for(Tiles tile : t) {
                if (newposition == tile.getTileNumber()) {

                    // The bellow code is checking if the new position is greater than 100. If it is, it sets the position
                    // to 100 minus the new position minus 100. If it is not, it sets the position to the new position.
                    if (newposition > 100) {
                        p.setPosition(100 - (newposition - 100));
                    } else {
                        p.setPosition(tile.getNewPosition());
                    }

                    // The bellow code is checking if the player has won the game or not.
                    if (p.getPosition() == 80 || p.hasPlayerWon()) {
                        System.out.println(p + " got a dice value of " + p.getDiceValue() + "; now in square " + p.getPosition());
                        checkLaddersandSnakes();
                        System.out.println(p + " hits " + p.getPosition());
                        System.out.println(p + " Wins the game!!!");
                        updateBoard(players);
                        gameover = true;
                        System.exit(0);
                    }
                }
            }
            i++;
        }
    }


    private void checkLaddersandSnakes() {
        //this is checking for the snakes.
        for(int i = 0; i < NUM_SNAKES; i++) {
            if(snakes[i][0] == firstplayer.getPosition()) {
                firstplayer.setPosition(snakes[i][1]);
                System.out.println("Uh oh. Snakes takes " + firstplayer + " from square " + snakes[i][0] + " to square " + snakes[i][1]);
                System.out.println(firstplayer + " is now in square " + firstplayer.getPosition());
            } else if(snakes[i][0] == secondplayer.getPosition()) {
                secondplayer.setPosition(snakes[i][1]);
                System.out.println("Uh oh. Snakes takes " + secondplayer + " from square " + snakes[i][0] + " to square " + snakes[i][1]);
                System.out.println(secondplayer + " is now in square " + secondplayer.getPosition());
            }

        }

        //this is checking for the ladders.
        for(int i = 0; i < NUM_LADDERS; i++) {
            if(ladders[i][0] == firstplayer.getPosition()) {
                firstplayer.setPosition(ladders[i][1]);
                System.out.println("Yayy! Ladder takes " + firstplayer + " from square " + ladders[i][0] + " to square " + ladders[i][1]);
                System.out.println(firstplayer + " is now in square " + firstplayer.getPosition());
            } else if(ladders[i][0] == secondplayer.getPosition()) {
                secondplayer.setPosition(ladders[i][1]);
                System.out.println("Yayy! Ladder takes " + secondplayer + " from square " + ladders[i][0] + " to square " + ladders[i][1]);
                System.out.println(secondplayer + " is now in square " + secondplayer.getPosition());
            }
        }
    }

    /**
     * sets the snakes for the board
     */
    private void setSnakes() {
        //this is setting the snakes for the board.
        snakes = new int[NUM_SNAKES][2];

        snakes[0][0] = 16;
        snakes[0][1] = 6;
        snakes[1][0] = 48;
        snakes[1][1] = 30;
        snakes[2][0] = 64;
        snakes[2][1] = 60;
        snakes[3][0] = 79;
        snakes[3][1] = 19;
        snakes[4][0] = 93;
        snakes[4][1] = 68;
        snakes[5][0] = 95;
        snakes[5][1] = 24;
        snakes[6][0] = 97;
        snakes[6][1] = 76;
        snakes[7][0] = 98;
        snakes[7][1] = 78;
    }

    /**
     * sets the ladders for the board
     */
    private void setLadders() {
        //this is setting the ladders for the board.
        ladders = new int[NUM_LADDERS][2];

        ladders[0][0] = 1;
        ladders[0][1] = 38;
        ladders[1][0] = 4;
        ladders[1][1] = 14;
        ladders[2][0] = 9;
        ladders[2][1] = 31;
        ladders[3][0] = 21;
        ladders[3][1] = 42;
        ladders[4][0] = 28;
        ladders[4][1] = 84;
        ladders[5][0] = 36;
        ladders[5][1] = 44;
        ladders[6][0] = 51;
        ladders[6][1] = 67;
        ladders[7][0] = 71;
        ladders[7][1] = 91;
        ladders[8][0] = 80;
        ladders[8][1] = 100;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LadderAndSnake that)) return false;
        return ROWS == that.ROWS && COLS == that.COLS && NUM_SNAKES == that.NUM_SNAKES && NUM_LADDERS == that.NUM_LADDERS && Arrays.equals(gameboard, that.gameboard) && Arrays.equals(snakes, that.snakes) && Arrays.equals(ladders, that.ladders);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(ROWS, COLS, NUM_SNAKES, NUM_LADDERS);
        result = 31 * result + Arrays.hashCode(gameboard);
        result = 31 * result + Arrays.hashCode(snakes);
        result = 31 * result + Arrays.hashCode(ladders);
        return result;
    }
}
