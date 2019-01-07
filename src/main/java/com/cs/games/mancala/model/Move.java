package com.cs.games.mancala.model;

import lombok.ToString;

import java.util.Iterator;

/**
 * Represents a possible move on the board
 */
@ToString(exclude = "after")
public class Move implements Iterable<Cup> {
    /**
     * The start cup for this move.
     */
    private final Cup startCup;
    /**
     * The board before this move (this move is one of the moves on this board)
     */
    private final Board before;
    /**
     * A cache of the calculated "after" board state
     */
    private transient Board after = null;

    public Move(Cup startCup, Board before) {
        this.startCup = startCup;
        this.before = before;
    }

    public Board getBefore() {
        return before;
    }

    public Cup getCup() {
        return startCup;
    }

    /**
     * The player whose move this is
     */
    public Player getPlayer() {
        return before.getNextPlayer();
    }

    public Board getAfter() {
        if (after == null) {
            MoveOutcome outcome = calcMoveOutcome();
            after = new Board(outcome.getBeadCount(), outcome.isAnotherGo() ? getPlayer() : getPlayer().opponent());
        }
        return after;
    }

    private MoveOutcome calcMoveOutcome() {
        int[] cups = before.cloneBeads();
        Iterator<Cup> moveIterator = iterator();
        Cup cup = null;
        do {
            if (cup == null) {
                cup = moveIterator.next();
                // scoop up the beads
                cups[cup.index()] = 0;
            } else {
                cup = moveIterator.next();
                // drop a bead in that cup
                cups[cup.index()]++;
            }
        } while (moveIterator.hasNext());
        // is this another go for "this" player
        boolean anotherGo = cup.isPlayerEndCup(getPlayer());

        // special "bead opposite" stealing logic...
        // only applies when we did not finish in our end cup
        if (!anotherGo) {
            // Did we put the last bead in an empty cup? (i.e. count == 1)
            if (cups[cup.index()] == 1) {
                // and does it belongs to us..
                if (cup.getPlayer().equals(getPlayer())) {
                    // take that bead + the beads opposite into our end cup
                    Cup opposite = cup.getOpposite();
                    int steal = cups[opposite.index()] + 1;
                    cups[opposite.index()] = 0;
                    cups[cup.index()] = 0;
                    cups[Cup.endCup(getPlayer()).index()] += steal;
                }
            }
        }
        return MoveOutcome.builder()
                .anotherGo(anotherGo)
                .beadCount(cups)
                .build();
    }

    @Override
    public Iterator<Cup> iterator() {
        int beads = before.getBeadCount(startCup);
        return new CupIterator(startCup, beads + 1, getPlayer());
    }
}
