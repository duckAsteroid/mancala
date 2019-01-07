package com.cs.games.mancala.model;

import com.cs.games.mancala.player.MoveSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A game of mancala between two named players
 */
public class Game {
    /**
     * Logger for writing log messages (Log4j)
     */
    private static final Logger logger = LoggerFactory.getLogger(Game.class);
    /**
     * The history of moves in the game to date
     */
    private LinkedList<Move> history = new LinkedList<>();
    /**
     * The supplier of moves for the players
     */
    private MoveSupplier[] players = new MoveSupplier[2];
    /**
     * Listeners for updates to the game
     */
    private ArrayList<GameListener> listeners = new ArrayList<>();
    /**
     * The current board state
     */
    private Board board;

    public Game(MoveSupplier p1, MoveSupplier p2) {
        players[0] = p1;
        players[1] = p2;
        board = Board.initialBoard();
    }

    public void addGameListener(GameListener listener) {
        listeners.add(listener);
    }

    public void removeGameListener(GameListener listener) {
        listeners.remove(listener);
    }

    protected void notifyBoardChanged() {
        List<GameListener> copy = new ArrayList<>(listeners);
        for(GameListener listener : copy) {
            listener.boardChanged(board);
        }
    }

    public Board getBoard() {
        return board;
    }

    public List<Move> getMovesHistory() {
        return Collections.unmodifiableList(history);
    }

    public void doMove(Move m) {
        this.board = m.getAfter();
        history.add(m);
        notifyBoardChanged();
    }

    public void undo() {
        if (!history.isEmpty()) {
            Move move = history.removeLast();
            this.board = move.getBefore();
            notifyBoardChanged();
        }
        else {
            throw new IllegalStateException("Nothing to undo");
        }
    }

    public MoveSupplier getPlayer() {
        return players[board.getNextPlayer().number];
    }
}
