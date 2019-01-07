package com.cs.games.mancala.model;

/**
 * An enumeration for the two players
 */
public enum Player {
    ONE('A', 0, 1), TWO('B',1, 0);

    public final char id;
    public final int number;
    public final int otherNumber;

    Player(char id, int number, int otherNumber) {
        this.id = id;
        this.number = number;
        this.otherNumber = otherNumber;
    }

    public static Player byValue(int player) {
        switch (player) {
            case 1:
                return TWO;
            case 0:
                return ONE;
            default:
                throw new IllegalArgumentException("Not a valid player value");
        }
    }

    public static Player parse(char c) {
        for (Player p : values()) {
            if (c == p.id) {
                return p;
            }
        }
        return null;
    }

    public Player opponent() {
        return byValue(otherNumber);
    }
}
