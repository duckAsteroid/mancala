package com.cs.games.mancala.player;

import com.cs.games.mancala.model.Board;
import com.cs.games.mancala.model.Move;

/**
 * Interface to a class that can use it's own internal strategy to decide between a series of moves
 */
public interface MoveSupplier {
    /**
     * Given a board - choose a move
     * @param board the board state to choose a move from
     * @return the selected move
     */
    Move selectFrom(Board board);

    /**
     * A display name for this move supplier
     */
    String getDisplayName();
}
