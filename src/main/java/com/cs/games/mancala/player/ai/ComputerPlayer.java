package com.cs.games.mancala.player.ai;

import com.cs.games.mancala.model.Board;
import com.cs.games.mancala.model.Move;
import com.cs.games.mancala.model.Player;
import com.cs.games.mancala.player.MoveSupplier;

import java.util.Iterator;
import java.util.List;

/**
 * A computer player can search through a vast array of possible next moves to
 * evaluate which one maximises the pay-off to the player.
 *
 * @author <A HREF="mailto:chris.senior@teradyne.com?subject=com.cs.games.mancala.model.ai.ComputerPlayer">Chris Senior</A>
 */
public class ComputerPlayer implements MoveSupplier {

    @Override
    public Move selectFrom(Board board) {
        return doBestScoreMove(board);
    }

    /**
     * Selects the move to give this player the best score possible after the move.
     *
     * @param board The board to pick the best score from
     * @return The last move with the highest score
     */
    public Move doBestScoreMove(Board board) {
        List<Move> moves = board.nextMoves();
        Integer bestScore = null;
        Move bestMove = null;
        // TODO recursive search for best move...
        Iterator iter = moves.iterator();
        while (iter.hasNext()) {
            Move move = (Move) iter.next();
            Integer score = new Integer(move.getAfter().getLead(board.getNextPlayer()));
            if (bestScore == null || bestScore.compareTo(score) > 0) {
                bestScore = score;
                bestMove = move;
            }
        }
        return bestMove;
    }

    @Override
    public String getDisplayName() {
        return "Computer AI Player";
    }
}