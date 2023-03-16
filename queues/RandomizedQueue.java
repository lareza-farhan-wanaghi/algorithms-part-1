import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
        n = 0;
    }

    private void resize(int m) {
        Item[] a = (Item[]) new Object[m];
        for (int i = 0; i < n; i++) {
            a[i] = items[i];
        }
        items = a;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n <= 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        items[n++] = item;
        if (n >= items.length) {
            resize(n * 2);
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int randIndex = StdRandom.uniformInt(n);
        Item item = items[randIndex];
        items[randIndex] = items[n - 1];
        items[--n] = null;
        if (n > 0 && n == items.length / 4) {
            resize(items.length / 2);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return items[StdRandom.uniformInt(n)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ReverseArrayIterator implements Iterator<Item> {
        Item[] iterItems;
        int iterN;

        public ReverseArrayIterator() {
            iterItems = (Item[]) new Object[n];
            for (int i = 0; i < n; i++) {
                iterItems[i] = items[i];
            }
            iterN = iterItems.length;
        }

        public boolean hasNext() {
            return iterN > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            int randIndex = StdRandom.uniformInt(iterN);
            Item item = iterItems[randIndex];
            iterItems[randIndex] = iterItems[iterN - 1];
            iterItems[--iterN] = null;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        int n = 5;
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < n; i++) {
            queue.enqueue(i);
        }

        for (int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }
    }

}