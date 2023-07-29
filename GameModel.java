import java.util.ArrayList;
import java.util.Random;

/**
 * The GameModel class holds the logic for the card game. It contains the deal
 * stack, discard stack, and player's hands of cards. It  shuffles the cards,
 * deals the cards, and plays through each turn.
 *
 * @author Tim Harris
 * @version 1.0
 */
public class GameModel {
    private int playerCardValue;    // The value of the player's next card
    private int discardValue;// The value of the card on top of the discard pile
    private Queue<Integer> playerOneHand;
    private Queue<Integer> playerTwoHand;
    private Queue<Integer> playerThreeHand;
    private Queue<Integer> playerFourHand;
    private Queue<Integer> playerFiveHand;
    private Queue<Integer> playerSixHand;
    private Stack<Integer> dealStack = new Stack<Integer>();
    private Stack<Integer> discardStack = new Stack<Integer>();
    public GameModel(int numberOfPlayers) {
        if (numberOfPlayers > 1) {
            // A new queue for player one's hand
            playerOneHand = new Queue<Integer>();
            // A new queue for player two's hand
            playerTwoHand = new Queue<Integer>();
        }
        if (numberOfPlayers > 2) {
            // A new queue for player one's hand
            playerThreeHand = new Queue<Integer>();
        }
        if (numberOfPlayers > 3) {
            // A new queue for player one's hand
            playerFourHand = new Queue<Integer>();
        }
        if (numberOfPlayers > 4) {
            // A new queue for player one's hand
            playerFiveHand = new Queue<Integer>();
        }
        if (numberOfPlayers > 5) {
            // A new queue for player one's hand
            playerSixHand = new Queue<Integer>();
        }

    }

    /**
     * Adds 52 cards to an array, then shuffles them and puts them into a stack.
     */
    public void initialSetUp() {
        // An array list to hold the values of the 52 cards
        ArrayList<Integer> cards = new ArrayList<>(52);
        // An array list to hold the values of the cards once shuffled
        ArrayList<Integer> shuffledCards;

        // Put four sets of 1-13 into the cards array list
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j <= 13; j++) {
                cards.add(j);
            }
        }

        // Shuffle the cards
        shuffledCards = shuffleDeck(cards);

        // Put the shuffled cards into a stack for the deal stack
        for (int i = 0; i < 52; i++){
            dealStack.push(shuffledCards.get(i));
        }
    }

    /**
     * Deals seven cards to each player and puts one card on discard stack.
     */
    public void dealToPlayers(int numberOfPlayers){
        // Deal seven cards to each player
        for (int i = 0; i < 7; i++) {
            playerOneHand.enqueue(dealStack.pop());
            playerTwoHand.enqueue(dealStack.pop());
            if (numberOfPlayers > 2){
                playerThreeHand.enqueue(dealStack.pop());
            }
            if (numberOfPlayers > 3){
                playerFourHand.enqueue(dealStack.pop());
            }
            if (numberOfPlayers > 4){
                playerFiveHand.enqueue(dealStack.pop());
            }
            if (numberOfPlayers > 5){
                playerSixHand.enqueue(dealStack.pop());
            }

        }
        // Add a card onto the discard stack
        discardStack.push(dealStack.pop());
    }

    /**
     * @return A queue of player one's hand of cards.
     */
    public Queue<Integer> getPlayerOneHand(){
        return playerOneHand;
    }

    /**
     * @return The next card in player one's queue.
     */
    public int getPlayerOneCard(){
        return playerOneHand.peek();
    }

    /**
     * @return The next card in player two's queue.
     */
    public int getPlayerTwoCard(){
        return playerTwoHand.peek();
    }

    /**
     * @return A queue of player two's hand of cards.
     */
    public Queue<Integer> getPlayerTwoHand(){
        return playerTwoHand;
    }

    /**
     * @return A queue of player three's hand of cards.
     */
    public Queue<Integer> getPlayerThreeHand(){
        return playerThreeHand;
    }

    /**
     * @return The next card in player three's queue.
     */
    public int getPlayerThreeCard(){
        return playerThreeHand.peek();
    }

    /**
     * @return A queue of player four's hand of cards.
     */
    public Queue<Integer> getPlayerFourHand(){
        return playerFourHand;
    }

    /**
     * @return The next card in player four's queue.
     */
    public int getPlayerFourCard(){
        return playerFourHand.peek();
    }

    /**
     * @return A queue of player five's hand of cards.
     */
    public Queue<Integer> getPlayerFiveHand(){
        return playerFiveHand;
    }

    /**
     * @return The next card in player five's queue.
     */
    public int getPlayerFiveCard(){
        return playerFiveHand.peek();
    }

    /**
     * @return A queue of player six's hand of cards.
     */
    public Queue<Integer> getPlayerSixHand(){
        return playerSixHand;
    }

    /**
     * @return The next card in player six's queue.
     */
    public int getPlayerSixCard(){
        return playerSixHand.peek();
    }

    /**
     * @return The top card in the discard stack.
     */
    public int getDiscardValue(){
        dealStackCheck();    // Make sure the deal stack is not empty
        return (discardStack.peek());
    }

    /**
     * Checks to see if the deal stack is empty, and if it is, "flips over"
     * the discard stack to be the deal stack, but leaves the top card from the
     * discard stack as the only discard stack card.
     */
    public void dealStackCheck(){
        int topDiscard;     // The card on top of the discard stack

        // If the deal stack is empty, flip over the discard stack
        if (dealStack.empty()){
            topDiscard = discardStack.pop();
            // Pop the discards stack cards into the deal stack.
            while(!discardStack.empty()) {
                dealStack.push(discardStack.pop());
            }
            // Leave the top card from the discard stack on the discard stack
            discardStack.push(topDiscard);
        }
    }

    /**
     * Plays through one player's turn and returns whether the player's card was
     * lower, higher, or tied with the card on top of the discard stack.
     *
     * @param playerHand A queue representing the player's hand of cards.
     * @return A string representing whether the player's card was higher,
     * lower, or tied with the discard stack card. Also returns "OVER" if the
     * game is over.
     */
    public String takeTurn(Queue<Integer> playerHand) {
        playerCardValue = playerHand.peek();   // The value of the player's card
        discardValue = discardStack.peek(); // The value of the top discard card

        // Put the player's card onto the discard stack
        discardStack.push(playerHand.dequeue());

        // Return how the player's card compared to the top discard stack card
        if (playerCardValue > discardValue) {
            // Checks to see if the player won the game
            if (playerHand.empty()) {
                return "OVER";
            }
            else
                return "HIGHER";
        }
        else if (playerCardValue == discardValue) {
            dealStackCheck();   // Check to see if the deal stack is empty
            playerHand.enqueue(dealStack.pop());    // Draw one card
            return "TIED";
        } else {
            dealStackCheck();   // Check to see if the deal stack is empty
            playerHand.enqueue(dealStack.pop());    // Draw a card
            dealStackCheck();   // Check to see if the deal stack is empty
            playerHand.enqueue(dealStack.pop());    // Draw another card
            return "LOWER";
        }
    }

    /**
     * Shuffles the cards at the start of the game.
     *
     * @param cards An array list with the values of each card.
     * @return An array list with the values of each card shuffled.
     */
    public ArrayList<Integer> shuffleDeck(ArrayList<Integer> cards) {
        // Create new Random object
        Random rand = new Random();
        // Repeats for each card
        for (int i = cards.size(); i > 1; i--) {
            int j = rand.nextInt(i);
            int temp = cards.get(i - 1);
            cards.set(i - 1, cards.get(j));
            cards.set(j, temp);
        }
        return cards;
    }
}
