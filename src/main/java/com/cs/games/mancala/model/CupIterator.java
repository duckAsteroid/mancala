package com.cs.games.mancala.model;

import java.util.Iterator;

/**
 * An iterator around the board, it may be limited to a number of steps
 * and it may present only cups that can be modified by a given player
 */
public class CupIterator implements Iterator<Cup> {
    private final Cup origin;
    private final Player player;
    private int counter;
    private Cup current;

    public CupIterator(Cup origin, int cups, Player player) {
        if (origin == null) {
            throw new IllegalArgumentException("Origin cup cannot be null");
        }
        this.origin = origin;
        this.counter = cups;
        this.player = player;
        this.current = null;
    }

    @Override
    public boolean hasNext() {
        return counter != 0;
    }

    @Override
    public Cup next() {
        if (current == null) {
            current = origin;
        }
        else {
            current = current.next(player);
        }
        // update counter
        if (counter > 0) {
            counter--;
        }
        return current;
    }
}
