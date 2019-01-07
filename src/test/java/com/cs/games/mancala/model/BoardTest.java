package com.cs.games.mancala.model;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class BoardTest {

    private Board subject = Board.initialBoard();

    @Test
    public void testInitialScore() {
        assertEquals(24, subject.getScore(Player.ONE));
        assertEquals(0, subject.getSafeScore(Player.ONE));
        assertEquals(24, subject.getScore(Player.TWO));
        assertEquals(0, subject.getSafeScore(Player.TWO));
        assertNull(subject.getLeader());
        List<Move> moves = subject.nextMoves();
        assertNotNull(moves);
        assertEquals(6, moves.size());
    }

    @Test
    public void testMakeMove() {
        // simple move that wraps into opponents board
        Move move = subject.nextMoves().get(5);// last cup
        assertEquals(Player.ONE, move.getPlayer());
        assertEquals(5, move.getCup().getCupNumber());
        Board after = move.getAfter();

        assertEquals(21, after.getScore(Player.ONE));
        assertEquals(1, after.getSafeScore(Player.ONE));
        assertEquals(27, after.getScore(Player.TWO));
        assertEquals(0, after.getSafeScore(Player.TWO));
        assertEquals(Player.TWO, after.getLeader());
        List<Move> moves = after.nextMoves();
        assertNotNull(moves);
        assertEquals(6, moves.size());
        assertEquals(Player.TWO, after.getNextPlayer());

        // a move that gives another go
        move = subject.nextMoves().get(2);
        assertEquals(Player.ONE, move.getPlayer());
        assertEquals(2, move.getCup().getCupNumber());
        after = move.getAfter();

        assertEquals(24, after.getScore(Player.ONE));
        assertEquals(1, after.getSafeScore(Player.ONE));
        assertEquals(24, after.getScore(Player.TWO));
        assertEquals(0, after.getSafeScore(Player.TWO));
        assertEquals(null, after.getLeader());
        assertEquals(Player.ONE, after.getNextPlayer());
        moves = after.nextMoves();
        assertNotNull(moves);
        assertEquals(5, moves.size());
    }

    @Test
    public void testSkipOpponentsCupAndSteal() {
        Board before = new Board(new int[]{0,4,4,4,4,8,0,4,4,4,4,4,4,0}, Player.ONE);
        Move move = before.nextMoves().get(4);
        assertEquals(Player.ONE, move.getPlayer());
        assertEquals(5, move.getCup().getCupNumber());
        assertEquals(8, before.getBeadCount(move.getCup()));
        Board after = move.getAfter();

        assertEquals(23, after.getScore(Player.ONE));
        assertEquals(7, after.getSafeScore(Player.ONE));
        assertEquals(25, after.getScore(Player.TWO));
        assertEquals(0, after.getSafeScore(Player.TWO));
        assertEquals(Player.TWO, after.getLeader());
        List<Move> moves = after.nextMoves();
        assertNotNull(moves);
        assertEquals(5, moves.size());
        assertEquals(Player.TWO, after.getNextPlayer());
    }
}