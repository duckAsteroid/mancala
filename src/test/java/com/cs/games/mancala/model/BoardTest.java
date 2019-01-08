package com.cs.games.mancala.model;

import com.cs.games.mancala.model.visitor.MoveVisitor;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class BoardTest {

    private static class CountingVisitor implements MoveVisitor {
        public LinkedList<Move> visited = new LinkedList<>();

        @Override
        public void visit(Move m) {
            visited.add(m);
        }
    }

    private Board subject = Board.initialBoard();

    @Test(expected = AssertionError.class)
    public void testBadCounts() {
        // only fails if assertions enabled
        Board bad = new Board(new int[14], Player.ONE);
        assert false : "Should fail assertion check earlier";
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadCountSize() {
        Board bad = new Board(new int[12], Player.ONE);
    }

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

    @Test
    public void testVisitor() {
        CountingVisitor visitor = new CountingVisitor();
        subject.visit(visitor, 1);
        assertEquals(6, visitor.visited.size());

        visitor = new CountingVisitor();
        subject.visit(visitor, 2);
        assertEquals(41, visitor.visited.size());
    }
}