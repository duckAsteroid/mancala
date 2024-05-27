package com.cs.games.mancala.player.ai;

import com.cs.games.mancala.model.Board;
import com.cs.games.mancala.model.Move;
import com.cs.games.mancala.model.Player;
import com.cs.games.mancala.model.visitor.MoveVisitor;

import java.util.Map;
import java.util.TreeMap;

public class ScoreProbabilityAccumulator implements MoveVisitor {
    private final Player player;
    private Map<Integer, Integer> histogram = new TreeMap<>();

    public ScoreProbabilityAccumulator(Player player) {
        this.player = player;
    }

    @Override
    public void visit(Move move) {
        Board board = move.getAfter();
        int lead = board.getLead(player);
        int count = 0;
        if (histogram.containsKey(lead)) {
            count = histogram.get(lead);
        }
        count = count + 1;
        histogram.put(lead, count);
    }

    public int getTotalPopulationSize() {
        return histogram.values().stream().mapToInt(Integer::intValue).sum();
    }

    public int getMin() {
        return histogram.keySet().stream().mapToInt(Integer::intValue).min().orElse(0);
    }

    public int getMax() {
        return histogram.keySet().stream().mapToInt(Integer::intValue).max().orElse(0);
    }

    public int getCount(int score) {
        return histogram.getOrDefault(score, 0);
    }

    public double getSum() {
        double sum = 0.0;
        for(Map.Entry<Integer, Integer> entry : histogram.entrySet()) {
            double product = entry.getKey().doubleValue() * entry.getValue().doubleValue();
            sum += product;
        }
        return sum;
    }

    public double getAverageLead() {
        return getSum() / (double)getTotalPopulationSize();
    }
}
