package com.cs.games.mancala.model.visitor;

import com.cs.games.mancala.model.Move;

public interface MoveVisitor {
    /**
     * A visitor to all the moves in a board
     * @param move
     */
    void visit(Move move);
}
