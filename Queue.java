public class Queue<T> {

    /**
     * The Node class holds the functionality of storing values to a node and
     * linking nodes bi-directionally to one another.
     * @author Mike McKee
     */
    private class Node {

        /**
         * Stores value for a node.
         */
        public T cardValue;

        /**
         * Stores reference node to link.
         */
        public Node next;

        /**
         * Stores previous reference node to link.
         */
        @SuppressWarnings("unused")
        public Node prev;

        /**
         * The constructor takes in a value and assigns it null node links.
         *
         * @param payload Value to be added to a node
         * @param next Reference node to link (next node)
         * @param prev Reference node to link (previous node)
         */
        public Node(T payload, Node next, Node prev) {
            this.cardValue = payload;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node head; // Front of the queue
    private Node tail; // Back of the queue

    /**
     * Constructor which sets the queue head and tail to null.
     */
    public Queue() {
        head = null;
        tail = null;
    }

    /**
     * The enqueue method takes a value and adds it to the tail of the queue.
     * If the queue is empty, the object is added as the queue's head (and thus
     * also its tail).
     *
     * @param direction value to add to the queue
     */
    public void enqueue(T direction) {
        if (empty()) {
            tail = new Node (direction, null, null);
            head = tail;
        } else {
            tail.next = new Node (direction, null, tail);
            tail = tail.next;
        }
    }

    /**
     * The dequeue method removes a value from the head of the queue and sets
     * the queue's head value to the next object in line.
     *
     * @return value from the head of the queue
     * @throws IllegalArgumentException thrown in the event of an empty queue
     */
    public T dequeue() {
        if (empty())
            throw new IllegalArgumentException("Error! Empty queue!");
        else {
            T returnVal = head.cardValue;
            if (head.next != null) {
                head = head.next;
                head.prev = null;
            } else {
                head = null;
            }
            return returnVal;
        }
    }

    /**
     * The peek method reveals - but does not remove - what the value at the
     * head of the queue is.
     *
     * @return value of an object from the head of the queue
     * @throws IllegalArgumentException thrown in the event of an empty queue
     */
    public T peek() {
        if (empty())
            throw new IllegalArgumentException("Error! Empty queue!");
        else
            return head.cardValue;
    }

    /**
     * The empty method returns whether a queue is empty or not.
     *
     * @return true if empty, else false
     */
    public boolean empty() {
        return head == null;
    }

    /**
     * The copy method takes the queue values of a Queue instance, creates a
     * deep copy of them and enqueues them into another Queue instance. Returns
     * an empty queue if the original queue was empty.
     *
     * @return a Queue copied with the values of another Queue
     */
    public Queue <T> copy() {
        Queue <T> retQueue =new Queue <T>();
        Node n = this.head;
        while (n != null) {
            retQueue.enqueue(n.cardValue);
            n = n.next;
        }
        return retQueue;
    }

    /**
     * The toString method takes values of a Queue instance and copies them to
     * a single string which it then returns.
     *
     * @return a string of the values contained within queue
     */
    public String toString() {
        StringBuilder strbldr = new StringBuilder();
        Node p = head; // Copy of instance queue

        while (p != null) {
            strbldr.append(p.cardValue + " ");
            p = p.next;
        }
        return strbldr.toString();
    }

    /**
     * The append method takes any values contained within one Queue instance
     * and appends them to the tail of another instance. It is not used for
     * this game.
     *
     * @param addQueue queue to be added to another queue's tail
     */
    public void append(Queue <T>  addQueue) {
        Queue <T> tempQueue = addQueue.copy();
        while (tempQueue.head != null){
            this.enqueue(tempQueue.head.cardValue);
            tempQueue.head = tempQueue.head.next;
        }
    }

    /**
     * The equals method checks to see if a given Queue instance contains
     * the same payload data as the current Queue instance. It is not used
     * for this game.
     *
     * @param queue Queue instance to compare to
     * @return true if the same, else false
     */
    public boolean equals(Queue <T> queue) {
        return queue == this;
    }

}
