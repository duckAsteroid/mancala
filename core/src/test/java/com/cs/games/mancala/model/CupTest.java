package com.cs.games.mancala.model;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;


public class CupTest {

    @Test
    public void testFirstAndEndCups() {
        final Cup p1first = Cup.firstCup(Player.ONE);
        final Cup p2first = Cup.firstCup(Player.TWO);
        assertEquals(0, p1first.index());
        assertEquals(7, p2first.index());

        final Cup p1End = Cup.endCup(Player.ONE);
        final Cup p2End = Cup.endCup(Player.TWO);
        assertEquals(6, p1End.index());
        assertEquals(13, p2End.index());
        assertEquals(p1End, p2End.getOpposite());
        assertEquals(p2End, p1End.getOpposite());

        assertEquals(p1first, p2End.getNext());
        assertEquals(p2first, p1End.getNext());
    }

    @Test
    public void testPlayerCupIterator() {
        int count = 0;
        for (int i=0; i < 2; i++) {
            Player player = Player.byValue(i);
            Iterable<Cup> subject = Cup.playerCups(player);
            assertNotNull(subject);
            Iterator<Cup> iter = subject.iterator();
            for (int j = 0; j < 7; j++) {
                assertTrue(iter.hasNext());
                Cup cup = iter.next();
                assertNotNull(cup);
                assertEquals(i, cup.getPlayer().number);
                assertEquals(j, cup.getCupNumber());
                assertEquals(count, cup.index());

                Cup opposite = cup.getOpposite();
                assertNotNull(opposite);
                assertEquals(cup, opposite.getOpposite());
                count++;
            }
            assertFalse(iter.hasNext());
        }
        assertEquals(14, count);
    }

    @Test
    public void testFullIterator() {
        Iterator<Cup> iterator = Cup.iterator();
        assertNotNull(iterator);
        for(int i=0; i < 14; i++) {
            assertTrue(iterator.hasNext());
            Cup cup = iterator.next();
            assertEquals(i, cup.index());
        }
        assertFalse(iterator.hasNext());
    }


}