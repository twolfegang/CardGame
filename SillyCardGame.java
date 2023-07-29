import java.util.Scanner;

/**
 * The SillyCardGame program plays through a card game where each player is
 * dealt seven cards and tries to discard their hand by having their card value
 * be higher than the value of the card on top of the discard stack. The first
 * player to run out of cards wins.
 *
 * @author Tim Harris
 * @version 1.0
 */
public class SillyCardGame {
    /**
     * Plays through the game and displays welcome and goodbye messages. Allows
     * the users to repeat the game if they want.
     *
     * @param args A string array containing the command line arguments.
     */
    public static void main(String[] args) {
        String input;       // Holds user input for repeating the game
        char repeat;        // Used for repeating the game.
        String[] names;     // Contains the names of the players
        int numberOfPlayers;
        // Create a new Scanner object
        Scanner keyboard = new Scanner(System.in);

        // Displays a welcome message
        welcome();

        System.out.print("How many players are there (2-6)? ");
        numberOfPlayers = keyboard.nextInt();
        keyboard.nextLine();
        System.out.println();

        // Allows the users to enter their names
        names = getPlayerNames(keyboard, numberOfPlayers);

        // Repeat as many times as user chooses
        do {
            // Create new GameModel object for one play through the game
            GameModel game = new GameModel(numberOfPlayers);

            // Shuffles the cards
            game.initialSetUp();

            // Deals seven cards to players
            game.dealToPlayers(numberOfPlayers);

            // Plays through one round of the game
            playOneRound(game, names, keyboard, numberOfPlayers);

            // Allow user to repeat the game if they want to
            System.out.print("Would you like to play again (enter " +
                    "no to stop)? ");
            input = keyboard.nextLine();
            repeat = input.charAt(0);
        } while (repeat != 'n' || repeat != 'N');

        // Displays goodbye message
        goodbye();

        // Close the Scanner object
        keyboard.close();
    }

    /**
     * Plays through one round of the game and goes until someone has won.
     *
     * @param game A GameModel object created for each play through the game.
     * @param names A string array with the names of the players.
     * @param keyboard A scanner object used for taking a turn.
     */
    public static void playOneRound(GameModel game, String[] names,
                                    Scanner keyboard, int numberOfPlayers) {
        String turnResult = " ";
        // Repeat turns until a player has won by discarding their last card
        while (turnResult != "OVER") {
            turnResult = playTurn(game, names[0], keyboard,
                    game.getPlayerOneHand(), game.getPlayerOneCard());
            // If player one did not just win, take a turn for player two
            if (turnResult != "OVER") {
                turnResult = playTurn(game, names[1], keyboard,
                        game.getPlayerTwoHand(), game.getPlayerTwoCard());
            }
            if (turnResult != "OVER" && numberOfPlayers > 2) {
                turnResult = playTurn(game, names[2], keyboard,
                        game.getPlayerThreeHand(), game.getPlayerThreeCard());
            }
            if (turnResult != "OVER" && numberOfPlayers > 3) {
                turnResult = playTurn(game, names[3], keyboard,
                        game.getPlayerFourHand(), game.getPlayerFourCard());
            }
            if (turnResult != "OVER" && numberOfPlayers > 4) {
                turnResult = playTurn(game, names[4], keyboard,
                        game.getPlayerFiveHand(), game.getPlayerFiveCard());
            }
            if (turnResult != "OVER" && numberOfPlayers > 5) {
                turnResult = playTurn(game, names[5], keyboard,
                        game.getPlayerSixHand(), game.getPlayerSixCard());
            }
        }
    }

    /**
     * Plays through one turn for one player.
     *
     * @param game A GameModel object for one round of the card game.
     * @param name The player's name whose turn it is.
     * @param keyboard  A Scanner object for user input
     * @param playerHand The players hand of cards (a queue)
     * @param playerCardValue The value of the next card in the player's queue
     * @return A string representing the result of the turn.
     */
    public static String playTurn(GameModel game, String name,
                                  Scanner keyboard, Queue<Integer> playerHand,
                                  int playerCardValue){
        String turnResult;  // The result of the turn
        int[] playerCards = new int[52];    // The player's hand of cards
        int count = 0;      // Used for displaying the player's cards
        // Creates a copy of player one's queue of cards
        Queue<Integer> copy = playerHand.copy();
        // Put the user's cards into an array so that they can be printed
        while (!copy.empty()) {
            playerCards[count] = copy.dequeue();
            count++;
        }

        // Prints the player's cards from the array
        System.out.println("\n" + name + "'s turn, cards: ");
        System.out.print("| ");
        for (int i = 0; i < playerCards.length; i++) {
            if (playerCards[i] != 0) {
                System.out.print(playerCards[i] + " | ");
            }
        }
        System.out.println();
        // Display the value of the top card in the discard stack
        System.out.println("Discard pile card: " + game.getDiscardValue());
        // Display the value of the next card in the player's hand
        System.out.println("Your current card: " + playerCardValue);

        // Takes a turn when the user presses enter
        System.out.println("Press ENTER to take a turn.");
        keyboard.nextLine();

        // Takes one turn for player one.
        turnResult = takeTurn(game, playerHand);
        return turnResult;
    }

    /**
     * Plays through one turn for one player.
     *
     * @param game A GameModel object representing one play through of the game.
     * @param playerHand A queue representing one player's hand of cards
     * @return A string representing whether the player's card was higher,
     * lower, or tied, or whether the player won.
     */
    public static String takeTurn(GameModel game, Queue<Integer> playerHand){
        // A string containing the result of the player's turn
        String turnResult = game.takeTurn(playerHand);

        // If the player's card was lower than the discard:
        if (turnResult == "LOWER"){
            System.out.println("Your card is lower, pick up two cards.\n");
        }
        // If the player's card was higher:
        else if (turnResult == "HIGHER"){
            System.out.println("Your card is higher, your turn is over.\n");
        }
        // If the player's card was tied:
        else if (turnResult == "TIED"){
            System.out.println("Your card is tied, pick up one card!\n");
        }
        // Otherwise, the player won
        else
        {
            System.out.println("You won! \n\nThe game has finished!\n");
        }
        return turnResult;
    }

    /**
     * Displays a welcome message.
     */
    public static void welcome() {
        System.out.println("""

                Silly Little Games welcomes you to our new card game. This\s
                game is for two players and the goal is to run out of cards in\s
                your hand quicker than your opponent does. Let's play! Good\s 
                Luck!
                """);
    }

    /**
     * Allows the users to enter their names and returns them in a string array.
     *
     * @param keyboard A Scanner object to allow the users to enter their names
     * @return A string array containing the names of the players.
     */
    public static String[] getPlayerNames(Scanner keyboard,
                                          int numberOfPlayers){

        // A string array to hold the names of the players
        String[] playerNames = new String[numberOfPlayers];

        // Repeat for the number of players
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.print("Enter the name of player " + (i + 1) + ": ");
            playerNames[i] = keyboard.nextLine();
        }
        return playerNames;
    }

    /**
     * Displays a goodbye message.
     */
    public static void goodbye(){
        System.out.println("\nGoodbye, and thanks " +
                "for playing!");
    }


}
