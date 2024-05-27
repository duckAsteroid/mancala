package com.cs.games.mancala.model;

import java.util.List;

public interface BoardCache {
    boolean contains(List<Move> moves);

    Board get(List<Move> moves);

    void put(List<Move> moves, Board board);
}
