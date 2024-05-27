package com.cs.games.mancala.model;

public interface GameListener {
    void boardChanged(Board b);
    void moving(Move move);
    void moved(Move move);
    void undoing(Move move);
    void undone(Move move);
}
