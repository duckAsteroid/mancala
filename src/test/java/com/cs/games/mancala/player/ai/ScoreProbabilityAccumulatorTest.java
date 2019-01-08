package com.cs.games.mancala.player.ai;

import com.cs.games.mancala.model.Board;
import com.cs.games.mancala.model.Player;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScoreProbabilityAccumulatorTest {
    private ScoreProbabilityAccumulator subject = new ScoreProbabilityAccumulator(Player.ONE);

    @Test
    public void visit() {
        Board.initialBoard().visit(subject, 3);
        assertEquals(226, subject.getTotalPopulationSize());
        assertEquals(-1.1858407079646018, subject.getAverageLead(), 0.00001);
    }
}