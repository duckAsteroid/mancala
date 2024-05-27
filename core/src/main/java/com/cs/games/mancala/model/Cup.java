package com.cs.games.mancala.model;

import lombok.EqualsAndHashCode;

import java.util.Iterator;

/**
 * Represents a cup on the board. A cup knows which is next, and which is opposite.
 */
@EqualsAndHashCode
public class Cup {
    /**
     * The index of the end cup in a players cups
     */
    private static final int END_CUP = 6;
    /**
     * The number of cups for a player
     */
    private static final int NUM_CUPS = END_CUP + 1;
    /**
     * Statically represents the cups on the board. Indexed by player (0/1) then by cup number (0-6).
     */
    public static final Cup[][] BOARD_LAYOUT = initialCups();
    /**
     * Which player does this belong to
     */
    private final Player player;
    /**
     * The cup number (0 - 6)
     */
    private final int cupNumber;
    /**
     * The next cup around the board
     */
    private Cup next;
    /**
     * The cup opposite (null for end cups)
     */
    private Cup opposite;

    public Cup(Player player, int cupNumber) {
        this.player = player;
        this.cupNumber = cupNumber;
    }

    /**
     * Creates the initial state of the cups on the board
     *
     * @return
     */
    static final Cup[][] initialCups() {
        Cup[][] cups = new Cup[2][7];
        Cup last = null;
        for (int playerNum = 0; playerNum < 2; playerNum++) {
            Player player = Player.byValue(playerNum);
            for (int cup = 0; cup < 7; cup++) {
                Cup built = new Cup(player, cup);
                cups[playerNum][cup] = built;

                // add link to this as "next" for the last cup
                if (last != null) {
                    last.next = built;
                }
                last = built;
            }
        }
        // make board "next" a loop
        last.next = cups[0][0];
        // add opposites
        for (int i = 0; i < 6; i++) {
            // 0,0 > 1,5
            // 0,5 > 1,0
            cups[0][i].opposite = cups[1][5 - i];
            cups[1][5 - i].opposite = cups[0][i];
        }
        // opposites for end cups
        cups[0][6].opposite = cups[1][6];
        cups[1][6].opposite = cups[0][6];
        return cups;
    }

    /**
     * An iterator once around the board from player 1 cup 0
     * @return a new iterator
     */
    public static Iterator<Cup> iterator() {
        return new CupIterator(BOARD_LAYOUT[0][0], 14, null);
    }

    public static Cup firstCup(Player p) {
        return BOARD_LAYOUT[p.number][0];
    }

    public static Cup endCup(Player p) {
        return BOARD_LAYOUT[p.number][END_CUP];
    }

    public static Iterable<Cup> playerCups(Player player) {
        return new Iterable<Cup>() {
            @Override
            public Iterator<Cup> iterator() {
                return new CupIterator(firstCup(player), NUM_CUPS, player);
            }
        };
    }

    public static Iterable<Cup> playerMoveCups(Player player) {
        return new Iterable<Cup>() {
            @Override
            public Iterator<Cup> iterator() {
                return new CupIterator(firstCup(player), END_CUP, player);
            }
        };
    }

    public static Cup parse(String s) {
        Player p = Player.parse(s.charAt(0));
        int index = Integer.parseInt(s.substring(1));
        return BOARD_LAYOUT[p.number][index];
    }

    public static Iterable<? extends Cup> cups() {
        return new Iterable<Cup>() {
            @Override
            public Iterator<Cup> iterator() {
                return Cup.iterator();
            }
        };
    }


    public Player getPlayer() {
        return player;
    }

    public Cup getOpposite() {
        return opposite;
    }

    public int getCupNumber() {
        return cupNumber;
    }

    public Cup getNext() {
        return next;
    }

    public String toString() {
        return player.id + Integer.toString(cupNumber);
    }

    public boolean isEndCup() {
        return cupNumber == END_CUP;
    }

    public boolean isPlayerEndCup(Player player) {
        return isEndCup() && player.equals(this.player);
    }

    public Cup next(Player p) {
        if (p != null && next.isEndCup() && !next.getPlayer().equals(p)) {
            // skip other player's end cup
            return next.next;
        }
        return next;
    }

    public int index() {
        return player.number * 7 + cupNumber;
    }

}
