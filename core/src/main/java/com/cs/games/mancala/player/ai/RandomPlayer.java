package com.cs.games.mancala.player.ai;

import com.cs.games.mancala.model.Board;
import com.cs.games.mancala.model.Move;
import com.cs.games.mancala.model.Player;
import com.cs.games.mancala.player.MoveSupplier;

import java.util.List;

/**
 * @author <A
 * HREF="mailto:chris.senior@teradyne.com?subject=com.cs.games.mancala.model.ai.RandomPlayer">Chris
 * Senior </A>
 */
public class RandomPlayer implements MoveSupplier {

    @Override
    public Move selectFrom(Board board) {
        List<Move> moves = board.nextMoves();
        int selection = (int) (Math.random() * moves.size());
        return (Move) moves.get(selection);
    }

    @Override
    public String getDisplayName() {
        return "Random player";
    }
}