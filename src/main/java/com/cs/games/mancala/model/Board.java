package com.cs.games.mancala.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An immutable representation of the current state of the Mancala game - cups and beads; and who's turn it is
 *
 * <pre>
 *
 *
 *   --------------&gt;------------------
 *   |   | 0 | 1 | 2 | 3 | 4 | 5 |   |  Player 1
 *   |13 |-----------------------| 6 |
 *   |   |12 |11 |10 | 9 | 8 | 7 |   |  Player 2
 *   --------------&lt;------------------
 *
 *
 * </pre>
 *
 * @author <A
 * HREF="mailto:chris.senior@teradyne.com?subject=com.cs.games.mancala.model.Board">Chris
 * Senior </A>
 */
@EqualsAndHashCode
@ToString
public class Board {
    /**
     * The bead counts in the cups. Indexed by {@link Cup#index()}
     */
    private final int[] beads;
    /**
     * The scores for each player
     */
    private final int[] scores;
    /**
     * The player who makes the next move
     */
    private final Player nextPlayer;
    /**
     * The cups which are valid moves for the {@link #nextPlayer}
     */
    private final List<Cup> nextMoves;

    public Board(int[] beads, Player nextPlayer) {
        this.beads = beads;
        this.nextPlayer = nextPlayer;
        this.scores = new int[]{calcScore(Player.ONE), calcScore(Player.TWO)};
        this.nextMoves = calcNextMoves();
        checksum();
    }

    public Board(int[] beads, Player nextPlayer, int[] scores, List<Cup> nextMoves) {
        this.beads = beads;
        this.nextPlayer = nextPlayer;
        this.scores = scores;
        this.nextMoves = nextMoves;
        checksum();
    }

    public void checksum() {
        if (Arrays.asList(beads).stream().count() == 48) {
            throw new IllegalStateException("Count of beads is not 48!!");
        }
    }

    public static Board initialBoard() {
        return new Board(initialBeads(), Player.ONE);
    }

    private static int[] initialBeads() {
        return new int[]{4,4,4,4,4,4,0,4,4,4,4,4,4,0};
    }

    private int calcScore(Player player) {
        int score = 0;
        for (Cup cup : Cup.playerCups(player)) {
            score += beads[cup.index()];
        }
        return score;
    }

    public Iterable<Cup> getCups(Player player) {
        return Arrays.asList(Cup.BOARD_LAYOUT[player.number]);
    }

    public int getScore(Player player) {
        return scores[player.number];
    }

    public int getSafeScore(Player player) {
        return beads[Cup.endCup(player).index()];
    }

    public int getLead(Player player) {
        return scores[player.number] - scores[player.otherNumber];
    }

    public int getBeadCount(Cup cup) {
        return beads[cup.index()];
    }

    public int[] cloneBeads() {
        int[] clone = new int[14];
        System.arraycopy(beads, 0, clone, 0, clone.length);
        return clone;
    }

    public List<Move> nextMoves() {
        return nextMoves.stream()
                .map(cup -> new Move(cup, this))
                .collect(Collectors.toList());
    }

    private List<Cup> calcNextMoves() {
        ArrayList<Cup> moves = new ArrayList<>();
        for(Cup playerCup : Cup.playerMoveCups(nextPlayer)) {
            if (beads[playerCup.index()] > 0) {
                moves.add(playerCup);
            }
        }
        return Collections.unmodifiableList(moves);
    }

    public boolean isGameOver() {
        return nextMoves.isEmpty();
    }

    /**
     * Which player (if either) is in the lead.
     *
     * @return the lead player or NULL if tied
     */
    public Player getLeader() {
        int lead = getLead(Player.ONE);
        if (lead > 0) {
            return Player.ONE;
        } else if (lead < 0) {
            return Player.TWO;
        } else {
            return null;
        }
    }

    public Player getNextPlayer() {
        return nextPlayer;
    }

}