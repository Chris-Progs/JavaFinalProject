
package project2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class LinkedList {

    // Nested inner private class Node
    public class Node {

        private Song songData;
        private Node nextPointer;
        
        public Node(){           
        }
        // Node class constructor
        public Node(Song data) {
            this.songData = data;
        }

        // Getter method for private inner Node class
        public Node getNode(Node node) {
            return node;
        }
    }
    
    private Node nodeHead = null;
    
    // Conversion of the LinkedList to an ObservableList of Song objects
    public ObservableList<Song> linkedToObservable(){
        Node sNode = nodeHead;
        ObservableList<Song> newObList = FXCollections.observableArrayList();
        while(sNode != null){
            newObList.add(sNode.songData);
            sNode = sNode.nextPointer;
        }
        return newObList;
    }
    
    public String findSongOfIndex(int index) {
        Node countNode = nodeHead;
        int count = 0;
        while (count < index) {
            countNode = countNode.nextPointer;
            count++;
        }    
        return countNode.songData.getFilePath();
    }
    
    public int findIndexOfSong(Song data) {
        Node countNode = nodeHead;
        int count = 0;
        while (!countNode.songData.equals(data)) {
            countNode = countNode.nextPointer;
            count ++;
        }
        return count;
    }
    
    // Add newNode to the start of the linkedlist
    public void addNode(Song data) {
        Node newNode = new Node(data);
        newNode.nextPointer = nodeHead;
        nodeHead = newNode;
    }
    
    public Node mergeSort() {
        nodeHead = mergeSort(nodeHead);
        return nodeHead;
    }
    
    public Node mergeSort(Node head) {

        if (head.nextPointer == null)
            return head;

        Node mid = getMid(head);
        Node head2 = mid.nextPointer;
        mid.nextPointer = null;
        Node newHead1 = mergeSort(head);
        Node newHead2 = mergeSort(head2);
        Node finalHead = merge(newHead1, newHead2);

        return finalHead;
    }

    public Node getMid(Node head) {

        Node slow = head, fast = head.nextPointer;
        while (fast != null && fast.nextPointer != null) {
            slow = slow.nextPointer;
            fast = fast.nextPointer.nextPointer;
        }
        return slow;
    }

    public Node merge(Node head1, Node head2) {

        if (head1 == null)
            return head2;
        if (head2 == null)
            return head1;
        // start with the linked list
        // whose head data is the least
        if (head1.songData.compareTo(head2.songData) < 0) {
            head1.nextPointer = merge(head1.nextPointer, head2);
            return head1;
        }
        else {
            head2.nextPointer = merge(head1, head2.nextPointer);
            return head2;
        }
    }
    
    // Function to find middle element
    // using Fast and Slow pointers
    public Node middleNode(Node start, Node last)
    {
        if (start == null)
            return null;
        Node slow = start;
        Node fast = start.nextPointer;
        while (fast != last)
        {
            fast = fast.nextPointer;
            if (fast != last)
            {
                slow = slow.nextPointer;
                fast = fast.nextPointer;
            }
        }
        return slow;
    }
 
    public Song binarySearch(Song value){
        Node searchNode = binarySearch(nodeHead, value);
        if (searchNode != null) {
            return searchNode.songData;
        } else {
            return null;
        }
    }

    // function to recursively search and split the LinkedList until parameter is found
    public Node binarySearch(Node head, Song value)
    {
        Node start = head;
        Node last = null;
        do
        {
            // Find Middle
            Node mid = middleNode(start, last);
            // If middle is empty
            if (mid == null)
                return null;
            // If value is present at middle
            if (mid.songData.compareTo(value) == 0)
                return mid;
            // If value is less than mid
            else if (mid.songData.compareTo(value) < 0)
            {
                start = mid.nextPointer;
            }
            // If the value is more than mid.
            else
                last = mid;
        } while (last == null || last != start);
        // value not present
        return null;
    }
}
