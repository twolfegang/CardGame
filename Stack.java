public class Stack<T> {
    /**
     * The Node class holds the functionality of storing values to a node and
     * linking nodes to one another.
     * @author Mike McKee
     */
    private class Node  {

        /**
         * Stores value for a node.
         */
        public T cardValue;

        /**
         * Stores reference node to link.
         */
        public Node next;

        /**
         * The constructor takes in a value and assigns it a null node link.
         *
         * @param payload Value to be added to a node
         * @param next Reference node to link
         */
        public Node(T payload, Node next) {
            this.cardValue = payload;
            this.next = next;
        }
    }

    private Node top; // Top of the stack

    /**
     * The constructor creates a fresh linked list stack with no entries.
     */
    public Stack() {
        top = null;
    }

    /**
     * The push method adds a new item to the top of the stack.
     *
     * @param val item to add to the stack
     */
    public void push(T val) {
        top = new Node(val, top);
    }

    /**
     * The pop method returns and removes the top value of the stack.
     *
     * @return item from top of stack
     * @exception IllegalArgumentException If the stack is empty
     */
    public T pop() {
        if (empty())
            throw new IllegalArgumentException("Error! The stack is empty.");
        else {
            T returnValue = top.cardValue;
            top = top.next;
            return returnValue;
        }
    }

    /**
     * The empty method checks for an empty stack.
     *
     * @return true if stack is empty, else false
     */
    public boolean empty() {
        return top == null;
    }

    /**
     * The peek method returns the top value of the stack.
     *
     * @return value of item sitting at the top of stack
     * @exception IllegalArgumentException If the stack is empty
     */
    public T peek() {
        if (empty())
            throw new IllegalArgumentException("Error! The stack is empty.");
        else
            return top.cardValue;
    }

    /**
     * The copy method takes the values of a Stack instance, creates a
     * deep copy of them and pushes them into another Stack instance. Returns
     * an empty stack if the original stack was empty.
     *
     * @return a Stack copied with the values of another Stack
     */
    public Stack copy() {
        Stack  copiedStack = new Stack (); // Deep copy of object
        Stack temp = new Stack (); // Temp copy to maintain item order
        Node q = top;

        while (q != null) {
            temp.push(q.cardValue);
            q = q.next;
        }

        // Transfers payload over in correct order to be returned
        while (!temp.empty()) {
            copiedStack.push(temp.pop());
        }
        return copiedStack;
    }

    /**
     * The toString method takes values of a Stack instance and copies them to
     * a single string which it then returns.
     *
     * @return a string of the values contained within queue
     */
    public String toString() {
        StringBuilder strbldr = new StringBuilder();
        Node p = top; // Copy of instance stack

        while (p != null) {
            strbldr.append(p.cardValue + " ");
            p = p.next;
        }
        return strbldr.toString();
    }

    /**
     * The equals method checks to see if a given Stack instance contains
     * the same payload data as the current Stack instance. It is not used for
     * this game.
     *
     * @param stack Stack instance to compare to
     * @return true if the same, else false
     */
    public boolean equals(Stack <T> stack) {
        Stack <T> stack1 = stack.copy();
        Stack <T> stack2 = this.copy();

        while (!stack1.empty()) {
            if (stack2.empty())
                return false;
            else {
                T val1 = stack1.pop();
                T val2 = stack2.pop();
                if (!val1.equals(val2))
                    return false;
            }
        }
        return true;
    }
}
